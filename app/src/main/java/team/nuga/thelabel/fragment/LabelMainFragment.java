package team.nuga.thelabel.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.EntrustLeaderActivity;
import team.nuga.thelabel.FireMemberActivity;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelMainFragment extends Fragment {


    private String labelName;

    @BindView(R.id.button_entrustLeader)
    Button entrustLeaderbutton;
    @BindView(R.id.button_firemember)
    Button firememberbutton;
    @BindView(R.id.textView_LabelMain_LabelName)
    TextView labelNameView;

    @OnClick(R.id.button_entrustLeader)
    void entrustLeaderOnClick(){
        Intent intent = new Intent(getActivity(), EntrustLeaderActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.button_firemember)
    void firememberOnClick(){
        Intent intent = new Intent(getActivity(), FireMemberActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.button_LabelMain_back)
    public void clickBackButton(){
        team.nuga.thelabel.fragment.LabelContainerFragment parent  = (team.nuga.thelabel.fragment.LabelContainerFragment)getParentFragment();
        parent.backSelectLabel();
        // 레이블선택으로 돌아가기위해 역시 부모 프래그먼트를 얻어와 메소드를 실행
    }
    @OnClick(R.id.button_LabelMain_goSetting)
    public void clickSettingButton(){
        team.nuga.thelabel.fragment.LabelContainerFragment parent  = (team.nuga.thelabel.fragment.LabelContainerFragment)getParentFragment();
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
