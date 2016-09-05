package team.nuga.thelabel.viewholder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Message;

/**
 * Created by Tacademy on 2016-09-05.
 */
public class MessageDateLineViewHolder extends MessageParentViewHolder {

    @BindView(R.id.textView_MessageDateLine)
    TextView date;

    public MessageDateLineViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        // 그냥 오늘 날짜를 설정
    }

    @Override
    public void setMessage(Message m) {

    }

    @Override
    public void setImage(String s) {

    }
}
