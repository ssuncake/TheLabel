package team.nuga.thelabel.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelMakeFragment extends Fragment {


    public LabelMakeFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.button_LabelMake_Back)
    public void ClickBack(){
        LabelContainerFragment parent  = (LabelContainerFragment)getParentFragment();
        parent.backSelectLabel();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_label_make, container, false);
        ButterKnife.bind(this,view);


        return view;
    }

}
