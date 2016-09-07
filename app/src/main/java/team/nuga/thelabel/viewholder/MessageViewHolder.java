package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.User;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class MessageViewHolder extends RecyclerView.ViewHolder {
    ImageView userphotoView;
    TextView usernameView;

    public interface OnMessageItemClickListener{
        public void onMessageItemClick(View view, User user, int position);
    }
    OnMessageItemClickListener listener;
    public void setOnMessageItemClickListener(OnMessageItemClickListener listener){
        this.listener = listener;
    }
    public MessageViewHolder(final View itemView) {
        super(itemView);

        userphotoView = (ImageView)itemView.findViewById(R.id.image_user);
        usernameView = (TextView)itemView.findViewById(R.id.textView_MemberList_Name);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener !=null){
                    listener.onMessageItemClick(view, user, getAdapterPosition());
                }
            }
        });

    }
    User user;

    public void setData(String name){
        // 나중에 이미지도 설정
        usernameView.setText(name);
    }

    public void setUser(User user){
        this.user = user;
       // usernameView.setText(user.getUserName());
    }


}
