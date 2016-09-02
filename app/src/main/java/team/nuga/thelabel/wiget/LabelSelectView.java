package team.nuga.thelabel.wiget;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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

    public ImageView settingView;
    TextView labelTitleView;

    public void init() {
        inflate(getContext(), R.layout.view_label_select, this);
        settingView = (ImageView) findViewById(R.id.imageview_label_select_setting);
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



    public void setLabel(Label label) {
        this.label = label;
        settingView.setVisibility(VISIBLE);
        labelTitleView.setText(label.getLabelName());
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
