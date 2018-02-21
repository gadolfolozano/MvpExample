package gadolfolozano.pe.mvpexample;

import android.app.Application;

import gadolfolozano.pe.mvpexample.di.component.ApplicationComponent;
import gadolfolozano.pe.mvpexample.di.component.DaggerApplicationComponent;
import gadolfolozano.pe.mvpexample.di.module.ApplicationModule;

/**
 * Created by gustavo.lozano on 2/20/2018.
 */

public class AndroidApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

}
