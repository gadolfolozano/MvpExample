package gadolfolozano.pe.mvpexample.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.adapter.AlbumAdapter;
import gadolfolozano.pe.mvpexample.databinding.ActivityMainBinding;
import gadolfolozano.pe.mvpexample.model.AlbumModel;
import gadolfolozano.pe.mvpexample.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity {

    //@Inject
    //GetAlbumsService getAlbumsService;

    private ActivityMainBinding mBinding;
    private AlbumAdapter mAlbumAdapter;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performGetAlbums();
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

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.init();
        viewModel.getAlbums().observe(this, new Observer<List<AlbumModel>>() {
            @Override
            public void onChanged(@Nullable List<AlbumModel> albumModels) {
                mAlbumAdapter.replaceElements(albumModels);
            }
        });
    }

    protected void performGetAlbums() {
        /*getAlbumsService.setServiceListener(new ServiceListener<List<AlbumResponse>>() {
            @Override
            public void onSucess(List<AlbumResponse> response) {
                mAlbumAdapter.replaceElements(AlbumMapper.toModel(response));
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(MainActivity.this, "error: " + t, Toast.LENGTH_LONG).show();
            }
        });
        getAlbumsService.execute();*/
    }

    @Override
    protected void initializeInjector() {
        getApplicationComponent().inject(this);
    }

}
