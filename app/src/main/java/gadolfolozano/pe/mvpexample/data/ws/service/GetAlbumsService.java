package gadolfolozano.pe.mvpexample.data.ws.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import gadolfolozano.pe.mvpexample.data.ws.response.AlbumResponse;
import retrofit2.Call;

/**
 * Created by gustavo.lozano on 2/16/2018.
 */
@Singleton
public class GetAlbumsService extends ServiceBase {
    private String hola;

    @Inject
    public GetAlbumsService() {
        this.hola = "";
    }

    @Override
    protected Call<List<AlbumResponse>> createCall() {
        return getAPIInterface().getAlbums(hola);
    }
}
