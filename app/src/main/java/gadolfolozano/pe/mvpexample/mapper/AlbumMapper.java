package gadolfolozano.pe.mvpexample.mapper;

import java.util.ArrayList;
import java.util.List;

import gadolfolozano.pe.mvpexample.view.model.AlbumModel;
import gadolfolozano.pe.mvpexample.data.ws.response.AlbumResponse;

/**
 * Created by gustavo.lozano on 2/19/2018.
 */

public class AlbumMapper {
    private AlbumMapper() {
        throw new IllegalStateException("Mapper class");
    }

    public static AlbumModel toModel(AlbumResponse response){
        AlbumModel model = new AlbumModel();
        model.setArtist(response.getArtist());
        model.setTitle(response.getTitle());
        model.setUrlImage(response.getImage());
        model.setThumbnailImage(response.getThumbnailImage());
        return model;
    }

    public static List<AlbumModel> toModel(List<AlbumResponse> responseList){
        List<AlbumModel> list = new ArrayList<>();
        for(AlbumResponse response : responseList){
            list.add(toModel(response));
        }
        return list;
    }
}
