package gadolfolozano.pe.mvpexample.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import gadolfolozano.pe.mvpexample.databinding.ItemEventBinding;
import gadolfolozano.pe.mvpexample.util.Constanst;
import gadolfolozano.pe.mvpexample.view.model.EventModel;

/**
 * Created by gustavo.lozano on 2/16/2018.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private List<EventModel> mDataset;
    private RecyclerAdapterListener recyclerAdapterListener;

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemEventBinding mBinding;

        private MyViewHolder(ItemEventBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        private void bind(final EventModel item) {
            mBinding.txtTitle.setText(item.getName());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constanst.DATE_FORMAT, Locale.getDefault());
            Date date = new Date(item.getTimeStamp());
            mBinding.txtDate.setText(simpleDateFormat.format(date));

            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerAdapterListener!=null){
                        recyclerAdapterListener.onItemClick(item);
                    }
                }
            });
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

    public void setRecyclerAdapterListener(RecyclerAdapterListener listener){
        this.recyclerAdapterListener = listener;
    }

    public interface RecyclerAdapterListener {
        void onItemClick(EventModel selectedItem);
    }
}
