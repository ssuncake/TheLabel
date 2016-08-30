package team.nuga.thelabel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.request.LoginRequest;


public class SignInFragment extends Fragment {

    public SignInFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.button_SignIn_moveMain)
    Button loginButton;

    @BindView(R.id.button_SignIn_moveSignUp)
    Button moveSignUp;

    @OnClick(R.id.button_SignIn_moveMain)
    public void OnClickLogin(){
        String dummyEmail = "nam@gamil.com";
        String dummyPassword = "1101";

        LoginRequest request = new LoginRequest(getContext(),dummyEmail,dummyPassword);
        IntroActivity introActivity = (IntroActivity)getActivity();
        introActivity.moveMainActivity();
    }

    @OnClick(R.id.button_SignIn_moveSignUp)
    public void OnClickSignUp(){
        IntroActivity introActivity = (IntroActivity)getActivity();
        introActivity.signUpFragmentCall();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this,view);


        // Inflate the layout for this fragment
        return view;
    }
}

