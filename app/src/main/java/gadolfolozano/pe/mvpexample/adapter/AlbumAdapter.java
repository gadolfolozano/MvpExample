package gadolfolozano.pe.mvpexample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import gadolfolozano.pe.mvpexample.databinding.ItemAlbumBinding;
import gadolfolozano.pe.mvpexample.model.AlbumModel;

/**
 * Created by gustavo.lozano on 2/16/2018.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {
    private List<AlbumModel> mDataset;

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemAlbumBinding mBinding;

        private MyViewHolder(ItemAlbumBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onClick(View view) {

        }

        private void bind(AlbumModel item) {
            mBinding.txtTitle.setText(item.getTitle());
            mBinding.txtDescription.setText(item.getArtist());

            Glide.with(mBinding.getRoot()).load(item.getUrlImage()).into(mBinding.imageView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemAlbumBinding binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AlbumModel item = mDataset.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public AlbumAdapter(List<AlbumModel> myDataset) {
        mDataset = myDataset;
    }

    public void replaceElements(List<AlbumModel> albumModels) {
        mDataset = albumModels;
        notifyDataSetChanged();
    }
}
