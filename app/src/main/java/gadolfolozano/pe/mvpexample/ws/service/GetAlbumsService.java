package gadolfolozano.pe.mvpexample.ws.service;

import java.util.List;

import gadolfolozano.pe.mvpexample.ws.response.AlbumResponse;
import retrofit2.Call;

/**
 * Created by gustavo.lozano on 2/16/2018.
 */

public class GetAlbumsService extends ServiceBase {
    private String hola;

    public GetAlbumsService(String hola) {
        this.hola = hola;
    }

    @Override
    protected Call<List<AlbumResponse>> createCall() {
        return getAPIInterface().getAlbums(hola);
    }
}
