package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Member;

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
    }

    public void setMember(Member member){
        name.setText(member.getUser_name());

        // 사진과 리더여부 설정해줘야함
    }
}
