package team.nuga.thelabel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.LikeNotification;
import team.nuga.thelabel.viewholder.NotificationLikeViewHolder;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class NotificationLikeAdapter extends RecyclerView.Adapter<NotificationLikeViewHolder> implements NotificationLikeViewHolder.onLikeNotificationClickListener {

    List<LikeNotification> likeNotificationList = new ArrayList<>();

    public void addNotification(LikeNotification input){
        likeNotificationList.add(input);
        notifyDataSetChanged();
    }

    @Override
    public NotificationLikeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_like,parent,false);
        NotificationLikeViewHolder holder = new NotificationLikeViewHolder(view);
        holder.setOnLikeNotificationClickListener(this);
        return holder;
    }


    @Override
    public void onBindViewHolder(NotificationLikeViewHolder holder, int position) {
        holder.setNotification(likeNotificationList.get(position));
    }

    @Override
    public int getItemCount() {
        return likeNotificationList.size();
    }

    public interface onAdapterItemClickListener{
        public void onAdapterItemClick(View view, LikeNotification notification,int position);
    }

    onAdapterItemClickListener listener;

    public void setOnAdapterItemClickListener(onAdapterItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onLikeNotificationItemClick(View view, LikeNotification notification, int position) {
        if(listener!=null)
        {
            listener.onAdapterItemClick(view,notification,position);
        }
    }
}
