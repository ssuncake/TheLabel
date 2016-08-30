package team.nuga.thelabel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
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
        String dummyEmail = "nam@gmail.com";
        String dummyPassword = "1101";

        LoginRequest request = new LoginRequest(getContext(),dummyEmail,dummyPassword);

        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>(){
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {

                String message = result.getMessage();
                Log.e("로그인 성공",message);
                User user = result.getUser();
                Log.e("유저 정보 확인",user.getEmail()+" // "+ user.getUserName());

                IntroActivity introActivity = (IntroActivity) getActivity();
                introActivity.moveMainActivity(user);
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                Log.e("로그인 실패",errorMessage);
            }
        });

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

