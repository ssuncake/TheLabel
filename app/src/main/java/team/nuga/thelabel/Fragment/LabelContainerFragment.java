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
        return view;
    }

    public void selectLabel(String labelName){

        Bundle bundle = new Bundle();
        bundle.putString("LabelName",labelName);
        LabelMainFragment selectedLabelFragment = new LabelMainFragment();
        selectedLabelFragment.setArguments(bundle);


        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,selectedLabelFragment).addToBackStack(null).commit();
    }


    public void makeLabel(){
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,new LabelMakeFragment()).addToBackStack(null).commit();
    }

    public void backSelectLabel(){
        getChildFragmentManager().popBackStack();
    }

    public void labelSetting(String labelName){
        Bundle bundle = new Bundle();
        bundle.putString("LabelName",labelName);
        LabelSettingFragment selectedLabelFragment = new LabelSettingFragment();
        selectedLabelFragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_LabelContainer,selectedLabelFragment).addToBackStack(null).commit();
    }

}
