package gadolfolozano.pe.mvpexample.view.model;

/**
 * Created by gustavo.lozano on 2/16/2018.
 */

public class AlbumModel {

    private String title;
    private String artist;
    private String urlImage;
    private String thumbnailImage;

    public AlbumModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }
}
