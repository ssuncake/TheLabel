package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserMainFragment extends Fragment {

    User user;

    @BindView(R.id.textView_UserMain_UsrName)
    TextView userName;

    public UserMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_main, container, false);
        ButterKnife.bind(this,view);
        user = (User)getArguments().getSerializable(MainActivity.MAINUSER);
        userName.setText(user.getUserName()+" 의 계정입니다.");

        return view;
    }

}
