package team.nuga.thelabel.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelMainFragment extends Fragment {


    private String labelName;


    @BindView(R.id.textView_LabelMain_LabelName)
    TextView labelNameView;

    @OnClick(R.id.button_LabelMain_back)
    public void clickBackButton(){
        LabelContainerFragment parent  = (LabelContainerFragment)getParentFragment();
        parent.backSelectLabel();
    }
    @OnClick(R.id.button_LabelMain_goSetting)
    public void clickSettingButton(){
        LabelContainerFragment parent  = (LabelContainerFragment)getParentFragment();
        parent.labelSetting(labelName);
    }

    public LabelMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_label_main, container, false);
        ButterKnife.bind(this,view);
        labelSetting();

        return view;
    }




    public void labelSetting(){
        labelName = getArguments().getString("LabelName"); // 메인액티비티로 부터 Bundle로 전달받은 레이블 이름을 Label네임에 저장
        labelNameView.setText(labelName);
    }

}
