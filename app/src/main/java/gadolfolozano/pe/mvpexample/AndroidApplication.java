package gadolfolozano.pe.mvpexample;

import android.app.Application;

import gadolfolozano.pe.mvpexample.di.HasComponent;
import gadolfolozano.pe.mvpexample.di.component.ApplicationComponent;
import gadolfolozano.pe.mvpexample.di.component.DaggerApplicationComponent;

/**
 * Created by gustavo.lozano on 2/20/2018.
 */

public class AndroidApplication extends Application implements HasComponent<ApplicationComponent> {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder().build();
    }

    @Override
    public ApplicationComponent getComponent() {
        return this.applicationComponent;
    }
}
