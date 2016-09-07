package team.nuga.thelabel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team.nuga.thelabel.data.User;
import team.nuga.thelabel.R;
import team.nuga.thelabel.viewholder.SearchUserViewHolder;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class SearchUserResultListAdapter extends RecyclerView.Adapter<SearchUserViewHolder>
        implements SearchUserViewHolder.OnSearchUserItemClickListener {
    List<User> useritems = new ArrayList<>();

    public void add(User user) {
        useritems.add(user);
        notifyDataSetChanged();
    }

    @Override
    public SearchUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_messagelist, parent, false);
        SearchUserViewHolder holder = new SearchUserViewHolder(view);
        holder.setOnSearchItemClickListener(this);
        return holder;
    }
    Context mContext;
    @Override
    public void onBindViewHolder(SearchUserViewHolder holder, int position) {
        holder.setUser(useritems.get(position));
    }

    @Override
    public int getItemCount() {
        return useritems.size();
    }

    public interface OnAdapterItemClickListener {
        public void onAdapterItemClick(View view, User user, int position);
    }

    OnAdapterItemClickListener listener;

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onUserItemClick(View view, User user, int position) {
        if (listener != null) {
            listener.onAdapterItemClick(view, user, position);
        }
    }
}
