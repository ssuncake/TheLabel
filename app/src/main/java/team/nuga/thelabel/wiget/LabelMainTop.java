package team.nuga.thelabel.wiget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.User;

/**
 * Created by Tacademy on 2016-09-02.
 */
public class LabelMainTop extends LinearLayout {

    public static final String LOGTAG ="LabelMainTopView ";
    Boolean isNeed=true;
    Label label;
    User user;

    @BindView(R.id.linearLayout_LabelMainTop_NeedPosition)
    LinearLayout needPositionLayout;
    @BindView(R.id.imageView_LabelMainTop_Like)
    ImageView likeHeart;
    @BindView(R.id.textView_LabelMainTop_Likenum)
    TextView amountLike;
    @BindView(R.id.textView_LabelMainTop_LabelName)
    TextView labelName;
    @BindView(R.id.textView_LabelMainTop_Genre)
    TextView labelGanre;
    @BindView(R.id.textView_LabelMainTop_Profile)
    TextView labelText;
    @BindView(R.id.imageView_LabelMainTop_labelImage)
    ImageView labelImage;
    @BindView(R.id.layout_LabelMainTop_Image)
    FrameLayout frameLayout;
    @BindView(R.id.frameLayout_LabelMainTop_RequestJoin)
    FrameLayout requesJoinLayout;
    @BindView(R.id.button_LabelMainTop_RequestJoin)
    ImageButton joinButton;

    public void setUser(User user) {
        this.user = user;
    }

    public LabelMainTop(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    public void init(){
        inflate(getContext(),R.layout.view_labelmain_top,this);

        ButterKnife.bind(this);
        likeHeart.setImageDrawable(getResources().getDrawable(R.drawable.btn_like_on));
        requesJoinLayout.setVisibility(INVISIBLE);

    }

    public void setLabel(Label label){
        if(label!=null){
            this.label = label;
            Log.w(LOGTAG,"전달받은 레이블 : "+label.getLabelName()+" // is need :"+ label.isNeed());
            // 레이블이 구인중일 때 표시
            if(label.isNeed()){
                for(String s : label.getLabelNeedPositionList()){
                    TextView temp = new TextView(getContext());
                    temp.setText(s);
                    temp.setTextSize(10);
                    temp.setTextColor(Color.WHITE);
                    temp.setGravity(Gravity.CENTER_HORIZONTAL);
                    temp.setBackgroundResource(R.drawable.white_rectangle);
                    needPositionLayout.addView(temp);
                    Log.e(LOGTAG,"에드뷰 성공"+temp);
                }
            }else{
                needPositionLayout.setVisibility(INVISIBLE);
                // 니드포지션해서 추가
            }
            labelName.setText(label.getLabelName());
            labelGanre.setText(label.getLabelGenre());
            labelText.setText(label.getLabelProfile());
            amountLike.setText(label.getLabelILike()+"");
            Glide.with(labelImage.getContext())
                    .load(label.getImage_path())
                    .into(labelImage);


        }else{
            Log.e("레이블 메인탑뷰","받은 레이블이 Null");
        }
    }

    public void setIsMyLabel(boolean b){
        //내 레이블이 아닐경우 표시
        if(!b){
            requesJoinLayout.setVisibility(VISIBLE);
            joinButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e(LOGTAG,"가입요청 누름 label :"+label.getLabelID()+" labelLeader = "+label.getLabelLeaderID()+" userid ="+user.getUserID());
//                    LabelJoinDemandRequest request = new LabelJoinDemandRequest(user.getUserID(),label.getLabelID(),label.getLabelLeaderID());
//                    NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResultSimpleMessage>() {
//                        @Override
//                        public void onSuccess(NetworkRequest<NetworkResultSimpleMessage> request, NetworkResultSimpleMessage result) throws ParseException {
//
//                        }
//
//                        @Override
//                        public void onFail(NetworkRequest<NetworkResultSimpleMessage> request, int errorCode, String errorMessage, Throwable e) {
//
//                        }
//                    });
                }
            });
        }
    }

}
