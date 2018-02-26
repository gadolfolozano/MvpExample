package gadolfolozano.pe.mvpexample.view.activity;

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.databinding.ActivityDetailEventBinding;
import gadolfolozano.pe.mvpexample.di.component.DaggerEventComponent;
import gadolfolozano.pe.mvpexample.di.component.EventComponent;
import gadolfolozano.pe.mvpexample.util.Constanst;
import gadolfolozano.pe.mvpexample.view.dialog.EnrolledDialogFragment;
import gadolfolozano.pe.mvpexample.view.model.EventModel;
import gadolfolozano.pe.mvpexample.view.model.ModelResponse;
import gadolfolozano.pe.mvpexample.viewmodel.EventViewModel;

public class DetailEventActivity extends BaseActivity implements OnMapReadyCallback {

    private static final String TAG = DetailEventActivity.class.getSimpleName();

    private ActivityDetailEventBinding mBinding;

    private GoogleMap mGoogleMap;

    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 101;

    public static final String EXTRA_EVENT = "bundle_event";

    private EventComponent eventComponent;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    EventViewModel viewModel;

    private EventModel eventModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void bindViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_event);
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.text_detail_event_title);

        eventModel = getIntent().getExtras().getParcelable(EXTRA_EVENT);

        mBinding.txtName.setText(eventModel.getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constanst.DATE_FORMAT, Locale.getDefault());
        Date date = new Date(eventModel.getTimeStamp());
        mBinding.txtDate.setText(simpleDateFormat.format(date));
        mBinding.txtPlace.setText(eventModel.getLocale());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        getDeviceLocation();
    }

    private void getDeviceLocation() {
        if (checkLocalitionPermission()) {
            updateLocationUI();
        } else {
            askForLocationPermission();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_enroll:
                enrollToEvent();
                return true;
            case R.id.action_show_erolled:
                showEnrolled();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void showEnrolled(){
        EnrolledDialogFragment enrolledDialogFragment =
                EnrolledDialogFragment.newInstance(eventModel.getId());
        enrolledDialogFragment.setCancelable(true);
        enrolledDialogFragment.show(getSupportFragmentManager(), null);
    }

    private void enrollToEvent() {
        showLoading();
        viewModel.enrollToEvent(eventModel.getId(), FirebaseAuth.getInstance().getCurrentUser().getUid())
                .observe(this, new Observer<ModelResponse<EventModel>>() {
                    @Override
                    public void onChanged(@Nullable ModelResponse<EventModel> modelResponse) {
                        dissmisLoading();
                        switch (modelResponse.getStatus()) {
                            case ModelResponse.SUCCESS:
                                Toast.makeText(DetailEventActivity.this, "Fuiste agregado al evento", Toast.LENGTH_LONG).show();
                                break;
                            case ModelResponse.ERROR:
                                Toast.makeText(DetailEventActivity.this, "Lo sentimos, ocurrió un error", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
    }

    private void updateLocationUI() {
        if (mGoogleMap == null || !checkLocalitionPermission()) {
            return;
        }
        try {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            Log.d("MAP: %s", "Current location " + location);
            if (location != null) {
                LatLng mCurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                LatLng eventLocation = new LatLng(eventModel.getLatitude(), eventModel.getLongitude());

                mGoogleMap.addMarker(new MarkerOptions()
                        .position(mCurrentLocation).title("You"));

                mGoogleMap.addMarker(new MarkerOptions()
                        .position(eventLocation).title(eventModel.getName()));

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(mCurrentLocation);
                builder.include(eventLocation);
                LatLngBounds bounds = builder.build();

                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.20); // offset from edges of the map 20% of screen

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                mGoogleMap.animateCamera(cu);
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
