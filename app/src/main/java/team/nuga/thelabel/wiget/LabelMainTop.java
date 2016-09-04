package team.nuga.thelabel.wiget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Label;

/**
 * Created by Tacademy on 2016-09-02.
 */
public class LabelMainTop extends LinearLayout {

    
    Boolean isNeed=true;
    Label label;

    @BindView(R.id.button_LabelMainTop_RequestJoin)
    Button requestJoin;
    @BindView(R.id.linearLayout_LabelMainTop_NeedPosition)
    LinearLayout needPositionLayout;
    @BindView(R.id.imageView_LabelMainTop_Like)
    ImageView likeHeart;
    @BindView(R.id.textView_LabelMainTop_Like)
    TextView amountLike;
    @BindView(R.id.textView_LabelMainTop_LabelName)
    TextView labelName;
    @BindView(R.id.textView_LabelMainTop_Genre)
    TextView labelGanre;
    @BindView(R.id.textView_LabelMainTop_Profile)
    TextView labelText;


    public LabelMainTop(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    public void init(){
        inflate(getContext(),R.layout.view_labelmain_top,this);
        ButterKnife.bind(this);

    }

    public void setLabel(Label label){
        if(label!=null){
            this.label = label;
            Log.w("레이블 메인탑뷰","전달받은 레이블 : "+label.getLabelName());
            // 레이블이 구인중일 때 표시
            if(!isNeed){
                needPositionLayout.setVisibility(INVISIBLE);
            }else{
                // 니드포지션해서 추가
            }
            labelName.setText(label.getLabelName());
            labelGanre.setText(label.getLabelGenre());
//           labelText.setText(label.getLabelProfile());


        }else{
            Log.e("레이블 메인탑뷰","받은 레이블이 Null");
        }
    }

    public void setIsMyLabel(boolean b){
        //내 레이블이 아닐경우 표시
        if(b){
            requestJoin.setVisibility(INVISIBLE);
        }else{
            requestJoin.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "가입요청 누름", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
