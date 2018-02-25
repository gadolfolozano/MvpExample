package gadolfolozano.pe.mvpexample.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import gadolfolozano.pe.mvpexample.view.model.EventModel;
import gadolfolozano.pe.mvpexample.view.model.ModelResponse;

/**
 * Created by adolfo on 25/02/18.
 */
@Singleton
public class EventRepository {

    FirebaseDatabase mDatabase;

    @Inject
    public EventRepository() {
        mDatabase = FirebaseDatabase.getInstance();
    }

    public LiveData<ModelResponse<EventModel>> saveEvent(EventModel eventModel, String userId) {

        final MutableLiveData<ModelResponse<EventModel>> data = new MutableLiveData<>();
        final ModelResponse<EventModel> modelResponse = new ModelResponse<>();

        DatabaseReference myRef = mDatabase.getReference("events");
        String key_event = myRef.push().getKey();
        DatabaseReference eventRef = myRef.child(key_event);
        eventRef.setValue(eventModel);

        DatabaseReference myUserEventsRef = mDatabase.getReference("users").child(userId).child("user-events")
                .child(key_event);
        myUserEventsRef.setValue(eventModel);

        data.setValue(modelResponse.createSucces(eventModel));

        return data;
    }

    public LiveData<ModelResponse<List<EventModel>>> getEvents() {
        return getEvents(mDatabase.getReference("events"));
    }

    public LiveData<ModelResponse<List<EventModel>>> getEvents(String userId) {
        return getEvents(mDatabase.getReference("users").child(userId).child("user-events"));
    }

    private LiveData<ModelResponse<List<EventModel>>> getEvents(DatabaseReference databaseReference) {
        final MutableLiveData<ModelResponse<List<EventModel>>> data = new MutableLiveData<>();
        final ModelResponse<List<EventModel>> modelResponse = new ModelResponse<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<EventModel> eventModels = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    /*EventModel eventModel = new EventModel();
                    eventModel.setName(postSnapshot.child("name").getValue(String.class));
                    eventModel.setLatitude(postSnapshot.child("latitude").getValue(Double.class));
                    eventModel.setLongitude(postSnapshot.child("longitude").getValue(Double.class));*/
                    eventModels.add(postSnapshot.getValue(EventModel.class));
                }
                data.setValue(modelResponse.createSucces(eventModels));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                data.setValue(modelResponse.createError(null));
            }
        });
        return data;
    }

}
