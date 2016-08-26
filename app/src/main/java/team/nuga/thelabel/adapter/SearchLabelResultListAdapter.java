package team.nuga.thelabel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.R;
import team.nuga.thelabel.viewholder.SearchLabelViewHolder;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class SearchLabelResultListAdapter extends RecyclerView.Adapter<SearchLabelViewHolder>
                implements SearchLabelViewHolder.OnSearchLabelItemClickListener{
    List<Label> labelitems = new ArrayList<>();

    public void add(Label label) {
        labelitems.add(label);
        notifyDataSetChanged();
    }

    @Override
    public SearchLabelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_message_recycler, parent, false);
        SearchLabelViewHolder holder = new SearchLabelViewHolder(view);
        holder.setOnSearchItemClickListener(this);
        return holder;
    }

    Context context;
    @Override
    public void onBindViewHolder(SearchLabelViewHolder holder, int position) {
            holder.setLabel(labelitems.get(position));
    }

    @Override
    public int getItemCount() {
        return labelitems.size();
    }
    public interface OnAdapterItemClickListener {
        public void onAdapterItemClick(View view, Label user, int position);
    }

    OnAdapterItemClickListener listener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onLabelItemClick(View view, Label label, int position) {
        if(listener!=null){
            listener.onAdapterItemClick(view , label, position);
        }
    }
}
