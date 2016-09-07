package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.RoundImageTransform;
import team.nuga.thelabel.data.User;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class MessageViewHolder extends RecyclerView.ViewHolder {

    User user;
    @BindView(R.id.image_MessageList_userimage)
    ImageView userphotoView;
    @BindView(R.id.textView_MessageList_UserName)
    TextView usernameView;

    public interface OnMessageItemClickListener{
        public void onMessageItemClick(View view, User user, int position);
    }
    OnMessageItemClickListener listener;
    public void setOnMessageItemClickListener(OnMessageItemClickListener listener){
        this.listener = listener;
    }

    public MessageViewHolder( View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener !=null){
                    listener.onMessageItemClick(view,user, getAdapterPosition());
                }
            }
        });

    }


    public void setData(String name,String ImagePath){
        // 나중에 이미지도 설정
        usernameView.setText(name);
        Glide.with(userphotoView.getContext())
                .load(ImagePath)
                .transform(new RoundImageTransform(userphotoView.getContext()))
                .into(userphotoView);
    }

    public void setUser(User user){
        this.user = user;
    }




}
