package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSettingFragment extends Fragment {


    User user;
    @BindView(R.id.editText_ProfileSetting_inputName)
    EditText inputName;

    @OnClick(R.id.button_ProfileSetting_Complete)
    public void ClickComplete(){
        String inputUserName= inputName.getText().toString();
        user.setUserName(inputUserName);
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.drawerUserSetting(inputUserName);
        mainActivity.goMainFragment(MainFragment.USERTAB);
    }


    public ProfileSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_setting, container, false);
        ButterKnife.bind(this,view);

        user = (User)getArguments().getSerializable(MainActivity.MAINUSER);



        return view;
    }

}
