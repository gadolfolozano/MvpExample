package gadolfolozano.pe.mvpexample.view.model;

/**
 * Created by adolfo on 25/02/18.
 */

public class EventModel {

    private String name;
    private String locale;
    private double latitude;
    private double longitude;

    public EventModel(){
        //Do nothing
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
