package team.nuga.thelabel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.DBManager;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.manager.PropertyManager;
import team.nuga.thelabel.request.LoginRequest;

public class TestLoginActivity extends AppCompatActivity {

    @OnClick(R.id.button_testlogin_1)
    public void click1()
    {
        String email ="nam@gmail.com";
        String password="1101";

        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }
    @OnClick(R.id.button_testlogin_2)
    public void click2()
    {
        String email ="gyu@gmail.com";
        String password="1102";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }
    @OnClick(R.id.button_testlogin_3)
    public void click3()
    {
        String email ="soo@gmail.com";
        String password="1103";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }
    @OnClick(R.id.button_testlogin_4)
    public void click4()
    {
        String email ="seul@gmail.com";
        String password="1104";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);    }
    @OnClick(R.id.button_testlogin_5)
    public void click5()
    {
        String email ="moon@gmail.com";
        String password="1105";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }
    @OnClick(R.id.button_testlogin_6)
    public void click6()
    {
        String email ="do@gmail.com";
        String password="1106";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }
    @OnClick(R.id.button_testlogin_7)
    public void click7()
    {
        String email ="ki@gmail.com";
        String password="1107";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }
    @OnClick(R.id.button_testlogin_8)
    public void click8()
    {
        String email ="ho@gmail.com";
        String password="1108";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }
    @OnClick(R.id.button_testlogin_9)
    public void click9()
    {
        String email ="doo@gmail.com";
        String password="1109";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }
    @OnClick(R.id.button_testlogin_10)
    public void click10()
    {
        String email ="bin@gmail.com";
        String password="1110";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }
    @OnClick(R.id.button_testlogin_11)
    public void click11()
    {
        String email ="pcy@gmail.com";
        String password="1111";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }
    @OnClick(R.id.button_testlogin_12)
    public void click12()
    {
        String email ="yumi@gmail.com";
        String password="1112";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }
    @OnClick(R.id.button_testlogin_13)
    public void click13()
    {
        String email ="areum@gmail.com";
        String password="1113";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }
    @OnClick(R.id.button_testlogin_14)
    public void click14()
    {
        String email ="hyun@gmail.com";
        String password="1114";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }
    @OnClick(R.id.button_testlogin_15)
    public void click15()
    {
        String email ="lee4@gmail.com";
        String password="1115";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    } @OnClick(R.id.button_testlogin_16)
    public void click16()
    {
        String email ="ko@gmail.com";
        String password="1116";
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);
        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }


    @BindView(R.id.editText_testinpuid)
    EditText inputid;

    @BindView(R.id.editText_testinputpassword)
    EditText inputpassword;

    @OnClick(R.id.button_testlogin)
    public void clicklogin(){
        String email = inputid.getText().toString();
        String password = inputpassword.getText().toString();

        //자동로그인 기능 : 이메일 세팅
        String regid = PropertyManager.getInstance().getRegistrationId();
        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);

        String regId = PropertyManager.getInstance().getRegistrationId();
        goMain(email,password,regId);
    }


@BindView(R.id.seekBar_login)
SeekBar progressBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_login);
        ButterKnife.bind(this);


        progressBar.setMax(100);
        progressBar.setProgress(50);
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
              if(i>99)  {moveToLogin();}
                if(i<1){moteToSignUp();}
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


    }

    private void moteToSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void moveToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public void goMain(String email,String password, String regid){
        LoginRequest request = new LoginRequest(this,email,password,regid);

        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>(){
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {



                String message = result.getMessage();
                Log.e("로그인 성공",message);
                User user = result.getUser();
                DBManager.getInstance().setMainUser(user);
                Log.e("로그인 유저 정보 확인",user.getEmail()+" // "+ user.getUserName());

                startMainAc(user);
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(TestLoginActivity.this, "로그인 실패 !+", Toast.LENGTH_SHORT).show();
                Log.e("로그인 실패",errorMessage);
            }
        });
    }

    public void startMainAc(User user){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("LoginUser",user);
        startActivity(intent);
        finish();
    }


}
