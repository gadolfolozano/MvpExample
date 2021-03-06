package gadolfolozano.pe.mvpexample.view.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.databinding.FragmentEventsBinding;
import gadolfolozano.pe.mvpexample.di.component.DaggerEventComponent;
import gadolfolozano.pe.mvpexample.di.component.EventComponent;
import gadolfolozano.pe.mvpexample.view.activity.DetailEventActivity;
import gadolfolozano.pe.mvpexample.view.adapter.EventAdapter;
import gadolfolozano.pe.mvpexample.view.model.EventModel;
import gadolfolozano.pe.mvpexample.view.model.ModelResponse;
import gadolfolozano.pe.mvpexample.viewmodel.EventViewModel;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */

public class EventsFragment extends BaseFragment {

    private static final String TAG = EventsFragment.class.getSimpleName();

    private FragmentEventsBinding mBinding;

    public static final int TYPE_MY_EVENTS = 1;
    public static final int TYPE_OTHERS_EVENTS = 2;
    private static final String TYPE_EVENT = "type_event";

    private int mEventsType;

    private EventAdapter mEventAdapter;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private EventViewModel viewModel;

    private EventComponent eventComponent;

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
        viewModel = ViewModelProviders.of(this, mViewModelFactory).get(EventViewModel.class);
        viewModel.init();

        mEventsType = getArguments().getInt(TYPE_EVENT, TYPE_OTHERS_EVENTS);

        mEventAdapter = new EventAdapter(new ArrayList<EventModel>());
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mBinding.recyclerView.setAdapter(mEventAdapter);
        mEventAdapter.setRecyclerAdapterListener(new EventAdapter.RecyclerAdapterListener() {
            @Override
            public void onItemClick(EventModel selectedItem) {
                navigateToDetailEvent(selectedItem);
            }
        });

        loadEvents();
    }

    private void navigateToDetailEvent(EventModel selectedItem){
        Intent intent = new Intent(getActivity(), DetailEventActivity.class);
        intent.putExtra(DetailEventActivity.EXTRA_EVENT, selectedItem);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.transition_right_to_left, R.anim.transition_right_to_left_out);
    }

    @Override
    protected void initializeInjector() {
        eventComponent = DaggerEventComponent.builder()
                .build();
        eventComponent.inject(this);
    }

    public void loadEvents() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        LiveData<ModelResponse<List<EventModel>>> observable;
        if (mEventsType == TYPE_MY_EVENTS) {
            observable = viewModel.getEvents(currentUser.getUid());
        } else {
            observable = viewModel.getEvents();
        }

        observable.observe(this, new Observer<ModelResponse<List<EventModel>>>() {
            @Override
            public void onChanged(@Nullable ModelResponse<List<EventModel>> modelResponse) {
                switch (modelResponse.getStatus()) {
                    case ModelResponse.SUCCESS:
                        mEventAdapter.replaceElements(modelResponse.getBody());
                        break;
                    case ModelResponse.ERROR:
                        Toast.makeText(getContext(), "Hubo un error al recuperar datos", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }
}
