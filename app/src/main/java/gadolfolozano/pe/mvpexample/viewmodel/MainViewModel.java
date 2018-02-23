package gadolfolozano.pe.mvpexample.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import gadolfolozano.pe.mvpexample.model.AlbumModel;
import gadolfolozano.pe.mvpexample.repository.AlbumRepository;
import gadolfolozano.pe.mvpexample.ws.service.GetAlbumsService;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */

public class MainViewModel extends ViewModel {
    private LiveData<List<AlbumModel>> albums;
    private AlbumRepository albumRepository;

    /*@Inject // UserRepository parameter is provided by Dagger 2
    public UserProfileViewModel(UserRepository userRepo) {
        this.userRepo = userRepo;
    }*/

    public void init() {
        albumRepository = new AlbumRepository(new GetAlbumsService());
        if (this.albums != null) {
            return;
        }
        albums = albumRepository.getAlbums();
    }

    public LiveData<List<AlbumModel>> getAlbums() {
        return albums;
    }
}
