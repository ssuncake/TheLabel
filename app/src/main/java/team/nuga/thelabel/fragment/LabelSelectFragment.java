package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelSelectFragment extends Fragment  {


    public LabelSelectFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.button_LabelSelect_First)
    Button selectFirstLabel;

    @BindView(R.id.button_LabelSelect_Second)
    Button selectSecondLabel;

    @BindView(R.id.button_LabelSelect_Third)
    Button selecThirdLabel;



    @OnClick(R.id.button_LabelSelect_First)
    public void onSelectFirstLabel(){
        if(selectFirstLabel.getText().equals("레이블 만들기")){
            makeLabel();
        }else{
            selectLabel(user.getUserInLabelList().get(0));
        }

    }

    @OnClick(R.id.button_LabelSelect_Second)
    void onSelectSecondLabel(){
        if(selectSecondLabel.getText().equals("레이블 만들기")){
            makeLabel();
        }else{
            selectLabel(user.getUserInLabelList().get(1));
        }
    }

    @OnClick(R.id.button_LabelSelect_Third)
    void onSelectThirdLabel(){
        if(selecThirdLabel.getText().equals("레이블 만들기")){
            makeLabel();
        }else{
            selectLabel(user.getUserInLabelList().get(2));
        };
    }



    User user;
    Button[] buttons;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        user =(User) getArguments().getSerializable(MainActivity.MAINUSER);

        View view = inflater.inflate(R.layout.fragment_label_select, container, false);
        ButterKnife.bind(this,view);

       buttons = new Button[3];
       buttons[0] = selectFirstLabel;
        buttons[1] = selectSecondLabel;
        buttons[2] = selecThirdLabel;
        buttonSetting(user);


        return view;
    }

    public void selectLabel(Label label){
        LabelContainerFragment parent  = (LabelContainerFragment)getParentFragment();
        parent.selectLabel(label);

        //프레그먼트 교체가 부모프레그먼트에서 이루어져야 하기때문에 부모 프래그먼트를 getParentFragment로 호출하여
        // selectLabel을 호출한다.
    }

    public void makeLabel(){
        LabelContainerFragment parent  = (LabelContainerFragment)getParentFragment();
        parent.makeLabel();

        //위와 동일
    }

    public void buttonSetting(User user){
        if(user.getUserInLabelList()!=null)
        {
            int size = user.getUserInLabelList().size();
            this.user = user;
            for(int i =0; i<size; i++){
                buttons[i].setText(user.getUserInLabelList().get(i).getLabelName().toString());
            }
        }
    }




}
