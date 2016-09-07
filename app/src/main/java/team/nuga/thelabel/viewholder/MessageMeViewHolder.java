package team.nuga.thelabel.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Message;

/**
 * Created by Tacademy on 2016-09-05.
 */
public class MessageMeViewHolder extends MessageParentViewHolder {

    @BindView(R.id.textView_MessageMe_Text)
    TextView message;
    @BindView(R.id.textView_MessageMe_Date)
    TextView date;

    public MessageMeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setMessage(String m){
        if(m!=null){
            message.setText(m);
        }else{
            Log.d("메세지 me 뷰홀더","setMessage null");
        }
    }


    @Override
    public void setImage(String s) {
        // 설정해줄필요 없음
    }
}
