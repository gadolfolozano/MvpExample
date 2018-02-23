package gadolfolozano.pe.mvpexample.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gadolfolozano.pe.mvpexample.AndroidApplication;
import gadolfolozano.pe.mvpexample.viewmodel.MainViewModel;
import gadolfolozano.pe.mvpexample.viewmodel.ViewModelFactory;

/**
 * Created by gustavo.lozano on 2/20/2018.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    /*@Provides
    ViewModel provideMainViewModel(MainViewModel viewModel) {
        return viewModel;
    }*/

    @Provides
    ViewModelProvider.Factory provideViewModelFactory(ViewModelFactory factory) {
        return factory;
    }
}
