package gadolfolozano.pe.mvpexample.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import java.util.List;

import gadolfolozano.pe.mvpexample.data.repository.AlbumRepository;
import gadolfolozano.pe.mvpexample.view.model.AlbumModel;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */

public class LoginViewModel extends ViewModel {
    private LiveData<List<AlbumModel>> albums;
    private AlbumRepository albumRepository;

    public LoginViewModel(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public void init() {
        if (this.albums != null) {
            return;
        }
        albums = albumRepository.getAlbums();
    }

    public LiveData<List<AlbumModel>> getAlbums() {
        return albums;
    }


    public static class Factory implements ViewModelProvider.Factory {
        private final AlbumRepository dependency;

        public Factory(AlbumRepository dependency) {
            this.dependency = dependency;
        }

        @SuppressWarnings("unchecked")
        @Override
        public LoginViewModel create(Class modelClass) {
            return new LoginViewModel(dependency);
        }
    }
}
