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
import gadolfolozano.pe.mvpexample.view.model.UserModel;

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

    public LiveData<ModelResponse<EventModel>> saveEvent(final EventModel eventModel, final String userId) {

        final MutableLiveData<ModelResponse<EventModel>> data = new MutableLiveData<>();
        final ModelResponse<EventModel> modelResponse = new ModelResponse<>();

        DatabaseReference myRef = mDatabase.getReference("events");
        final String eventId = myRef.push().getKey();
        DatabaseReference eventRef = myRef.child(eventId);
        eventRef.setValue(eventModel);

        DatabaseReference myUserEventsRef = mDatabase.getReference("users").child(userId).child("user-events")
                .child(eventId);
        myUserEventsRef.setValue(eventModel);

        mDatabase.getReference("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final UserModel userModel = dataSnapshot.getValue(UserModel.class);
                userModel.setId(dataSnapshot.getKey());
                mDatabase.getReference("events").child(eventId).child("enrolled").child(userId).setValue(userModel);
                data.setValue(modelResponse.createSucces(eventModel));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                data.setValue(modelResponse.createError(null));
            }
        });

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
                    EventModel eventModel = postSnapshot.getValue(EventModel.class);
                    eventModel.setId(postSnapshot.getKey());
                    eventModels.add(eventModel);
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

    public LiveData<ModelResponse<EventModel>> enrollToEvent(final String eventId, final String userId) {

        final MutableLiveData<ModelResponse<EventModel>> data = new MutableLiveData<>();
        final ModelResponse<EventModel> modelResponse = new ModelResponse<>();

        mDatabase.getReference("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final UserModel userModel = dataSnapshot.getValue(UserModel.class);
                userModel.setId(dataSnapshot.getKey());

                mDatabase.getReference("events").child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        EventModel eventModel = dataSnapshot.getValue(EventModel.class);

                        mDatabase.getReference("events").child(eventId).child("enrolled").child(userId).setValue(userModel);
                        mDatabase.getReference("users").child(userId).child("user-events").child(eventId).setValue(eventModel);

                        data.setValue(modelResponse.createSucces(eventModel));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        data.setValue(modelResponse.createError(null));
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                data.setValue(modelResponse.createError(null));
            }
        });

        return data;
    }

    public LiveData<ModelResponse<List<UserModel>>> getEnrolleds(String eventId) {
        DatabaseReference ref = mDatabase.getReference("events").child(eventId).child("enrolled");

        final MutableLiveData<ModelResponse<List<UserModel>>> data = new MutableLiveData<>();
        final ModelResponse<List<UserModel>> modelResponse = new ModelResponse<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<UserModel> userModels = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UserModel userModel = postSnapshot.getValue(UserModel.class);
                    userModels.add(userModel);
                }
                data.setValue(modelResponse.createSucces(userModels));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                data.setValue(modelResponse.createError(null));
            }
        });
        return data;
    }

}
