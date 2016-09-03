package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import team.nuga.thelabel.data.User;
import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class SearchUserViewHolder extends RecyclerView.ViewHolder {
    ImageView userphotoView;
    TextView usernameView;

    public interface OnSearchUserItemClickListener{
        public void onUserItemClick(View view, User user, int position);
    }
    OnSearchUserItemClickListener listener;
    public void setOnSearchItemClickListener(OnSearchUserItemClickListener listener){
        this.listener = listener;
    }
    public SearchUserViewHolder(final View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener !=null){
                    listener.onUserItemClick(view, user, getAdapterPosition());
                }
            }
        });
        userphotoView = (ImageView)itemView.findViewById(R.id.image_user);
        usernameView = (TextView)itemView.findViewById(R.id.textView_MemberList_Name);
    }
    User user;

    public void setUser(User user){
        this.user = user;

        usernameView.setText(user.getUserName());
    }
}
