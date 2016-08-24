package team.nuga.thelabel.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelContainerFragment extends Fragment {


    public LabelContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_label_container, container, false);
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,new LabelSelectFragment()).commit();
        // 처음 탭화면에서 레이블을 선택하게되면 FragmentContainer가 호출되고 그아래에 차일드프래그먼트로 바로 LabelSelectFragment를 호출한다.

        return view;
    }

    public void selectLabel(String labelName){
        // 하위 프래그먼트인 LabelSelectFragment에서 레이블을 선택하게 되면, 이 메소드를 통해 프래그먼트를 Main프래그먼트로 교체해주는 역할을 하는 메소드


        Bundle bundle = new Bundle();
        bundle.putString("LabelName",labelName);
        LabelMainFragment selectedLabelFragment = new LabelMainFragment();
        selectedLabelFragment.setArguments(bundle);
        // 레이블 이름을 전달하기위해 번들을 생성해주고 putString으로 번들안에 레이블이름을 넣어둔다. 그러한 번들을 프래그먼트로 셋팅


        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,selectedLabelFragment).addToBackStack(null).commit();
        // 번들이 포함된 프래그먼트로 교체해주며 이때 addToBackStack을 통해 돌아올 시점을 만든다. popBackStack을 실시하게되면
        // 비긴트랙젝션부터 커밋까지의 내용이 취소된다.
    }


    public void makeLabel(){
        // 위와 마찬가지로 하위프래그먼트인 레이블선택프래그먼트에서 만들기버튼을 누르면 MakeLabel프래그먼트로 교체를 위한 역할을 한다.
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,new LabelMakeFragment()).addToBackStack(null).commit();
    }

    public void backSelectLabel(){
        getChildFragmentManager().popBackStack();
    }
    // 하위프레그먼트에서 돌아가기버튼을 누르면 마지막으로 addtoBackStack한 트랜젝션이 실행되기 전으로 간다.

    public void labelSetting(String labelName){
        // 하위프래그먼트에서 레이블 세팅을 위해 만들어둔 메소드. 위와 동일
        Bundle bundle = new Bundle();
        bundle.putString("LabelName",labelName);
        LabelSettingFragment selectedLabelFragment = new LabelSettingFragment();
        selectedLabelFragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,selectedLabelFragment).addToBackStack(null).commit();
    }

}
