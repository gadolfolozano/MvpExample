package gadolfolozano.pe.mvpexample.view.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adolfo on 25/02/18.
 */
@IgnoreExtraProperties
public class EventModel implements Parcelable {

    private String id;
    private String name;
    private String locale;
    private double latitude;
    private double longitude;
    private long timeStamp;

    public EventModel() {
        //Do nothing, required for map firebase
    }

    protected EventModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        locale = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        timeStamp = in.readLong();
    }

    public static final Creator<EventModel> CREATOR = new Creator<EventModel>() {
        @Override
        public EventModel createFromParcel(Parcel in) {
            return new EventModel(in);
        }

        @Override
        public EventModel[] newArray(int size) {
            return new EventModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("locale", locale);
        result.put("timeStamp", timeStamp);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(locale);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeLong(timeStamp);
    }
}
