package team.nuga.thelabel.Adapther;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team.nuga.thelabel.Data.User;
import team.nuga.thelabel.R;
import team.nuga.thelabel.ViewHolder.SearchUserViewHolder;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class SearchUserResultAdapter extends RecyclerView.Adapter<SearchUserViewHolder>
        implements SearchUserViewHolder.OnSearchItemClickListener {
    List<User> items = new ArrayList<>();

    public void add(User u) {
        items.add(u);

        notifyDataSetChanged();
    }

    @Override
    public SearchUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_message_recycler, parent, false);
        SearchUserViewHolder holder = new SearchUserViewHolder(view);
        holder.setOnSearchItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchUserViewHolder holder, int position) {
        holder.setUser(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
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
