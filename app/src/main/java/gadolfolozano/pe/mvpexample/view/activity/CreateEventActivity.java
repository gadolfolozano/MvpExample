package gadolfolozano.pe.mvpexample.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.Calendar;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.databinding.ActivityCreateEventBinding;
import gadolfolozano.pe.mvpexample.databinding.ActivityMainBinding;
import gadolfolozano.pe.mvpexample.view.fragment.EventsFragment;

public class CreateEventActivity extends BaseActivity {

    private ActivityCreateEventBinding mBinding;

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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Toast.makeText(CreateEventActivity.this, "onMapReady", Toast.LENGTH_LONG).show();
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
        int current_day= c.get(Calendar.DAY_OF_MONTH);
        int current_year = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mBinding.editTextDate.setText(day + "/" + (month + 1) + "/" + year);
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
                mBinding.editTextHour.setText(hourOfDay + ":" + minute);
            }
        }, current_hour, current_minute, true);
        timePickerDialog.show();
    }

    @Override
    protected void prepareActivity() {
        //setSupportActionBar(mBinding.toolbar);
    }

    @Override
    protected void initializeInjector() {
    }

}
