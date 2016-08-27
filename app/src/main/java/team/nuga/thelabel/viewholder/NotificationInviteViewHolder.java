package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.InviteNotification;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class NotificationInviteViewHolder extends RecyclerView.ViewHolder {

    ImageView senderProfileImage;
    TextView contentsText;
    InviteNotification notification;



    public NotificationInviteViewHolder(View itemView) {
        super(itemView);

        senderProfileImage = (ImageView)itemView.findViewById(R.id.ImageView_InviteNotification);
        contentsText = (TextView)itemView.findViewById(R.id.textView_InviteNotification_Contents);

    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void setNotification(InviteNotification notification){
        this.notification  = notification;
//        this.date.setText(notification.getNotificationDate().toString());
        if(notification.getInviteMode() == InviteNotification.INVITELABEL){
        contentsText.setText(notification.getSendLabel().getLabelName()+" 에 초대되었습니다. ");
            contentsText.setTextSize(10);}
        else{
            contentsText.setText(notification.getJoinUser().getUserName()+ "님이 레이블 가입을 요청하였습니다.");
            contentsText.setTextSize(10);
        }

    }
}
