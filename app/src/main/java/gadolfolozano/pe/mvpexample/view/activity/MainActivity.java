package gadolfolozano.pe.mvpexample.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.view.adapter.AlbumAdapter;
import gadolfolozano.pe.mvpexample.databinding.ActivityMainBinding;
import gadolfolozano.pe.mvpexample.view.model.AlbumModel;
import gadolfolozano.pe.mvpexample.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBinding;
    private AlbumAdapter mAlbumAdapter;

    private MainViewModel viewModel;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void bindViews() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void prepareActivity() {
        mAlbumAdapter = new AlbumAdapter(new ArrayList<AlbumModel>());
        mBinding.mRecyclerView.setHasFixedSize(true);
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mBinding.mRecyclerView.setAdapter(mAlbumAdapter);

        viewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        viewModel.init();
        viewModel.getAlbums().observe(this, new Observer<List<AlbumModel>>() {
            @Override
            public void onChanged(@Nullable List<AlbumModel> albumModels) {
                mAlbumAdapter.replaceElements(albumModels);
            }
        });
    }

    @Override
    protected void initializeInjector() {
        getApplicationComponent().inject(this);
    }

}
