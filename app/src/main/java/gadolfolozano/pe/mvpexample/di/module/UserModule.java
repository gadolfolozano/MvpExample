package gadolfolozano.pe.mvpexample.di.module;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import gadolfolozano.pe.mvpexample.data.repository.UserRepository;
import gadolfolozano.pe.mvpexample.viewmodel.UserViewModel;

/**
 * Created by adolfo on 23/02/18.
 */
@Module
public class UserModule {

    @Provides
    ViewModelProvider.Factory providesViewModelFactory(UserRepository dependency) {
        return new UserViewModel.Factory(dependency);
    }

}
