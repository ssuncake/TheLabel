package team.nuga.thelabel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText email,password,passwordCheck;
        Button emailCheck, nextstep;
        TextView agree_service, agree_personal;
        CheckBox checkBox_service, checkBox_personal;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
}
