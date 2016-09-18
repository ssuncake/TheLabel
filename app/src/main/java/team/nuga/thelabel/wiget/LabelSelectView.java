package team.nuga.thelabel.wiget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Label;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class LabelSelectView extends FrameLayout {


    private boolean isEmpty = true;
    Label label;
    int index;
    Context context;

    public LabelSelectView(Context context,int index) {
        super(context);
        this.context  = context;
        this.index = index;
        init();
    }

    ImageView settingView;
    TextView labelTitleView;
    ImageView labelImage;
    FrameLayout gradient;

    public void init() {
        inflate(getContext(), R.layout.view_label_select, this);
        settingView = (ImageView) findViewById(R.id.imageview_label_select_setting);
        labelImage = (ImageView) findViewById(R.id.imageView_label_select_Profile) ;
        gradient = (FrameLayout) findViewById(R.id.fraamLayout_labelselect_gradient);
        settingView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null)
                    listener.onSettingClick(label);
            }
        });
        settingView.setVisibility(INVISIBLE);

        labelTitleView = (TextView) findViewById(R.id.textview_label_select);
    }



    public void setLabel(Label label,int userId) {

        this.label = label;
        if(label.getLabelLeaderID() == userId){
            settingView.setVisibility(VISIBLE);
        }
        gradient.setBackgroundResource(R.drawable.gradation);
        labelTitleView.setText(label.getLabelName());
        labelTitleView.setTypeface(null, Typeface.BOLD);
        labelTitleView.setTextColor(Color.WHITE);

        Log.e("레이블 선택뷰","레이블 이미지 패스: "+label.getImage_path());

        Glide.with(getContext())
                .load(label.getImage_path())
                .fitCenter()
                .into(labelImage);

        isEmpty = false;
    }

    public Label getLabel() {
        return label;
    }

    public void setVisible(boolean b) {
        labelTitleView.setVisibility(INVISIBLE);
    }

    public boolean getEmpty(){
        return isEmpty;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }





    public interface  OnSettingImageClickListener{
        public void onSettingClick(Label label);
    }

    OnSettingImageClickListener listener;

    public void setOnSettingImageClickListener(OnSettingImageClickListener listener){
        this.listener = listener;
    }
}
