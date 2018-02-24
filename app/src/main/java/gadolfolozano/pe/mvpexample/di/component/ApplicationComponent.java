package gadolfolozano.pe.mvpexample.di.component;

import javax.inject.Singleton;

import dagger.Component;
import gadolfolozano.pe.mvpexample.di.module.ApplicationModule;

/**
 * Created by gustavo.lozano on 2/20/2018.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
}
