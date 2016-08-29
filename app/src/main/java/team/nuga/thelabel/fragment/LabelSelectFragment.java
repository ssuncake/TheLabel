package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.wiget.LabelSelectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelSelectFragment extends Fragment  {

    List<Label> userlabellist;


    public LabelSelectFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.layout_LabelSelect)
    RelativeLayout relativeLayout;
    @BindView(R.id.layout_LabelSelectFrist)
    RelativeLayout relativeLayoutFrist;
    @BindView(R.id.layout_LabelSelectSecond)
    RelativeLayout relativeLayoutSecond;


    User user;
    Button[] buttons;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_label_select, container, false);
        ButterKnife.bind(this,view);

        LabelSelectView labelSelectView = new LabelSelectView(getContext());
        LabelSelectView labelSelectViewFirst = new LabelSelectView(getContext());
        LabelSelectView labelSelectViewSecond = new LabelSelectView(getContext());

        relativeLayout.addView(labelSelectView); //CustomView 추가 하는 방법
        relativeLayoutFrist.addView(labelSelectViewFirst);
        relativeLayoutSecond.addView(labelSelectViewSecond);

        user =(User) getArguments().getSerializable(MainActivity.MAINUSER);
        userlabellist = user.getUserInLabelList();
//        labelSelectView.setLabel(userlabellist.get(3));


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
