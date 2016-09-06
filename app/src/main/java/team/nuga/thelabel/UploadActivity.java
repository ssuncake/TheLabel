package team.nuga.thelabel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.fragment.MainFragment;
import team.nuga.thelabel.fragment.UploadFragment;
import team.nuga.thelabel.fragment.UploadImageFragment;
import team.nuga.thelabel.fragment.UploadMusicFragment;
import team.nuga.thelabel.fragment.UploadYoutubeFragment;

public class UploadActivity extends AppCompatActivity {
    final int IMAGE_FROM_GALLERY = 100;
    final int IMAGE_FROM_GALLERY_FRAGMENT = 101;
    public final static int MUSIC = 101;
    public final static int PHOTO = 102;
    public final static int YOUTUBE = 103;


    @BindView(R.id.layout_Upload_SelectLabel)
    LinearLayout selectLabel;
    @BindView(R.id.radioGroup_UploadRadio)
    RadioGroup radioGroup;

    @BindView(R.id.textView_click)
    TextView textView;
    @OnClick(R.id.textView_click)
    public void onClickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_FROM_GALLERY_FRAGMENT);
    }

    @OnClick(R.id.button_Upload_Complete)
    public void ClickComplete() {
        Intent data = new Intent();
        data.putExtra(MainActivity.TABINDEX, MainFragment.USERTAB);
        setResult(RESULT_OK, data);
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
        uploadMode = getIntent().getIntExtra(UploadFragment.UPLOADMODE, -1);
        switch (uploadMode) {
            case MUSIC:
                getSupportFragmentManager().beginTransaction().replace(R.id.upload_content, new UploadMusicFragment()).commit();
                Toast.makeText(UploadActivity.this, "음악 업로드 화면", Toast.LENGTH_SHORT).show();
                break;
            case PHOTO:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_FROM_GALLERY);
                Toast.makeText(UploadActivity.this, "사진 업로드 화면", Toast.LENGTH_SHORT).show();
                break;
            case YOUTUBE:
                getSupportFragmentManager().beginTransaction().replace(R.id.upload_content, new UploadYoutubeFragment()).commit();
                Toast.makeText(UploadActivity.this, "유튜브 업로드 화면", Toast.LENGTH_SHORT).show();
                break;

        }


        user = (User) getIntent().getSerializableExtra(MainActivity.MAINUSER);
        userName = user.getUserName();

        setSupportActionBar(toolbar);
        toolbar.setTitle("업로드");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // 레이블 선택화면 설정
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText("내 계정");
        checkBox.setChecked(true);
        selectLabel.addView(checkBox);

        if (user.getUserInLabelList() != null && user.getUserInLabelList().size() != 0) {
            for (Label l : user.getUserInLabelList()) {
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

    String dataUri, dataType;
    Uri uri ;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("log","activity");
        Log.e("log"," requestCode : "+requestCode + "resultCode "+resultCode);
        if(requestCode ==IMAGE_FROM_GALLERY){
            Log.e("log","IMAGE_FROM_GALLERY");
            if(resultCode== Activity.RESULT_OK)
            {
                Fragment f=new UploadImageFragment();
                Bundle bundle = new Bundle();
                dataType = data.getType();
                uri= data.getData();
                dataUri = uri.toString();
                bundle.putString("dataUri", dataUri);
                bundle.putString("dataType", dataType);
                f.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.upload_content, f).commitAllowingStateLoss();
            }
        }
        else if(requestCode ==IMAGE_FROM_GALLERY_FRAGMENT){
            Log.e("log","IMAGE_FROM_GALLERY_FRAGMENT");
            if(resultCode== Activity.RESULT_OK)
            {
                Fragment f=new UploadImageFragment();
                Bundle bundle = new Bundle();
                dataType = data.getType();
                uri= data.getData();
                dataUri = uri.toString();
                bundle.putString("dataUri", dataUri);
                bundle.putString("dataType", dataType);
                f.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.upload_content, f).commitNowAllowingStateLoss();

            }
        }
    }
}