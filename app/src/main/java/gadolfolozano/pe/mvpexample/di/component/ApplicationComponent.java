package gadolfolozano.pe.mvpexample.di.component;

import javax.inject.Singleton;

import dagger.Component;
import gadolfolozano.pe.mvpexample.di.module.ApplicationModule;
import gadolfolozano.pe.mvpexample.view.activity.LoginActivity;
import gadolfolozano.pe.mvpexample.view.activity.MainActivity;
import gadolfolozano.pe.mvpexample.view.fragment.RegisterFragment;
import gadolfolozano.pe.mvpexample.view.fragment.SignInFragment;

/**
 * Created by gustavo.lozano on 2/20/2018.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity baseActivity);

    void inject(MainActivity baseActivity);

    void inject(RegisterFragment fragmentRegisterBinding);

    void inject(SignInFragment fragmentRegisterBinding);
}
