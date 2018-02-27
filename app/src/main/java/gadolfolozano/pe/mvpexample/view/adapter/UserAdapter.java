package gadolfolozano.pe.mvpexample.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import gadolfolozano.pe.mvpexample.databinding.ItemEnrolledBinding;
import gadolfolozano.pe.mvpexample.view.model.UserModel;

/**
 * Created by gustavo.lozano on 2/26/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private List<UserModel> mDataset;

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemEnrolledBinding mBinding;

        private MyViewHolder(ItemEnrolledBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        private void bind(final UserModel item) {
            mBinding.txtTitle.setText(item.getEmail());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemEnrolledBinding binding = ItemEnrolledBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserModel item = mDataset.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public UserAdapter(List<UserModel> myDataset) {
        mDataset = myDataset;
    }

    public void replaceElements(List<UserModel> events) {
        mDataset = events;
        notifyDataSetChanged();
    }
}
