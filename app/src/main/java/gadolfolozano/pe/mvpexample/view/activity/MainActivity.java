package gadolfolozano.pe.mvpexample.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

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
    }

    private void onFabClicked() {
        navigateToCreateEvent();
    }

    private void navigateToCreateEvent(){
        startActivityForResult(new Intent(this, CreateEventActivity.class), CREATE_EVENT_REQUEST);
    }

    @Override
    protected void initializeInjector() {
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private int count;

        private ScreenSlidePagerAdapter(FragmentManager fm, int count) {
            super(fm);
            this.count = count;
        }

        @Override
        public Fragment getItem(int position) {
            return new EventsFragment();
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
