package gadolfolozano.pe.mvpexample;

import android.app.Application;

import gadolfolozano.pe.mvpexample.di.component.DaggerRestServiceComponent;
import gadolfolozano.pe.mvpexample.di.component.RestServiceComponent;
import gadolfolozano.pe.mvpexample.di.module.RestServiceModule;

/**
 * Created by gustavo.lozano on 2/20/2018.
 */

public class MvpApplication extends Application {
    private RestServiceComponent restServiceComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        restServiceComponent = DaggerRestServiceComponent.builder()
                .restServiceModule(new RestServiceModule())
                .build();
    }

    public RestServiceComponent getRestServiceComponent() {
        return restServiceComponent;
    }
}
