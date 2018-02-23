package gadolfolozano.pe.mvpexample.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.databinding.FragmentSignInBinding;
import gadolfolozano.pe.mvpexample.util.StringValidation;
import gadolfolozano.pe.mvpexample.view.activity.LoginActivity;

/**
 * Created by gustavo.lozano on 2/23/2018.
 */

public class SignInFragment extends BaseFragment {

    private final String TAG = SignInFragment.class.getSimpleName();

    private FragmentSignInBinding mBinding;

    private TextWatcher mEmailTextWatcher;
    private TextWatcher mPasswordTextWathcer;

    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
    }

    private void onBtnRegisterClicked() {
        ((LoginActivity) getActivity()).navigateToRegister();
    }

    private void onBtnNextClicked() {
        showLoading();
        String email = mBinding.mEditTextEmail.getText().toString();
        String password = mBinding.mEditTextPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dissmisLoading();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            ((LoginActivity) getActivity()).navigateToMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
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
        getApplicationComponent().inject(this);
    }
}
