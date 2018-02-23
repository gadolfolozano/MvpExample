package gadolfolozano.pe.mvpexample.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import gadolfolozano.pe.mvpexample.view.model.AlbumModel;
import gadolfolozano.pe.mvpexample.data.repository.AlbumRepository;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */

public class LoginViewModel extends ViewModel {
    private LiveData<List<AlbumModel>> albums;
    private AlbumRepository albumRepository;

    @Inject // AlbumRepository parameter is provided by Dagger 2
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
}
