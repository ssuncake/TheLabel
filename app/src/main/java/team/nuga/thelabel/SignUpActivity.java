package team.nuga.thelabel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import team.nuga.thelabel.data.CheckEmailResult;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.CheckEmailRequest;

public class SignUpActivity extends AppCompatActivity {
    AppFunction appFunction;
    //    @BindView(R.id.TextInput_signUp_password)
    TextInputLayout inputLayout_password;
    //    @BindView(R.id.TextInput_signUp_password_check)
    TextInputLayout inputLayout_password_check;
    //    @BindView(R.id.TextInput_signUp_email)
    TextInputLayout inputLayout_email;

    @BindView(R.id.editText_signUp_email)
    EditText editText_email;
    @BindView(R.id.editText_signUp_password)
    EditText editText_password;
    @BindView(R.id.editText_signUp_password_check)
    EditText editText_passwordCheck;

    @BindView(R.id.button_checkOverlap)
    Button button_emailCheck;

    @OnClick(R.id.button_checkOverlap)
    public void onClickCheckOverlap() {
        if (validateEmail()) {
            onCheckEmail();
        }
    }

    private void onCheckEmail() {
        String email = editText_email.getText().toString();
        if (isCheckedEmail == false) {
            CheckEmailRequest emailRequest = new CheckEmailRequest(this, email);
            NetworkManager.getInstance().getNetworkData(emailRequest, new NetworkManager.OnResultListener<CheckEmailResult>() {
                @Override
                public void onSuccess(NetworkRequest<CheckEmailResult> request, CheckEmailResult result) {
                    if (result.getMatch() == 0) {
                        Log.d("Match 값 ", "" + result.getMatch());
                        Toast.makeText(SignUpActivity.this, "사용해도 좋아용", Toast.LENGTH_SHORT).show();
                        isCheckedEmail = true;
                    } else if (result.getMatch() == 1) {
                        Log.d("Match 값 ", "" + result.getMatch());
                        Toast.makeText(SignUpActivity.this, "이미 가입하신거 같네용ㅋ", Toast.LENGTH_SHORT).show();
                        isCheckedEmail = false;
                    }
                }

                @Override
                public void onFail(NetworkRequest<CheckEmailResult> request, int errorCode, String errorMessage, Throwable e) {
                    Toast.makeText(SignUpActivity.this, "네트워크 상태가 좋지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @BindView(R.id.button_nextStep_signUp)
    Button button_nextstep;

    boolean isCheckedEmail = false;
    boolean isSignUpSuccess = false;

    @OnClick(R.id.button_nextStep_signUp)                    //다음단계 버튼
    public void onClcikNextStep(View view) {
        view.requestFocus();
        String email = editText_email.getText().toString();
        String password = editText_password.getText().toString();

        if (isCheckedEmail == false) {
            onCheckEmail();
        }
        onPasswordCheck();
        if (checkBox_service.isChecked() && checkBox_personal.isChecked() && isCheckedEmail && isPasswordCheck == true) {
            if (Debug.debugmode) {
                Log.d(" 1 ", " 서비스 약관 동의 :" + checkBox_service.isChecked() + ", 개인정보 수집 동의 :" + checkBox_personal.isChecked() + ", 이메일 중복여부" + isCheckedEmail + ", 비밀번호 중복여부" + isPasswordCheck);
            }
            User signUp_user = new User();
            signUp_user.setUserEmail(email);
            signUp_user.setUserPassword(password);
            Intent intent = new Intent(this, SignUpEditActivity.class);
            intent.putExtra("signUpInfo", signUp_user);
            startActivity(intent);
        } else if (checkBox_service.isChecked() == false || checkBox_personal.isChecked() == false) {
            Toast.makeText(SignUpActivity.this, "약관에 동의 해주뗴염", Toast.LENGTH_SHORT).show();
            isSignUpSuccess = false;
            if (Debug.debugmode)
                Log.d(" 2 ", " 서비스 약관 동의 :" + checkBox_service.isChecked() + ", 개인정보 수집 동의 :" + checkBox_personal.isChecked() + ", 이메일 중복여부" + isCheckedEmail + ", 비밀번호 중복여부" + isPasswordCheck);
        } else if (isCheckedEmail == false) {
            if (validateEmail()) {
                onCheckEmail();
            }
            isSignUpSuccess = false;
            if (Debug.debugmode)
                Log.d(" 3 ", " 서비스 약관 동의 :" + checkBox_service.isChecked() + ", 개인정보 수집 동의 :" + checkBox_personal.isChecked() + ", 이메일 중복여부" + isCheckedEmail + ", 비밀번호 중복여부" + isPasswordCheck);
        } else if (isPasswordCheck == false) {
            onPasswordCheck();
            isSignUpSuccess = false;
            if (Debug.debugmode)
                Log.d(" 4 ", " 서비스 약관 동의 :" + checkBox_service.isChecked() + ", 개인정보 수집 동의 :" + checkBox_personal.isChecked() + ", 이메일 중복여부" + isCheckedEmail + ", 비밀번호 중복여부" + isPasswordCheck);
        }


    }

    boolean isPasswordCheck = false;

    //패스워드 비교 처리
    private void onPasswordCheck() {
        String password = editText_password.getText().toString();
        String passwodCheck = editText_passwordCheck.getText().toString();
        if (!TextUtils.isEmpty(password) && password.equals(passwodCheck)) {
            editText_password.setTextColor(Color.LTGRAY);
            editText_passwordCheck.setTextColor(Color.LTGRAY);
            isPasswordCheck = true;
        } else {
            editText_password.setTextColor(Color.MAGENTA);
            editText_passwordCheck.setTextColor(Color.MAGENTA);
            Toast.makeText(SignUpActivity.this, "비밀번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
            isPasswordCheck = false;
        }
    }

    @BindView(R.id.textView_serviceAgree)
    TextView agree_service;
    @BindView(R.id.textView_personalInformation)
    TextView agree_personal;
    @BindView(R.id.checkBox_service)
    CheckBox checkBox_service;

    @OnCheckedChanged(R.id.checkBox_service)
    public void onCheckBox_service(CompoundButton view, boolean checked) {
//        decorview.requestFocus();
        editText_email.clearFocus();
        editText_password.clearFocus();
        editText_passwordCheck.clearFocus();
        view.requestFocus();
    }

    @BindView(R.id.checkBox_personal)
    CheckBox checkBox_personal;

    @OnCheckedChanged(R.id.checkBox_personal)
    public void onPersonalAgree(CompoundButton view, boolean checked) {
//        decorview.requestFocus();
        editText_email.clearFocus();
        editText_password.clearFocus();
        editText_passwordCheck.clearFocus();
        view.requestFocus();
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        appFunction = new AppFunction(this);
        decorview = getWindow().getDecorView();
        setSupportActionBar(toolbar);
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("이메일 입력 및 약관동의");
        }
        toolbar.setTitle("이메일");


        editText_email.getFreezesText();
        int color = Color.parseColor("#d0777777");
        checkBox_service.setButtonDrawable(R.drawable.oncheck_agreebutton);
        checkBox_personal.setButtonDrawable(R.drawable.oncheck_agreebutton);
        inputLayout_email = (TextInputLayout) findViewById(R.id.TextInput_signUp_email);
        inputLayout_password = (TextInputLayout) findViewById(R.id.TextInput_signUp_password);
        inputLayout_password_check = (TextInputLayout) findViewById(R.id.TextInput_signUp_password_check);
        editText_email.addTextChangedListener(new MyTextWatcher(editText_email));
        editText_password.addTextChangedListener(new MyTextWatcher(editText_password));
        editText_passwordCheck.addTextChangedListener(new MyTextWatcher(editText_passwordCheck));
        editText_password.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        editText_email.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        editText_passwordCheck.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        scrollView_parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollView_service.getParent().requestDisallowInterceptTouchEvent(false);
                scrollView_personal.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        scrollView_service.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        scrollView_personal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    @BindView(R.id.scrollView_signUp_parent)
    ScrollView scrollView_parent;
    @BindView(R.id.scrollView_signUp_service)
    ScrollView scrollView_service;
    @BindView(R.id.scrollView_signUp_personal)
    ScrollView scrollView_personal;
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//if(hasFocus){
//    decorview.requestFocus();
//}
//    }

    private View decorview;

    @Override
    public void onBackPressed() {
        decorview.requestFocus();
        appFunction.appFinished();
    }

    private void submitForm() {
        if (!validatePasswordCheck()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validatePasswordCheck() {
        if (editText_passwordCheck.getText().toString().trim().isEmpty()) {
            inputLayout_password_check.setError(getString(R.string.err_msg_password_check));
            requestFocus(editText_passwordCheck);
            return false;
        } else {
//            inputLayout_email.s(false);
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private boolean validateEmail() {
        String email = editText_email.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayout_email.setError(getString(R.string.err_msg_email));
            requestFocus(editText_email);
            return false;
        } else {
            inputLayout_email.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        if (editText_password.getText().toString().trim().isEmpty()) {
            inputLayout_password.setError(getString(R.string.err_msg_password));
            requestFocus(inputLayout_password);
            return false;
        } else {
//            inputLayout_password.setErrorEnabled();
        }

        return true;
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (view.getId() == R.id.TextInput_signUp_email) {
                isCheckedEmail = false;
            }
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.TextInput_signUp_password_check:
                    validatePasswordCheck();
                    break;
                case R.id.TextInput_signUp_email:
                    isCheckedEmail = false;
                    validateEmail();
                    break;
                case R.id.TextInput_signUp_password:
                    validatePassword();
                    break;
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
