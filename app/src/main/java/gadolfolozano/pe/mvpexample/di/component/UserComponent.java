package gadolfolozano.pe.mvpexample.di.component;

import javax.inject.Singleton;

import dagger.Component;
import gadolfolozano.pe.mvpexample.di.module.UserModule;
import gadolfolozano.pe.mvpexample.view.fragment.RegisterFragment;
import gadolfolozano.pe.mvpexample.view.fragment.SignInFragment;

/**
 * Created by adolfo on 23/02/18.
 */

@Singleton
@Component(modules = {UserModule.class})
public interface UserComponent {

    void inject(SignInFragment fragmentRegisterBinding);

    void inject(RegisterFragment fragmentRegisterBinding);

}
