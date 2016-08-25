package team.nuga.thelabel.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.Data.Label;
import team.nuga.thelabel.Data.User;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelMakeFragment extends Fragment {


    User user;

    Label newLabel;

    @BindView(R.id.editText_MakeLabel_InputName)
    EditText inputName;


    public LabelMakeFragment() {
        // Required empty public constructor
    }


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       user = (User)getArguments().getSerializable("dummyUser");
    }

    @OnClick(R.id.button_LabelMake_Back)
    public void ClickBack(){
        LabelContainerFragment parent  = (LabelContainerFragment)getParentFragment();
        String labelName = inputName.getText().toString();
        newLabel = new Label();
        newLabel.setLabelName(labelName);
        user.addLabelList(newLabel);

        parent.successMakeLabel(user);
//        parent.backSelectLabel();
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
