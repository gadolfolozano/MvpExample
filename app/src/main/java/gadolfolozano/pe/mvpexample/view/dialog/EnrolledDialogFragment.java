package gadolfolozano.pe.mvpexample.view.dialog;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.databinding.DialogFragmentEnrolledBinding;
import gadolfolozano.pe.mvpexample.di.component.DaggerEventComponent;
import gadolfolozano.pe.mvpexample.di.component.EventComponent;
import gadolfolozano.pe.mvpexample.view.adapter.UserAdapter;
import gadolfolozano.pe.mvpexample.view.model.ModelResponse;
import gadolfolozano.pe.mvpexample.view.model.UserModel;
import gadolfolozano.pe.mvpexample.viewmodel.EventViewModel;

/**
 * Created by gustavo.lozano on 2/26/2018.
 */

public class EnrolledDialogFragment extends DialogFragment {

    private static final String EVENT_ID = "event_id";

    private DialogFragmentEnrolledBinding mBinding;

    private EventComponent eventComponent;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private EventViewModel viewModel;

    private UserAdapter mUserAdapter;

    public static EnrolledDialogFragment newInstance(String eventId) {
        EnrolledDialogFragment f = new EnrolledDialogFragment();
        Bundle args = new Bundle();
        args.putString(EVENT_ID, eventId);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initializeInjector();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_enrolled, container, false);
        prepareDialog();
        formatWindow();
        return mBinding.getRoot();
    }

    private void prepareDialog(){
        String eventId = getArguments().getString(EVENT_ID);

        mUserAdapter = new UserAdapter(new ArrayList<UserModel>());
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mBinding.recyclerView.setAdapter(mUserAdapter);

        viewModel = ViewModelProviders.of(this, mViewModelFactory).get(EventViewModel.class);
        viewModel.init();
        viewModel.getEnrolleds(eventId).observe(this, new Observer<ModelResponse<List<UserModel>>>() {
            @Override
            public void onChanged(@Nullable ModelResponse<List<UserModel>> modelResponse) {
                switch (modelResponse.getStatus()) {
                    case ModelResponse.SUCCESS:
                        mUserAdapter.replaceElements(modelResponse.getBody());
                        break;
                    case ModelResponse.ERROR:
                        Toast.makeText(getContext(), "Lo sentimos, ocurrió un error", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    public void formatWindow() {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }
    }

    private void initializeInjector() {
        eventComponent = DaggerEventComponent.builder()
                .build();
        eventComponent.inject(this);
    }
}
