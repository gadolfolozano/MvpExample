package gadolfolozano.pe.mvpexample.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import gadolfolozano.pe.mvpexample.mapper.AlbumMapper;
import gadolfolozano.pe.mvpexample.view.model.AlbumModel;
import gadolfolozano.pe.mvpexample.data.ws.response.AlbumResponse;
import gadolfolozano.pe.mvpexample.data.ws.service.GetAlbumsService;
import gadolfolozano.pe.mvpexample.data.ws.service.ServiceListener;
import gadolfolozano.pe.mvpexample.view.model.ModelResponse;

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

    public LiveData<ModelResponse<List<AlbumModel>>> getAlbums() {
        final MutableLiveData<ModelResponse<List<AlbumModel>>> data = new MutableLiveData<>();
        final ModelResponse<List<AlbumModel>> modelResponse = new ModelResponse<>();

        getAlbumsService.setServiceListener(new ServiceListener<List<AlbumResponse>>() {
            @Override
            public void onSucess(List<AlbumResponse> response) {
                data.setValue(modelResponse.createSucces(AlbumMapper.toModel(response)));
            }

            @Override
            public void onError(Throwable t) {
                data.setValue(modelResponse.createError(null));
            }
        });
        getAlbumsService.execute();
        return data;
    }

}
