package team.nuga.thelabel.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-09-05.
 */
public class MessageOtherViewHolder extends MessageParentViewHolder {

    @BindView(R.id.textView_MessageOther_Text)
    TextView message;
    @BindView(R.id.textView_MessageOther_Date)
    TextView date;
    @BindView(R.id.imageView_MessageOther_ImageView)
    ImageView image;

    public MessageOtherViewHolder(View itemView) {
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

    public void setImage(String s){
        //이미지 설정
    }


}
