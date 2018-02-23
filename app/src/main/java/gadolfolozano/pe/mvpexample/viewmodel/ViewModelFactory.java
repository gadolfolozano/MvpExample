package gadolfolozano.pe.mvpexample.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */
@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {
    private MainViewModel mViewModel;

    @Inject
    public ViewModelFactory(MainViewModel viewModel) {
        this.mViewModel = viewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) mViewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
