package gadolfolozano.pe.mvpexample.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.databinding.FragmentEventsBinding;
import gadolfolozano.pe.mvpexample.view.adapter.EventAdapter;
import gadolfolozano.pe.mvpexample.view.model.EventModel;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */

public class EventsFragment extends BaseFragment {

    private final String TAG = EventsFragment.class.getSimpleName();

    private FragmentEventsBinding mBinding;

    public static final int TYPE_MY_EVENTS = 1;
    public static final int TYPE_OTHERS_EVENTS = 2;
    private static final String TYPE_EVENT = "type_event";

    private int mEventsType;

    private EventAdapter mEventAdapter;

    /*@Inject
    ViewModelProvider.Factory mViewModelFactory;

    UserViewModel viewModel;

    private UserComponent userComponent;*/

    public static EventsFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(TYPE_EVENT, type);
        EventsFragment fragment = new EventsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View bindViews(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_events, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void prepareActivity() {
        /*mBinding.mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnNextClicked();
            }
        });*/

        /*viewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);
        viewModel.init();*/
        mEventsType = getArguments().getInt(TYPE_EVENT, TYPE_OTHERS_EVENTS);

        mEventAdapter = new EventAdapter(new ArrayList<EventModel>());
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mBinding.recyclerView.setAdapter(mEventAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef;

        if (mEventsType == TYPE_MY_EVENTS) {
            myRef = database.getReference("users").child(currentUser.getUid()).child("user-events");
        } else {
            myRef = database.getReference("events");
        }

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<EventModel> eventModels = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "postSnapshot " + postSnapshot);
                    EventModel eventModel = new EventModel();
                    eventModel.setName(postSnapshot.child("name").getValue(String.class));
                    eventModel.setLatitude(postSnapshot.child("latitude").getValue(Double.class));
                    eventModel.setLongitude(postSnapshot.child("longitude").getValue(Double.class));
                    eventModels.add(eventModel);
                }
                mEventAdapter.replaceElements(eventModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void initializeInjector() {
        /*userComponent = DaggerUserComponent.builder()
                .build();
        userComponent.inject(this);*/
    }
}
