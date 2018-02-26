package gadolfolozano.pe.mvpexample.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.databinding.ActivityCreateEventBinding;
import gadolfolozano.pe.mvpexample.di.component.DaggerEventComponent;
import gadolfolozano.pe.mvpexample.di.component.EventComponent;
import gadolfolozano.pe.mvpexample.util.Constanst;
import gadolfolozano.pe.mvpexample.view.model.EventModel;
import gadolfolozano.pe.mvpexample.view.model.ModelResponse;
import gadolfolozano.pe.mvpexample.viewmodel.EventViewModel;

public class CreateEventActivity extends BaseActivity implements OnMapReadyCallback {

    private static final String TAG = CreateEventActivity.class.getSimpleName();

    private ActivityCreateEventBinding mBinding;

    private GoogleMap mGoogleMap;

    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 101;

    private LatLng mSelectedPositionAtMap;

    private EventComponent eventComponent;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    EventViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void bindViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_event);
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.text_create_event_title);

        mBinding.editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditTextDateClicked();
            }
        });

        mBinding.editTextHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditTextHourClicked();
            }
        });

        mBinding.imgIndicator.setVisibility(View.GONE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveClicked();
            }
        });
    }

    private void onSaveClicked() {
        String dateString = String.format(Locale.getDefault(), "%s %s", mBinding.editTextDate.getText(), mBinding.editTextHour.getText());
        DateFormat formatter = new SimpleDateFormat(Constanst.DATE_FORMAT, Locale.getDefault());
        Date date = Calendar.getInstance().getTime();
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            Log.e("Exception: %s", e.getMessage());
        }
        Log.d("Date", "time " + date.getTime());

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        EventModel eventModel = new EventModel();
        eventModel.setName(mBinding.editTextName.getText().toString());
        eventModel.setLongitude(mSelectedPositionAtMap.longitude);
        eventModel.setLatitude(mSelectedPositionAtMap.latitude);
        eventModel.setLocale(mBinding.editTextPlace.getText().toString());
        eventModel.setTimeStamp(date.getTime());
        viewModel.saveEvent(eventModel, currentUser.getUid()).observe(this, new Observer<ModelResponse<EventModel>>() {
            @Override
            public void onChanged(@Nullable ModelResponse<EventModel> modelResponse) {
                switch (modelResponse.getStatus()) {
                    case ModelResponse.SUCCESS:
                        Toast.makeText(CreateEventActivity.this, "Evento creado exitosamente", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case ModelResponse.ERROR:
                        Toast.makeText(CreateEventActivity.this, "Hubo un error al crear el evento", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private void onEditTextHourClicked() {
        showTimePicker();
    }

    private void onEditTextDateClicked() {
        showDatePicker();
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();

        int current_month = c.get(Calendar.MONTH);
        int current_day = c.get(Calendar.DAY_OF_MONTH);
        int current_year = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String dateString = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month + 1, year);
                mBinding.editTextDate.setText(dateString);
            }
        }, current_year, current_month, current_day);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar c = Calendar.getInstance();
        final int current_hour = c.get(Calendar.HOUR_OF_DAY);
        final int current_minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String hourString = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                mBinding.editTextHour.setText(hourString);
            }
        }, current_hour, current_minute, true);
        timePickerDialog.show();
    }

    @Override
    protected void prepareActivity() {
        viewModel = ViewModelProviders.of(this, mViewModelFactory).get(EventViewModel.class);
        viewModel.init();
    }

    @Override
    protected void initializeInjector() {
        eventComponent = DaggerEventComponent.builder()
                .build();
        eventComponent.inject(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                Log.d(TAG, "onCameraIdle");
                mSelectedPositionAtMap = mGoogleMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
                mBinding.editTextPlace.setText("");
                mBinding.editTextPlace.append(mSelectedPositionAtMap.latitude + "-" + mSelectedPositionAtMap.longitude);
            }
        });
        getDeviceLocation();
    }

    private void getDeviceLocation() {
        if (checkLocalitionPermission()) {
            updateLocationUI();
        } else {
            askForLocationPermission();
        }

        // A step later in the tutorial adds the code to get the device location.
    }

    private boolean checkLocalitionPermission() {
        return ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void askForLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateLocationUI();
                }
            }
        }
    }

    private void updateLocationUI() {
        if (mGoogleMap == null || !checkLocalitionPermission()) {
            return;
        }
        try {
            mBinding.imgIndicator.setVisibility(View.VISIBLE);

            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .build();                   // Creates a CameraPosition from the builder
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
