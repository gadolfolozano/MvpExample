package gadolfolozano.pe.mvpexample.ws;

import java.util.List;

import gadolfolozano.pe.mvpexample.ws.response.AlbumResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gustavo.lozano on 2/16/2018.
 */

public interface APIInterface {
    @GET("api/music_albums/?")
    Call<List<AlbumResponse>> getAlbums(@Query("hola") String hola);
}
