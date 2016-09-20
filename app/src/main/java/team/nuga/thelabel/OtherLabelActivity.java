package team.nuga.thelabel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherLabelActivity extends AppCompatActivity {
    @OnClick(R.id.imageButton_back)
    public void backButton(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_label);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_otherlabel);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        TextView text = (TextView) findViewById(R.id.text_otherlabel);
        String s = intent.getStringExtra("other");
        text.setText(s + "");
        setResult(RESULT_OK, intent);

        final TextView joinrequestimageView = (TextView) findViewById(R.id.image_joinrequest);
        joinrequestimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(OtherLabelActivity.this)
                        .setMessage("가입을 요청했습니다.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                joinrequestimageView.setText("가입대기중");

                                joinrequestimageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        new AlertDialog.Builder(OtherLabelActivity.this)
                                                .setMessage("가입 요청이 취소되었습니다.")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        joinrequestimageView.setText("가입 요청");
                                                    }
                                                })
                                                .show();
                                    }
                                });
                            }
                        })
                        .show();
            }
        });
    Button addbutton = (Button)findViewById(R.id.button_add);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherLabelActivity.this, MemberListActivity.class);
                startActivity(intent);
            }
        });
    }

}
