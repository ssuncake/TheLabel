package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class AccountTypeOneViewHolder extends RecyclerView.ViewHolder{
    TextView titleView;
    TextView subTitleView;
    TextView descriptionView;
    public AccountTypeOneViewHolder(View itemView) {
        super(itemView);
        titleView =(TextView)itemView.findViewById(R.id.textView_profile_title);
        subTitleView=(TextView)itemView.findViewById(R.id.textView_profile_subtitle);
        descriptionView = (TextView)itemView.findViewById(R.id.textView_profile_description);
                }
   // User user;

//    public void setUser(User user){
//        this.user = user;
//        titleView.setText(user.getUserName());
//        subTitleView.setText(user.getUserName());
//        descriptionView.setText(user.getUserName());
//    }
}
