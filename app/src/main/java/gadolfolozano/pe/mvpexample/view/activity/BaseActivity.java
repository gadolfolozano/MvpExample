package gadolfolozano.pe.mvpexample.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import gadolfolozano.pe.mvpexample.AndroidApplication;
import gadolfolozano.pe.mvpexample.di.component.ApplicationComponent;

/**
 * Created by gustavo.lozano on 2/20/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();
        bindViews();
        prepareActivity();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getComponent();
    }

    protected abstract void bindViews();

    protected abstract void prepareActivity();

    protected abstract void initializeInjector();

    protected void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.show();
    }

    protected void dissmisLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}
