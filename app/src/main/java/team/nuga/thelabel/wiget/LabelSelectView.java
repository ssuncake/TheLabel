package team.nuga.thelabel.wiget;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Label;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class LabelSelectView extends FrameLayout {

    public LabelSelectView(Context context) {
        super(context);
        init();
    }
    ImageView settingView;
    TextView labelTitleView;

    public void init(){
        inflate(getContext(), R.layout.view_label_select,this);
//    settingView = (ImageView)findViewById(R.id.imageview_label_select);
    labelTitleView = (TextView)findViewById(R.id.textview_label_select);
}
    Label label;
    public void setLabel(Label label){
        labelTitleView.setText(label.getLabelName());
    }
    public Label getLabel(){
        return label;
    }

    public void setVisible(boolean b){
        labelTitleView.setVisibility(INVISIBLE);
    }

}
