package team.nuga.thelabel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.InviteNotification;
import team.nuga.thelabel.viewholder.NotificationInviteViewHolder;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class NotificationInviteAdapter extends RecyclerView.Adapter<NotificationInviteViewHolder>  {

    List<InviteNotification> inviteNotificationList = new ArrayList<>();

    public void addNotification(InviteNotification input){
        inviteNotificationList.add(input);
        notifyDataSetChanged();
    }

    @Override
    public NotificationInviteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_invite,parent,false);
        NotificationInviteViewHolder holder = new NotificationInviteViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(NotificationInviteViewHolder holder, int position) {
        holder.setNotification(inviteNotificationList.get(position));
    }

    @Override
    public int getItemCount() {
        return inviteNotificationList.size();
    }


}
