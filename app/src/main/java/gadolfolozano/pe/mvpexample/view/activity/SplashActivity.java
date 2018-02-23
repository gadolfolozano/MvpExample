package gadolfolozano.pe.mvpexample.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int secondsDelayed = 2;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }

    @Override
    protected void bindViews() {
        //Do nothing
    }

    @Override
    protected void prepareActivity() {
        //Do nothing
    }

    @Override
    protected void initializeInjector() {
        //Do nothing
    }
}
