package gadolfolozano.pe.mvpexample.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import gadolfolozano.pe.mvpexample.mapper.AlbumMapper;
import gadolfolozano.pe.mvpexample.model.AlbumModel;
import gadolfolozano.pe.mvpexample.ws.response.AlbumResponse;
import gadolfolozano.pe.mvpexample.ws.service.GetAlbumsService;
import gadolfolozano.pe.mvpexample.ws.service.ServiceListener;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */
@Singleton
public class AlbumRepository {

    private GetAlbumsService getAlbumsService;

    @Inject
    public AlbumRepository(GetAlbumsService getAlbumsService) {
        this.getAlbumsService = getAlbumsService;
    }

    public LiveData<List<AlbumModel>> getAlbums() {
        final MutableLiveData<List<AlbumModel>> data = new MutableLiveData<>();

        getAlbumsService.setServiceListener(new ServiceListener<List<AlbumResponse>>() {
            @Override
            public void onSucess(List<AlbumResponse> response) {
                data.setValue(AlbumMapper.toModel(response));
            }

            @Override
            public void onError(Throwable t) {

            }
        });
        getAlbumsService.execute();
        return data;
    }

}
