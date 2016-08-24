package team.nuga.thelabel.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelSelectFragment extends Fragment {


    public LabelSelectFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.button_LabelSelect_First)
    Button selectFirstLabel;

    @BindView(R.id.button_LabelSelect_Second)
    Button selectSecondLabel;

    @BindView(R.id.button_LabelSelect_Third)
    Button selecThirdtLabel;

    @BindView(R.id.button_LabelSelect_MakeLabel)
    Button makeLabel;

//    @OnClick(R.id.button_LabelSelect_First)
//    public void onSelectFirstLabel(){
//        selectLabel("1st");
//    }
//
//    @OnClick(R.id.button_LabelSelect_Second)
//    void onSelectSecondLabel(){
//        selectLabel("2nd");
//    }
//
//    @OnClick(R.id.button_LabelSelect_Third)
//    void onSelectThirdLabel(){
//        selectLabel("3rd");
//    }
//
//    @OnClick(R.id.button_LabelSelect_MakeLabel)
//    void onMakeLabel(){
//        makeLabel();
//    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_label_select, container, false);
        ButterKnife.bind(this,view);
        return view;
    }


}
