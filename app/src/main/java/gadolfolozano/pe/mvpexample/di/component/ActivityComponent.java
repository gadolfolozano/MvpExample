package gadolfolozano.pe.mvpexample.di.component;

import javax.inject.Singleton;

import dagger.Component;
import gadolfolozano.pe.mvpexample.di.module.ActivityModule;
import gadolfolozano.pe.mvpexample.view.activity.LoginActivity;

/**
 * Created by adolfo on 23/02/18.
 */

@Singleton
@Component(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);

}
