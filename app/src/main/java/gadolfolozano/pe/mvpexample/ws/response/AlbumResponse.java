package gadolfolozano.pe.mvpexample.ws.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gustavo.lozano on 2/16/2018.
 */

public class AlbumResponse implements Serializable {

    @SerializedName("title")
    private String title;

    @SerializedName("artist")
    private String artist;

    @SerializedName("url")
    private String url;

    @SerializedName("image")
    private String image;

    @SerializedName("thumbnail_image")
    private String thumbnailImage;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnail_image) {
        this.thumbnailImage = thumbnail_image;
    }
}
