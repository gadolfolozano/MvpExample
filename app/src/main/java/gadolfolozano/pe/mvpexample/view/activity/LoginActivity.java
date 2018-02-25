package gadolfolozano.pe.mvpexample.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

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
    //private EventAdapter mAlbumAdapter;

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
        /*mAlbumAdapter = new EventAdapter(new ArrayList<AlbumModel>());
        mBinding.mRecyclerView.setHasFixedSize(true);
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mBinding.mRecyclerView.setAdapter(mAlbumAdapter);*/

        mAuth = FirebaseAuth.getInstance();

        viewModel = ViewModelProviders.of(this, mViewModelFactory).get(LoginViewModel.class);
        viewModel.init();
        viewModel.getAlbums().observe(this, new Observer<ModelResponse<List<AlbumModel>>>() {
            @Override
            public void onChanged(@Nullable ModelResponse<List<AlbumModel>> modelResponse) {
                //mAlbumAdapter.replaceElements(albumModels);
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
        Toast.makeText(this, "currentUser: " + currentUser, Toast.LENGTH_LONG).show();
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
    }
}
