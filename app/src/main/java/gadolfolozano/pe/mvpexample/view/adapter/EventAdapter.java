package gadolfolozano.pe.mvpexample.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import gadolfolozano.pe.mvpexample.databinding.ItemEventBinding;
import gadolfolozano.pe.mvpexample.view.model.EventModel;

/**
 * Created by gustavo.lozano on 2/16/2018.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private List<EventModel> mDataset;

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemEventBinding mBinding;

        private MyViewHolder(ItemEventBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onClick(View view) {

        }

        private void bind(EventModel item) {
            mBinding.txtTitle.setText(item.getName());
            mBinding.txtDescription.setText(item.getLatitude() + "");

            //Glide.with(mBinding.getRoot()).load(item.getUrlImage()).into(mBinding.imageView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemEventBinding binding = ItemEventBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventModel item = mDataset.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public EventAdapter(List<EventModel> myDataset) {
        mDataset = myDataset;
    }

    public void replaceElements(List<EventModel> events) {
        mDataset = events;
        notifyDataSetChanged();
    }
}
