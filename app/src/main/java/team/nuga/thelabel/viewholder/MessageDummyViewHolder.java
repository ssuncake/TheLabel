package team.nuga.thelabel.viewholder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-09-05.
 */
public class MessageDummyViewHolder extends MessageParentViewHolder {
    @BindView(R.id.textView_MessageDummy)
    TextView textView;

    public MessageDummyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setMessage(String message,String date){

    }


    @Override
    public void setImage(String s) {
        // 설정해줄필요 없음
    }
}
