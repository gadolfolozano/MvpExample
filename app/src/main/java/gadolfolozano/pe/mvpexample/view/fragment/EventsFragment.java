package gadolfolozano.pe.mvpexample.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.databinding.FragmentEventsBinding;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */

public class EventsFragment extends BaseFragment {

    private final String TAG = EventsFragment.class.getSimpleName();

    private FragmentEventsBinding mBinding;

    /*@Inject
    ViewModelProvider.Factory mViewModelFactory;

    UserViewModel viewModel;

    private UserComponent userComponent;*/


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
    }


    @Override
    protected void initializeInjector() {
        /*userComponent = DaggerUserComponent.builder()
                .build();
        userComponent.inject(this);*/
    }
}
