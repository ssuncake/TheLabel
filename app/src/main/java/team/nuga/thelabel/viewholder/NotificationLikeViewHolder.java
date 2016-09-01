package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.LikeNotification;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class NotificationLikeViewHolder extends RecyclerView.ViewHolder {

    ImageView senderProfileImage;
    TextView contentsText;
    TextView date;
    LikeNotification notification;

    public interface onLikeNotificationClickListener{
        public void onLikeNotificationItemClick(View view, LikeNotification notification, int position);
    }
    onLikeNotificationClickListener listener;

    public void setOnLikeNotificationClickListener(onLikeNotificationClickListener listener){
        this.listener = listener;
    }

    public NotificationLikeViewHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null) {
                    listener.onLikeNotificationItemClick(view, notification, getAdapterPosition());
                }
            }
        });
        senderProfileImage = (ImageView)itemView.findViewById(R.id.ImageView_LikeNotification);
        contentsText = (TextView)itemView.findViewById(R.id.textView_LikeNotification_Contents);
        date = (TextView)itemView.findViewById(R.id.textView_LikeNotification_Date);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void setNotification(LikeNotification notification){
        this.notification  = notification;
//        this.date.setText(notification.getNotificationDate().toString());
        contentsText.setText(notification.getSender().getUserName()+" 님이 "+" 게시물을 좋아합니다.");

    }
}
