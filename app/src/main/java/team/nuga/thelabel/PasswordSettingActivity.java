package team.nuga.thelabel;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.PasswordChangeRequest;

public class PasswordSettingActivity extends AppCompatActivity {

    TextInputLayout textInputLayout_currentPassword;
    TextInputLayout textInputLayout_newPassword;
    TextInputLayout textInputLayout_checkNewPassword;

    @BindView(R.id.editText_password_current)
    EditText editText_currentPassword;
    @BindView(R.id.editText_password_change)
    EditText editText_newPassword;
    @BindView(R.id.editText_password_change_check)
    EditText editText_checkNewPassword;

    @BindView(R.id.button_passwordChange)
    Button button_passwordChange;

    @OnClick(R.id.button_passwordChange)
    public void onPasswordChageClick() {
        if(Debug.debugmode)Log.e("버튼 "," 변경하기 버튼 클릭");
        if (editText_newPassword.getText().toString().trim().isEmpty() == true) {
            Log.i("비밀번호", " 불린값 true:" + editText_newPassword.getText().toString().trim().isEmpty());
        } else if(editText_newPassword.getText().toString().trim().isEmpty() == false) {
            Log.i("비밀번호", " 불린값 :" + editText_newPassword.getText().toString().trim().isEmpty());
        }else{Log.i("eee","eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            //1.(앱) 현재비밀번호와 새로 설정할 비밀번호를 다르게 했는지 체크
            validateNewPassword();
            //2.(앱) 새로설정할 비밀번호와 새확인비밀번호가 같은지 체크
            validatecheckNewPassword();

            String currentPassword = editText_currentPassword.getText().toString();
            String newPassword = editText_newPassword.getText().toString();
            PasswordChangeRequest request = new PasswordChangeRequest(currentPassword, newPassword);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult> request, NetworkResult result) {

                }

                @Override
                public void onFail(NetworkRequest<NetworkResult> request, int errorCode, String errorMessage, Throwable e) {

                }
            });
            Toast.makeText(this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_setting);
        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_impormation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textInputLayout_checkNewPassword = (TextInputLayout) findViewById(R.id.TextInput_newPasswordCheck);
        textInputLayout_newPassword = (TextInputLayout) findViewById(R.id.TextInput_newPassword);
        textInputLayout_currentPassword = (TextInputLayout) findViewById(R.id.TextInput_currentPassword);
        editText_currentPassword.addTextChangedListener(new MyPassTextWatcher(editText_currentPassword));
        editText_newPassword.addTextChangedListener(new MyPassTextWatcher(editText_newPassword));
        editText_checkNewPassword.addTextChangedListener(new MyPassTextWatcher(editText_checkNewPassword));



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyPassTextWatcher implements TextWatcher {

        private View view;

        private MyPassTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.TextInput_currentPassword:
//                    validateCurrentPasswordCheck();
                    // 현재 비밀번호와 동일한지 체크
                    break;
                case R.id.TextInput_newPassword:
                    validateNewPassword();
                    //현재비밀번호와 다른지 체크
                    //비밀번호 설정을 충족하는지 체크크
                    break;
                case R.id.TextInput_newPasswordCheck:
                    validatecheckNewPassword();
                    //새 패스워드와 같은지 체크
                    break;
            }
        }
    }


    boolean clear_currentPassword = false;
    boolean clear_newPassword = false;
    boolean clear_checkPassword = false;

//    private void validateCurrentPasswordCheck() {
//        switch ()
//        if (editText_currentPassword.getText().toString().trim().isEmpty()) { //입력 ㄴㄴ시 에러체크
//            textInputLayout_currentPassword.setError(getString(R.string.err_msg_password));//입력하세요 메세지 출력
//            requestFocus(editText_currentPassword);//포커스이동
//        } else if (editText_currentPassword.getText().toString() != currentPassword) { //서버의 비밀번호와 맞는지 체크
//            textInputLayout_currentPassword.setError(getString(R.string.err_msg_currnet_password_check));   //정확한 비밀번호를 입력하세요 출력
//            requestFocus(editText_currentPassword);//포커스이동
//        } else if (editText_currentPassword.getText().toString() == currentPassword) {
//            clear_currentPassword = true;
//        }
//    }

    private boolean validateNewPassword() {
        if (editText_currentPassword.getText().toString() != editText_checkNewPassword.getText().toString()) { //입력한 현재 비밀번호와 새비밀번호 다른지 체크
            clear_newPassword = true;
        } else {
            textInputLayout_newPassword.setError(getString(R.string.err_msg_currentAndNew_password_check)); //같으면 에러메세지 같다고 출력
            requestFocus(editText_currentPassword);
            return false;
        }
        return true;
    }

    private boolean validatecheckNewPassword() {
        if (editText_newPassword.getText().toString().equals(editText_checkNewPassword.getText().toString())) { //새비밀번호와 확인비밀번호 비교
            clear_checkPassword = true;
        } else {//새로 설정한 비밀번호가 새로설정한비밀번호 확인 과 다를경우
            textInputLayout_checkNewPassword.setError(getString(R.string.err_msg_checkNewAndNew_password_check));//새비밀번호와 다릅니다. 출력
            requestFocus(editText_checkNewPassword);
            return false;
        }
        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
