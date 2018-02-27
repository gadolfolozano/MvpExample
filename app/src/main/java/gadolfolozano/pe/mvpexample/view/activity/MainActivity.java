package gadolfolozano.pe.mvpexample.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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
        mBinding.fab.setVisibility(View.GONE);
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Do Nothing
            }

            @Override
            public void onPageSelected(int position) {
                if(position==1) {
                    mBinding.fab.setVisibility(View.VISIBLE);
                } else {
                    mBinding.fab.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Do Nothing
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
        overridePendingTransition(R.anim.transition_right_to_left, R.anim.transition_right_to_left_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CREATE_EVENT_REQUEST:
                if (resultCode == RESULT_OK) {
                    ((EventsFragment) mBinding.viewPager.getAdapter().instantiateItem(mBinding.viewPager, 0)).loadEvents();
                    ((EventsFragment) mBinding.viewPager.getAdapter().instantiateItem(mBinding.viewPager, 1)).loadEvents();
                }
                break;
        }
    }

    private void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
        overridePendingTransition(R.anim.transition_left_to_right, R.anim.transition_left_to_right_out);
    }

    @Override
    protected void initializeInjector() {
        //Do nothing
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
            switch (position) {
                case 0:
                    return getString(R.string.text_my_events);
                case 1:
                    return getString(R.string.text_other_events);
                default:
                    throw new IllegalStateException("Not fragment defined");
            }
        }
    }

}
