package gadolfolozano.pe.mvpexample.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import gadolfolozano.pe.mvpexample.AndroidApplication;
import gadolfolozano.pe.mvpexample.di.component.ApplicationComponent;

/**
 * Created by gustavo.lozano on 2/20/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

}
