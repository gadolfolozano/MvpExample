package gadolfolozano.pe.mvpexample.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

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
    }

    @Override
    protected void initializeInjector() {
        getApplicationComponent().inject(this);
    }

}
