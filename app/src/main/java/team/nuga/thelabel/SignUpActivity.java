package team.nuga.thelabel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {
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
        Toast.makeText(SignUpActivity.this, "중복확인 클릭..", Toast.LENGTH_SHORT).show();
    }

    @BindView(R.id.button_nextStep_signUp)
    Button button_nextstep;

    @OnClick(R.id.button_nextStep_signUp) //다음단계 버튼
    public void onClcikNextStep() {
//        submitForm();
        Intent intent = new Intent(this, SignUpEditActivity.class);
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("이메일 입력 및 약관동의");
        }
        editText_email.getFreezesText();


        inputLayout_email = (TextInputLayout) findViewById(R.id.TextInput_signUp_email);
        inputLayout_password = (TextInputLayout) findViewById(R.id.TextInput_signUp_password);
        inputLayout_password_check = (TextInputLayout) findViewById(R.id.TextInput_signUp_password_check);
        editText_email.addTextChangedListener(new MyTextWatcher(editText_email));
        editText_password.addTextChangedListener(new MyTextWatcher(editText_password));
        editText_passwordCheck.addTextChangedListener(new MyTextWatcher(editText_passwordCheck));


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
//            inputLayout_email.setErrorEnabled(false);
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
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.TextInput_signUp_password_check:
                    validatePasswordCheck();
                    break;
                case R.id.TextInput_signUp_email:
                    validateEmail();
                    break;
                case R.id.TextInput_signUp_password:
                    validatePassword();
                    break;
            }
        }
    }
}
