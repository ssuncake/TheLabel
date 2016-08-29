package team.nuga.thelabel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.editText_signUp_email)
    EditText email;
    @BindView(R.id.editText_signUp_password)
    EditText password;
    @BindView(R.id.editText_signUp_password_check)
    EditText passwordCheck;
    @BindView(R.id.button_checkOverlap)
    Button emailCheck;
    @OnClick(R.id.button_checkOverlap)
    public void onClickCheckOverlap(){
        Toast.makeText(SignUpActivity.this, "중복확인 클릭..", Toast.LENGTH_SHORT).show();
    }
    @BindView(R.id.button_nextStep_signUp)
    Button nextstep;
    @OnClick(R.id.button_nextStep_signUp)
    public void onClcikNextStep(){
        Intent intent = new Intent(this, SignUpEditActivity.class );
//        intent.putExtra()
        startActivity(intent);
//        finish();
    }



    @BindView(R.id.textView_serviceAgree)
    TextView agree_service;
    @BindView(R.id.textView_personalInformation)
    TextView agree_personal;
    @BindView(R.id.checkBox_service)
    CheckBox checkBox_service;
    @BindView(R.id.checkBox_personal)
    CheckBox checkBox_personal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        email.getFreezesText();


    }
}
