package gadolfolozano.pe.mvpexample.di.module;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import gadolfolozano.pe.mvpexample.data.repository.AlbumRepository;
import gadolfolozano.pe.mvpexample.viewmodel.LoginViewModel;

/**
 * Created by adolfo on 23/02/18.
 */
@Module
public class ActivityModule {

    @Provides
    ViewModelProvider.Factory providesViewModelFactory(AlbumRepository dependency) {
        return new LoginViewModel.Factory(dependency);
    }

}
