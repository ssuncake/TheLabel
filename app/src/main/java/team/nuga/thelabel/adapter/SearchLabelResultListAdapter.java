package team.nuga.thelabel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.SearchLabel;
import team.nuga.thelabel.viewholder.SearchLabelViewHolder;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class SearchLabelResultListAdapter extends RecyclerView.Adapter<SearchLabelViewHolder>
                implements SearchLabelViewHolder.OnSearchLabelItemClickListener{
    List<SearchLabel> labelitems = new ArrayList<>();
    HashSet<Integer> labelset = new HashSet<>();
    SearchLabel label;

    public void removeAllItem(){
        labelitems = new ArrayList<>();
        notifyDataSetChanged();
    }
    public void add(SearchLabel label) {
        if(labelset.contains(label.getSearchLabelId())){
        }else{
            labelset.add(label.getSearchLabelId());
            labelitems.add(label);
            notifyDataSetChanged();
        }
    }

    @Override
    public SearchLabelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_label, parent, false);
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
        public void onAdapterItemClick(View view, SearchLabel label, int position);
    }

    OnAdapterItemClickListener listener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onLabelItemClick(View view, SearchLabel label, int position) {
        if(listener!=null){
            listener.onAdapterItemClick(view , label, position);
        }
    }
}
