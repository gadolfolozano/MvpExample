package gadolfolozano.pe.mvpexample.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gadolfolozano.pe.mvpexample.AndroidApplication;
import gadolfolozano.pe.mvpexample.di.component.ApplicationComponent;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */

public abstract class BaseFragment extends Fragment {

    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initializeInjector();
        return bindViews(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareActivity();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getActivity().getApplication()).getComponent();
    }

    protected abstract View bindViews(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract void prepareActivity();

    protected abstract void initializeInjector();

    protected void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
        }
        mProgressDialog.show();
    }

    protected void dissmisLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
