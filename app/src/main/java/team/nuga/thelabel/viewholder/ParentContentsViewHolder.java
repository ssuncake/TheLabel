package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.User;

/**
 * Created by Tacademy on 2016-08-31.
 */
public abstract class ParentContentsViewHolder extends RecyclerView.ViewHolder {

    protected User user;
    protected Contents contents;
    public ParentContentsViewHolder(View itemView) {
        super(itemView);
//            titleView =(TextView)itemView.findViewById(R.id.textView_profile_title);
//            subTitleView=(TextView)itemView.findViewById(R.id.textView_profile_subtitle);
//            descriptionView = (TextView)itemView.findViewById(R.id.textView_profile_description);
    }

    public void setData(User user,Contents contents) {
        this.user = user;
        this.contents = contents;
        applyData(user,contents);
    }

    public void setProfile(User user){
        this.user = user;
        applyData(user ,null);
    }

    public abstract void applyData(User user, Contents contents);
}