package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelContainerFragment extends Fragment {

    User user;



    public LabelContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User)getArguments().getSerializable("dummyUser");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_label_container, container, false);


        LabelSelectFragment labelSelectFragment =  new LabelSelectFragment();
        Bundle b  = new Bundle();
        b.putSerializable("dummyUser",user);
        labelSelectFragment.setArguments(b);
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,labelSelectFragment).commit();
        // 처음 탭화면에서 레이블을 선택하게되면 FragmentContainer가 호출되고 그아래에 차일드프래그먼트로 바로 LabelSelectFragment를 호출한다.

        return view;
    }

    public void selectLabel(Label label){
        // 하위 프래그먼트인 LabelSelectFragment에서 레이블을 선택하게 되면, 이 메소드를 통해 프래그먼트를 Main프래그먼트로 교체해주는 역할을 하는 메소드


        Bundle bundle = new Bundle();
        String labelName = label.getLabelName().toString();
        bundle.putString("LabelName",labelName);
        team.nuga.thelabel.fragment.LabelMainFragment selectedLabelFragment = new team.nuga.thelabel.fragment.LabelMainFragment();
        selectedLabelFragment.setArguments(bundle);
        // 레이블 이름을 전달하기위해 번들을 생성해주고 putString으로 번들안에 레이블이름을 넣어둔다. 그러한 번들을 프래그먼트로 셋팅


        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,selectedLabelFragment).addToBackStack(null).commit();
        // 번들이 포함된 프래그먼트로 교체해주며 이때 addToBackStack을 통해 돌아올 시점을 만든다. popBackStack을 실시하게되면
        // 비긴트랙젝션부터 커밋까지의 내용이 취소된다.
    }


    public void makeLabel(){

        LabelMakeFragment labelMakeFragment = new LabelMakeFragment();
        Bundle b = new Bundle();
        b.putSerializable("dummyUser",user);
        labelMakeFragment.setArguments(b);
        // 위와 마찬가지로 하위프래그먼트인 레이블선택프래그먼트에서 만들기버튼을 누르면 MakeLabel프래그먼트로 교체를 위한 역할을 한다.
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,labelMakeFragment).addToBackStack(null).commit();
    }

    public void backSelectLabel(){
        getChildFragmentManager().popBackStack();
    }



    public void successMakeLabel(User user){
        FragmentManager fm = getChildFragmentManager();
        getChildFragmentManager().popBackStack(null, fm.POP_BACK_STACK_INCLUSIVE);
        LabelSelectFragment labelSelectFragment =  new LabelSelectFragment();
        Bundle b  = new Bundle();
        b.putSerializable("dummyUser",user);
        labelSelectFragment.setArguments(b);
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,labelSelectFragment).commit();
    }




    public void labelSetting(String labelName){
        // 하위프래그먼트에서 레이블 세팅을 위해 만들어둔 메소드. 위와 동일
        Bundle bundle = new Bundle();
        bundle.putString("LabelName",labelName);
        team.nuga.thelabel.fragment.LabelSettingFragment selectedLabelFragment = new team.nuga.thelabel.fragment.LabelSettingFragment();
        selectedLabelFragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,selectedLabelFragment).addToBackStack(null).commit();
    }

}
