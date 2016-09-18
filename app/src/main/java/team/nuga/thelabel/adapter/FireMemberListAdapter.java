package team.nuga.thelabel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.viewholder.FireMemberViewHolder;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class FireMemberListAdapter extends RecyclerView.Adapter<FireMemberViewHolder>implements FireMemberViewHolder.OnFireMemberListItemClickListener {
    List<User> useritems = new ArrayList<>();
    int mainUserid = -1;

    public void setMainUserid(int mainUserid) {
        this.mainUserid = mainUserid;
    }

    public void add(User user) {
        useritems.add(user);
        notifyDataSetChanged();
    }

    @Override
    public FireMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_firemember_recycler, parent, false);
        FireMemberViewHolder holder = new FireMemberViewHolder(view);
        holder.setOnFireMemberListItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(FireMemberViewHolder holder, int position) {
        if(mainUserid!=-1){
            holder.setUser(useritems.get(position));
        }

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
    public void onUserItemClick(View view, User user, int adapterPosition) {
        if (useritems != null) {
            listener.onAdapterItemClick(view, user, adapterPosition);
        }
    }
}
