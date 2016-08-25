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
        // 레이블선택으로 돌아가기위해 역시 부모 프래그먼트를 얻어와 메소드를 실행
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
        labelName = getArguments().getString("LabelName"); // 부모프래그먼트로부터 전달받은 번들에서 레이블 이름을꺼내 세팅
        labelNameView.setText(labelName);
    }

}
