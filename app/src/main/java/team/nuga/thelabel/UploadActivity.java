package team.nuga.thelabel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.fragment.UploadFragment;
import team.nuga.thelabel.fragment.UploadImageFragment;
import team.nuga.thelabel.fragment.UploadMusicFragment;
import team.nuga.thelabel.fragment.UploadYoutubeFragment;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.UploadImageContentRequest;

public class UploadActivity extends AppCompatActivity {
    public final static String LOGTAG = "UploadActivity ";

    final int IMAGE_FROM_GALLERY = 100;
    final int IMAGE_FROM_GALLERY_FRAGMENT = 101;
    public final static int MUSIC = 101;
    public final static int PHOTO = 102;
    public final static int YOUTUBE = 103;
    int labelId = -1;
    int opento = 0;

    Label[] labels;



    @BindView(R.id.radioGroup_UploadRadio)
    RadioGroup contnetsOpenRadioGroup;
    @BindView(R.id.radioGroup_UploadSelectRadio)
    RadioGroup labelSelectRadioGroup;

    @BindView(R.id.textView_click)
    TextView textView;

    @BindView(R.id.editText_Upload_InputText)
    EditText editText_input;

    @OnClick(R.id.textView_click)
    public void onClickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_FROM_GALLERY_FRAGMENT);
    }

    @OnClick(R.id.button_Upload_Complete)
    public void ClickComplete() {
//        Intent data = new Intent();
//        data.putExtra(MainActivity.TABINDEX, MainFragment.USERTAB);
//        setResult(RESULT_OK, data);
        String filetitle = "";
        String inputText = editText_input.getText().toString();

        Log.i("업로드 모드 ",""+uploadMode);
        switch (uploadMode){
            case PHOTO:
                UploadImageContentRequest request = new UploadImageContentRequest( 1, imagefile, filetitle, labelId, opento, inputText);
                if(Debug.debugmode)Log.i("업로드 정보", "context"+this+" , 1"+", imagefile :"+imagefile+", input :"+inputText);
                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult> request, NetworkResult result) {
                        Toast.makeText(UploadActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult> request, int errorCode, String errorMessage, Throwable e) {
                        Toast.makeText(UploadActivity.this, ""+errorMessage, Toast.LENGTH_SHORT).show();
                        Log.e("err","errcode"+errorCode+", errMessage :"+errorMessage+ ", e :"+e);
                    }
                });
                break;
            case YOUTUBE:
                break;
            case MUSIC:
                break;
        }
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
                Bundle b = new Bundle();
                b.putString("MusicTitle","음악을 선택 해주세요");
                UploadMusicFragment uf = new UploadMusicFragment();
                uf.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.upload_content, uf).commit();
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




        // 공개범위 설정화면
        if(contnetsOpenRadioGroup.getChildCount()!=0){
            contnetsOpenRadioGroup.removeAllViews();
        }
        RadioButton button;
        button = new RadioButton(this);
        button.setText("전체 공개");
        contnetsOpenRadioGroup.addView(button,0);
        button = new RadioButton(this);
        button.setText("레이블 공개");
        contnetsOpenRadioGroup.addView(button,1);
        button = new RadioButton(this);
        button.setText("비공개");
        contnetsOpenRadioGroup.addView(button,2);

        contnetsOpenRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                opento = i;
                Log.e(LOGTAG,"opento = "+ (i-1));
                if(opento == 2){
                    labelSelectRadioGroup.setClickable(true);
                }else{
                    labelSelectRadioGroup.setClickable(false);
                    for(int k = 0 ; k<labelSelectRadioGroup.getChildCount() ; k++){
                        RadioButton btn = (RadioButton)labelSelectRadioGroup.getChildAt(k);
                        btn.setTextColor(Color.GRAY);
                        btn.setHighlightColor(Color.GRAY);
                    }
                }
            }
        });


        if (user.getUserInLabelList() != null && user.getUserInLabelList().length != 0) {
            labels = user.getUserInLabelList();
            int j=0;
            for (Label l : labels) {
                String s = l.getLabelName();
                labelId = l.getLabelID();
                button = new RadioButton(this);
                button.setText(s);
                labelSelectRadioGroup.addView(button,j++);

            }
            labelSelectRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    labelId = labels[i-3].getLabelID();
                    Log.e(LOGTAG,"SelectedLabel = "+ labels[i-3].getLabelID());

                }
            });
        }else{
            TextView tv = new TextView(this);
            tv.setText("가입한 레이블이 업습니다.");
            labelSelectRadioGroup.addView(tv);
        }

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
    Uri uri;
    File imagefile;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == IMAGE_FROM_GALLERY) {
            Log.e("log", "IMAGE_FROM_GALLERY");
            if (resultCode == Activity.RESULT_OK) {

                Log.e("log", "activity");
                Log.e("log", " requestCode : " + requestCode + "resultCode " + resultCode);
                uri = data.getData();
                Bitmap savebitmap = null;
                InputStream inputStream = null;
                try {
                    inputStream = getContentResolver().openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                savebitmap = BitmapFactory.decodeStream(inputStream);
                Uri bitmapUri = writeToTempImageAndGetPathUri(this, savebitmap);
                Cursor c = getContentResolver().query(bitmapUri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    imagefile = new File(path);
                }

                Fragment f = new UploadImageFragment();
                Bundle bundle = new Bundle();
                dataType = data.getType();
                uri = data.getData();
                dataUri = uri.toString();
                bundle.putString("dataUri", dataUri);
                bundle.putString("dataType", dataType);
                f.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.upload_content, f).commitAllowingStateLoss();
            }
        }
        if (requestCode == IMAGE_FROM_GALLERY_FRAGMENT) {
            Log.e("log", "IMAGE_FROM_GALLERY_FRAGMENT");
            if (resultCode == Activity.RESULT_OK) {

                Log.e("log", "activity");
                Log.e("log", " requestCode : " + requestCode + "resultCode " + resultCode);
                uri = data.getData();
                Bitmap savebitmap = null;
                InputStream inputStream = null;
                try {
                    inputStream = getContentResolver().openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                savebitmap = BitmapFactory.decodeStream(inputStream);
                Uri bitmapUri = writeToTempImageAndGetPathUri(this, savebitmap);
                Cursor c = getContentResolver().query(bitmapUri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    imagefile = new File(path);
                }

                Fragment f = new UploadImageFragment();
                Bundle bundle = new Bundle();
                dataType = data.getType();
                uri = data.getData();
                dataUri = uri.toString();
                bundle.putString("dataUri", dataUri);
                bundle.putString("dataType", dataType);
                f.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.upload_content, f).commitNowAllowingStateLoss();
            }
        }

        if(requestCode == MainActivity.REQUEST_MUSICUPLOAD){
            if(resultCode == Activity.RESULT_OK){
                Uri uri = data.getData();
                String musictitle = data.getStringExtra("MusicTitle");
                Log.e("뮤직업로드",uri.toString()+ " "+musictitle);
                Fragment f = new UploadMusicFragment();
                Bundle bundle = new Bundle();
                bundle.putString("MusicTitle", musictitle);
                f.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.upload_content, f).commitNowAllowingStateLoss();

            }
        }

        if (Debug.debugmode) Log.i("onActivityResult", " imagePath :" + imagefile);
        if (Debug.debugmode) Log.i("onActivityResult", " Uri.path : " + data.getData().getPath());
    }

    public static Uri writeToTempImageAndGetPathUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}