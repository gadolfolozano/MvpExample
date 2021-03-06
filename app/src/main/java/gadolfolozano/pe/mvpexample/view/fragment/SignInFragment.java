package gadolfolozano.pe.mvpexample.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.data.entity.UserEntity;
import gadolfolozano.pe.mvpexample.databinding.FragmentSignInBinding;
import gadolfolozano.pe.mvpexample.di.component.DaggerUserComponent;
import gadolfolozano.pe.mvpexample.di.component.UserComponent;
import gadolfolozano.pe.mvpexample.util.StringValidation;
import gadolfolozano.pe.mvpexample.view.activity.LoginActivity;
import gadolfolozano.pe.mvpexample.view.model.ModelResponse;
import gadolfolozano.pe.mvpexample.viewmodel.UserViewModel;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */

public class SignInFragment extends BaseFragment {

    private static final String TAG = SignInFragment.class.getSimpleName();

    private FragmentSignInBinding mBinding;

    private TextWatcher mEmailTextWatcher;
    private TextWatcher mPasswordTextWathcer;

    private UserComponent userComponent;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    UserViewModel viewModel;

    @Override
    protected View bindViews(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void prepareActivity() {
        configureTextWatchers();
        mBinding.mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnNextClicked();
            }
        });
        mBinding.mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnRegisterClicked();
            }
        });

        viewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);
        viewModel.init();
    }

    private void onBtnRegisterClicked() {
        ((LoginActivity) getActivity()).navigateToRegister();
    }

    private void onBtnNextClicked() {
        showLoading();
        String email = mBinding.mEditTextEmail.getText().toString();
        String password = mBinding.mEditTextPassword.getText().toString();

        viewModel.signIn(email, password).observe(this, new Observer<ModelResponse<UserEntity>>() {
            @Override
            public void onChanged(@NonNull ModelResponse<UserEntity> modelResponse) {
                dissmisLoading();
                switch (modelResponse.getStatus()){
                    case ModelResponse.SUCCESS:
                        UserEntity userEntity = modelResponse.getBody();
                        Toast.makeText(getContext(), "signIn currentUser: " + userEntity.getEmailAddress(), Toast.LENGTH_LONG).show();
                        ((LoginActivity) getActivity()).navigateToMainActivity();
                        break;
                    case ModelResponse.ERROR:
                        Toast.makeText(getContext(), "Hubo un error al logear al usuario" , Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private void configureTextWatchers() {
        mBinding.mEditTextEmail.removeTextChangedListener(mEmailTextWatcher);
        if (mEmailTextWatcher == null) {
            mEmailTextWatcher = createTextWatcher();
        }
        mBinding.mEditTextEmail.addTextChangedListener(mEmailTextWatcher);


        mBinding.mEditTextPassword.removeTextChangedListener(mPasswordTextWathcer);
        if (mPasswordTextWathcer == null) {
            mPasswordTextWathcer = createTextWatcher();
        }
        mBinding.mEditTextPassword.addTextChangedListener(mPasswordTextWathcer);
    }

    private TextWatcher createTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mBinding.mBtnNext.setEnabled(validateInputs());
            }
        };
    }

    private boolean validateInputs() {
        return StringValidation.isValidEmail(mBinding.mEditTextEmail.getText().toString())
                && StringValidation.isValidPassword(mBinding.mEditTextPassword.getText().toString());
    }

    @Override
    protected void initializeInjector() {
        userComponent = DaggerUserComponent.builder()
                .build();
        userComponent.inject(this);
    }
}
