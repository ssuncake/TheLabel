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

/**
 * Created by Tacademy on 2016-09-02.
 */
public class LabelMainMemberViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_LabelMainMember_MemberName)
    TextView memberName;
    @BindView(R.id.textView_LabelMainMember_MemberPosition)
    TextView memberPosition;
    @BindView(R.id.imageView_LabelMainMember_MemberImage)
    ImageView memberImage;

    public LabelMainMemberViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setUser(Member member){
        memberName.setText(member.getUser_nickname());
        memberPosition.setText(member.getUser_possition());
        Glide.with(memberImage.getContext())
                .load(member.getUser_imagepath())
                .into(memberImage);
    }
}
