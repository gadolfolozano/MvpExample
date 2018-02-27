package gadolfolozano.pe.mvpexample.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import javax.inject.Inject;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.databinding.ActivityLoginBinding;
import gadolfolozano.pe.mvpexample.di.component.ActivityComponent;
import gadolfolozano.pe.mvpexample.di.component.DaggerActivityComponent;
import gadolfolozano.pe.mvpexample.di.module.ActivityModule;
import gadolfolozano.pe.mvpexample.view.fragment.RegisterFragment;
import gadolfolozano.pe.mvpexample.view.fragment.SignInFragment;
import gadolfolozano.pe.mvpexample.view.model.AlbumModel;
import gadolfolozano.pe.mvpexample.view.model.ModelResponse;
import gadolfolozano.pe.mvpexample.viewmodel.LoginViewModel;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding mBinding;

    private LoginViewModel viewModel;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private FirebaseAuth mAuth;

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void bindViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    @Override
    protected void prepareActivity() {
        mAuth = FirebaseAuth.getInstance();

        viewModel = ViewModelProviders.of(this, mViewModelFactory).get(LoginViewModel.class);
        viewModel.init();
        viewModel.getAlbums().observe(this, new Observer<ModelResponse<List<AlbumModel>>>() {
            @Override
            public void onChanged(@Nullable ModelResponse<List<AlbumModel>> modelResponse) {
            }
        });
    }

    @Override
    protected void initializeInjector() {
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule())
                .build();
        activityComponent.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            navigateToSignIn();
        } else {
            navigateToMainActivity();
        }
    }

    public void navigateToRegister() {
        RegisterFragment registerFragment = new RegisterFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, registerFragment)
                .commitAllowingStateLoss();
    }

    public void navigateToSignIn() {
        SignInFragment signInFragment = new SignInFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, signInFragment)
                .commitAllowingStateLoss();
    }

    public void navigateToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.transition_right_to_left, R.anim.transition_right_to_left_out);
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment != null && fragment instanceof RegisterFragment) {
            navigateToSignIn();
            return;
        }

        super.onBackPressed();
    }
}
