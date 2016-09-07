package team.nuga.thelabel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team.nuga.thelabel.data.User;
import team.nuga.thelabel.viewholder.MessageViewHolder;
import team.nuga.thelabel.R;


public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder>
        implements MessageViewHolder.OnMessageItemClickListener {
    List<User> items = new ArrayList<>();

    public void add(User u) {
        items.add(u);

        notifyDataSetChanged();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_messagelist, parent, false);
        MessageViewHolder holder = new MessageViewHolder(view);
        holder.setOnMessageItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
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
    public void onMessageItemClick(View view, User user, int position) {
        if (listener != null) {
            listener.onAdapterItemClick(view, user, position);
        }
    }
}
