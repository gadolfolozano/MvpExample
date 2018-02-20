package gadolfolozano.pe.mvpexample;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gadolfolozano.pe.mvpexample.adapter.AlbumAdapter;
import gadolfolozano.pe.mvpexample.databinding.ActivityMainBinding;
import gadolfolozano.pe.mvpexample.mapper.AlbumMapper;
import gadolfolozano.pe.mvpexample.model.AlbumModel;
import gadolfolozano.pe.mvpexample.ws.response.AlbumResponse;
import gadolfolozano.pe.mvpexample.ws.service.GetAlbumsService;
import gadolfolozano.pe.mvpexample.ws.service.ServiceListener;

public class MainActivity extends AppCompatActivity {

    @Inject
    GetAlbumsService getAlbumsService;

    private ActivityMainBinding mBinding;

    private AlbumAdapter mAlbumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mAlbumAdapter = new AlbumAdapter(new ArrayList<AlbumModel>());

        mBinding.mRecyclerView.setHasFixedSize(true);
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mBinding.mRecyclerView.setAdapter(mAlbumAdapter);
        onPrepareActivity();
    }

    private void onPrepareActivity(){
        initializeDagger();
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

    private void initializeDagger() {
        MvpApplication application = (MvpApplication) getApplication();
        application.getRestServiceComponent().inject(this);
    }

}
