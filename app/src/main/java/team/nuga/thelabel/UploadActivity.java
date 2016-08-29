package team.nuga.thelabel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.fragment.MainFragment;
import team.nuga.thelabel.fragment.UploadFragment;

public class UploadActivity extends AppCompatActivity {

    public final static int MUSIC = 101;
    public final static int PHOTO = 102;
    public final static int YOUTUBE = 103;



    @BindView(R.id.layout_Upload_SelectLabel)
    LinearLayout selectLabel;
    @BindView(R.id.radioGroup_UploadRadio)
    RadioGroup radioGroup;

    @OnClick(R.id.button_Upload_Complete)
    public void ClickComplete(){

        Intent data = new Intent();
        data.putExtra(MainActivity.TABINDEX, MainFragment.NEWSFEEDTAB);
        setResult(RESULT_OK,data);
        finish();
    }


    private int uploadMode = -1;
    private User user;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_Upload);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        uploadMode = getIntent().getIntExtra(UploadFragment.UPLOADMODE,-1);
        switch(uploadMode){
            case MUSIC :
                Toast.makeText(UploadActivity.this, "음악 업로드 화면", Toast.LENGTH_SHORT).show();
                break;
            case PHOTO :
                Toast.makeText(UploadActivity.this, "사진 업로드 화면", Toast.LENGTH_SHORT).show();
                break;
            case YOUTUBE:
                Toast.makeText(UploadActivity.this, "유튜브 업로드 화면", Toast.LENGTH_SHORT).show();
                break;

        }


        user = (User)getIntent().getSerializableExtra(MainActivity.MAINUSER);
        userName = user.getUserName();

        setSupportActionBar(toolbar);
        toolbar.setTitle("업로드");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        // 레이블 선택화면 설정
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText("내 계정");
        checkBox.setChecked(true);
        selectLabel.addView(checkBox);

        if(user.getUserInLabelList()!=null && user.getUserInLabelList().size()!=0){
            for(Label l : user.getUserInLabelList()){
                String s = l.getLabelName();
                checkBox = new CheckBox(this);
                checkBox.setText(s);
                selectLabel.addView(checkBox);
            }
        }


        // 공개범위 설정화면
        RadioButton button;
        button = new RadioButton(this);
        button.setText("전체 공개");
        radioGroup.addView(button);
        button = new RadioButton(this);
        button.setText("레이블 공개");
        radioGroup.addView(button);
        button = new RadioButton(this);
        button.setText("비공개");
        radioGroup.addView(button);


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
}
