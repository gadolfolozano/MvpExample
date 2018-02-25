package gadolfolozano.pe.mvpexample.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.databinding.ActivityMainBinding;
import gadolfolozano.pe.mvpexample.view.fragment.EventsFragment;

public class MainActivity extends BaseActivity {

    private static final int CREATE_EVENT_REQUEST = 100;

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void bindViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void prepareActivity() {
        mBinding.viewPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager(), 2));
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        setSupportActionBar(mBinding.toolbar);
        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabClicked();
            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(currentUser.getUid());
        myRef.child("email").setValue(currentUser.getEmail());
    }

    private void onFabClicked() {
        navigateToCreateEvent();
    }

    private void navigateToCreateEvent() {
        startActivityForResult(new Intent(this, CreateEventActivity.class), CREATE_EVENT_REQUEST);
    }

    private void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void initializeInjector() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            navigateToLogin();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private int count;

        private ScreenSlidePagerAdapter(FragmentManager fm, int count) {
            super(fm);
            this.count = count;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return EventsFragment.newInstance(EventsFragment.TYPE_MY_EVENTS);
                case 1:
                    return EventsFragment.newInstance(EventsFragment.TYPE_OTHERS_EVENTS);
                default:
                    throw new IllegalStateException("Not fragment defined");
            }
        }

        @Override
        public int getCount() {
            return count;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "fragment " + position;
        }
    }

}
