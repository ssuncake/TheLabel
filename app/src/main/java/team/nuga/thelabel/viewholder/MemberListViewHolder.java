package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Member;
import team.nuga.thelabel.data.RoundImageTransform;

/**
 * Created by 우리집 on 2016-09-03.
 */
public class MemberListViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.textView_MemberList_Name)
    TextView name;
    @BindView(R.id.textView_MemberList_Leader)
    TextView isLeader;
    @BindView(R.id.image_MemberList_Image)
    ImageView profile;

    public MemberListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.isLeader.setVisibility(View.INVISIBLE);
    }

    public void setMember(Member member,boolean isLeader){
        name.setText(member.getUser_nickname());
        Glide.with(profile.getContext())
                .load(member.getUser_imagepath())
                .transform(new RoundImageTransform(profile.getContext()))
                .into(profile);
        if(isLeader){
            this.isLeader.setVisibility(View.VISIBLE);
        }
    }
}
