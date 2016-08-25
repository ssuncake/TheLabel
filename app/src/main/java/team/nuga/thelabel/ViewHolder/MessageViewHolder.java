package team.nuga.thelabel.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import team.nuga.thelabel.Data.User;
import team.nuga.thelabel.R;

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
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener !=null){
                    listener.onMessageItemClick(view, user, getAdapterPosition());
                }
            }
        });
        userphotoView = (ImageView)itemView.findViewById(R.id.image_user);
        usernameView = (TextView)itemView.findViewById(R.id.text_username);
    }
    User user;

    public void setUser(User user){
        this.user = user;
        userphotoView.setImageDrawable(user.getUserProfileImage());
        usernameView.setText(user.getUserName());
    }
}
