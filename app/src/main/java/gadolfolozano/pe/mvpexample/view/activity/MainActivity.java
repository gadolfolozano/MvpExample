package gadolfolozano.pe.mvpexample.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gadolfolozano.pe.mvpexample.R;
import gadolfolozano.pe.mvpexample.adapter.AlbumAdapter;
import gadolfolozano.pe.mvpexample.databinding.ActivityMainBinding;
import gadolfolozano.pe.mvpexample.di.HasComponent;
import gadolfolozano.pe.mvpexample.di.component.AlbumComponent;
import gadolfolozano.pe.mvpexample.di.component.DaggerAlbumComponent;
import gadolfolozano.pe.mvpexample.mapper.AlbumMapper;
import gadolfolozano.pe.mvpexample.model.AlbumModel;
import gadolfolozano.pe.mvpexample.ws.response.AlbumResponse;
import gadolfolozano.pe.mvpexample.ws.service.GetAlbumsService;
import gadolfolozano.pe.mvpexample.ws.service.ServiceListener;

public class MainActivity extends BaseActivity implements HasComponent<AlbumComponent> {

    private AlbumComponent albumComponent;

    @Inject
    GetAlbumsService getAlbumsService;

    private ActivityMainBinding mBinding;
    private AlbumAdapter mAlbumAdapter;

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
    }

    protected void performGetAlbums(){
        getAlbumsService.setServiceListener(new ServiceListener<List<AlbumResponse>>() {
            @Override
            public void onSucess(List<AlbumResponse> response) {
                mAlbumAdapter.replaceElements(AlbumMapper.toModel(response));
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(MainActivity.this, "error: " + t, Toast.LENGTH_LONG).show();
            }
        });
        getAlbumsService.execute();
    }

    @Override
    protected void initializeInjector() {
        this.albumComponent = DaggerAlbumComponent.builder()
                //.applicationComponent(getApplicationComponent())
                //.activityModule(getActivityModule())
                .build();
        this.albumComponent.inject(this);
    }

    @Override
    public AlbumComponent getComponent() {
        return this.albumComponent;
    }
}
