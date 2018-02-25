package gadolfolozano.pe.mvpexample.di.module;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import gadolfolozano.pe.mvpexample.data.repository.EventRepository;
import gadolfolozano.pe.mvpexample.viewmodel.EventViewModel;

/**
 * Created by adolfo on 25/02/18.
 */
@Module
public class EventModule {
    @Provides
    ViewModelProvider.Factory providesViewModelFactory(EventRepository dependency) {
        return new EventViewModel.Factory(dependency);
    }
}
