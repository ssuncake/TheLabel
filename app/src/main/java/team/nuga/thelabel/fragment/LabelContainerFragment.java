package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.nuga.thelabel.MainActivity;
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
        user = (User)getArguments().getSerializable(MainActivity.MAINUSER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_label_container, container, false);
        init();
        return view;
    }

    public void init(){
        LabelSelectFragment labelSelectFragment =  new LabelSelectFragment();
        Bundle b  = new Bundle();
        b.putSerializable(MainActivity.MAINUSER,user);
        labelSelectFragment.setArguments(b);
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,labelSelectFragment).commit();
        //처음 탭화면에서 레이블을 선택하게되면 FragmentContainer가 호출되고 그아래에 차일드프래그먼트로 바로 LabelSelectFragment를 호출한다.
    }

    public void selectLabel(Label label){


        Bundle bundle = new Bundle();
        bundle.putInt(MainActivity.LABELID,label.getLabelID());
        bundle.putSerializable(MainActivity.MAINUSER,user);
        LabelMainFragment selectedLabelFragment = new LabelMainFragment();
        selectedLabelFragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,selectedLabelFragment).addToBackStack(null).commit();

        // 하위 프래그먼트인 LabelSelectFragment에서 레이블을 선택하게 되면, 이 메소드를 통해 프래그먼트를 Main프래그먼트로 교체해주는 역할을 하는 메소드
        // 레이블 이름을 전달하기위해 번들을 생성해주고 putString으로 번들안에 레이블아이디를 전달
        // 번들이 포함된 프래그먼트로 교체해주며 이때 addToBackStack을 통해 돌아올 시점을 만든다. popBackStack을 실시하게되면
        // 비긴트랙젝션부터 커밋까지의 내용이 취소된다.
    }

    public void goLabel(int Id){
        Bundle bundle = new Bundle();
        bundle.putInt(MainActivity.LABELID,Id);
        LabelMainFragment selectedLabelFragment = new LabelMainFragment();
        selectedLabelFragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,selectedLabelFragment).addToBackStack(null).commit();
    }

    public void refreshLabelSelect(){
        Fragment frg = null;
        frg = getChildFragmentManager().findFragmentByTag("LabelSelect");
        final FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }


    public void backSelectLabel(){
        getChildFragmentManager().popBackStack();
    }



    public void successMakeLabel(User user){
        FragmentManager fm = getChildFragmentManager();
        getChildFragmentManager().popBackStack(null, fm.POP_BACK_STACK_INCLUSIVE);
        LabelSelectFragment labelSelectFragment =  new LabelSelectFragment();
        Bundle b  = new Bundle();
        b.putSerializable(MainActivity.MAINUSER,user);
        labelSelectFragment.setArguments(b);
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,labelSelectFragment).commit();
    }






}
