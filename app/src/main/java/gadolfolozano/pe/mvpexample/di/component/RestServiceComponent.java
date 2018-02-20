package gadolfolozano.pe.mvpexample.di.component;

import javax.inject.Singleton;

import dagger.Component;
import gadolfolozano.pe.mvpexample.MainActivity;
import gadolfolozano.pe.mvpexample.di.module.RestServiceModule;

/**
 * Created by gustavo.lozano on 2/20/2018.
 */
@Singleton
@Component(modules = { RestServiceModule.class })
public interface RestServiceComponent {
    void inject(MainActivity mainActivity);
}
