package team.nuga.thelabel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.RoundImageTransform;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.fragment.UploadFragment;
import team.nuga.thelabel.fragment.UploadImageFragment;
import team.nuga.thelabel.fragment.UploadMusicFragment;
import team.nuga.thelabel.fragment.UploadYoutubeFragment;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.UploadImageContentRequest;
import team.nuga.thelabel.request.UploadMusicContentRequest;
import team.nuga.thelabel.request.UploadYoutubeContentRequest;

public class UploadActivity extends AppCompatActivity {
    public final static String LOGTAG = "UploadActivity ";

    final int IMAGE_FROM_GALLERY = 100;
    final int IMAGE_FROM_GALLERY_FRAGMENT = 101;
    public static final int OPEN_TO_ALL = 0;  //모두공개
    public static final int OPEN_TO_LABEL = 0;  //레이블공개
    public static final int OPEN_TO_NOBODY = 0;  //비공개
    public  static final int MUSIC = 101;
    public  static final int PHOTO = 102;
    public  static final int YOUTUBE = 103;
    int labelId = 0;
    int opento = OPEN_TO_ALL;

    Label[] labels;
    private int uploadMode = -1;
    private User user;
    private String userName;

    String dataUri, dataType;
    Uri uri;
    File imagefile;
    File musicFile;

    String musicTitle;
    public static String youtubeURL = null;

    @BindView(R.id.radioGroup_UploadRadio)
    RadioGroup contnetsOpenRadioGroup;
    @BindView(R.id.radioGroup_UploadSelectRadio)
    RadioGroup labelSelectRadioGroup;
    @BindView(R.id.imageView_Upload_ProfileImage)
    ImageView profileImage;

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
        switch (uploadMode) {
            case PHOTO:
                if (imagefile != null) {
                    UploadImageContentRequest request = new UploadImageContentRequest(1, imagefile, filetitle, labelId, opento, inputText);
                    if (Debug.debugmode)
                        Log.i("업로드 정보", "context" + this + " , 1" + ", imagefile :" + imagefile + ", input :" + inputText);
                    NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetworkResult> request, NetworkResult result) {
                            Toast.makeText(UploadActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail(NetworkRequest<NetworkResult> request, int errorCode, String errorMessage, Throwable e) {
                            Toast.makeText(UploadActivity.this, "" + errorMessage, Toast.LENGTH_SHORT).show();
                            Log.e("err", "errcode" + errorCode + ", errMessage :" + errorMessage + ", e :" + e);
                        }
                    });
                } else {
                    Toast.makeText(UploadActivity.this, "사진을 등록해 주세요", Toast.LENGTH_SHORT).show();
                }
                break;
            case YOUTUBE:
                if (youtubeURL != null) {
                    if (Debug.debugmode) Log.i("유튭 URL", youtubeURL);
                    f = (UploadYoutubeFragment) getSupportFragmentManager().findFragmentById(R.id.editText_youtubeUpload);
                    UploadYoutubeContentRequest request2 = new UploadYoutubeContentRequest(this, 2, youtubeURL, "", labelId, opento, inputText);
                    NetworkManager.getInstance().getNetworkData(request2, new NetworkManager.OnResultListener<NetworkResult>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetworkResult> request, NetworkResult result) {
                            if (Debug.debugmode)
                                Log.d(LOGTAG, "유튜브 업로드  = " + youtubeURL + " title = " + "" + " 올릴레이블 =" + labelId + " 공개범위 = " + opento);
                        }

                        @Override
                        public void onFail(NetworkRequest<NetworkResult> request, int errorCode, String errorMessage, Throwable e) {
                            Log.e("err", "errcode" + errorCode + ", errMessage :" + errorMessage + ", e :" + e);
                        }
                    });
                } else {
                    Toast.makeText(UploadActivity.this, "유튜브 URL을 등록해 주세요", Toast.LENGTH_SHORT).show();
                }
                break;
            case MUSIC:
                if (musicFile != null) {
                    UploadMusicContentRequest request1 = new UploadMusicContentRequest(this, 0, musicFile, musicTitle, labelId, opento, inputText);
                    NetworkManager.getInstance().getNetworkData(request1, new NetworkManager.OnResultListener<NetworkResult>() {
                        @Override
                        public void onSuccess(NetworkRequest<NetworkResult> request, NetworkResult result) {
                            if (Debug.debugmode)
                                Log.d(LOGTAG, "음악파일 업로드  = " + musicFile.toString() + " title = " + musicTitle + " 올릴레이블 =" + labelId + " 공개범위 = " + opento);
                            Toast.makeText(UploadActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail(NetworkRequest<NetworkResult> request, int errorCode, String errorMessage, Throwable e) {
                            Toast.makeText(UploadActivity.this, "" + errorMessage, Toast.LENGTH_SHORT).show();
                            Log.e("err", "errcode" + errorCode + ", errMessage :" + errorMessage + ", e :" + e);
                        }
                    });
                } else {
                    Toast.makeText(UploadActivity.this, "음원을 등록해 주세요", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                //도달할수없는함수, 에러
                break;
        }
        finish();
    }

    public static void setYoutubeURL(String text) {
        youtubeURL = text;
    }
    UploadYoutubeFragment f = new UploadYoutubeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_Upload);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        uploadMode = getIntent().getIntExtra(UploadFragment.UPLOADMODE, -1);
        switch (uploadMode) {
            case MUSIC:
                Bundle b = new Bundle();
                b.putString("MusicTitle", "음악을 선택 해주세요");
                UploadMusicFragment uf = new UploadMusicFragment();
                uf.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.upload_content, uf).commit();
                break;
            case PHOTO:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_FROM_GALLERY);
                break;
            case YOUTUBE:
                getSupportFragmentManager().beginTransaction().replace(R.id.upload_content, f).commit();
                break;
        }

        user = (User) getIntent().getSerializableExtra(MainActivity.MAINUSER);
        userName = user.getUserName();

        setSupportActionBar(toolbar);
        toolbar.setTitle("업로드");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 사용자 프로필 설정
        String imagePath = user.getImageUrl();
        if (imagePath != null) {
            Glide.with(this)
                    .load(imagePath)
                    .transform(new RoundImageTransform(this))
                    .into(profileImage);
        } else {
            Log.e(LOGTAG, "pofileImage null!!");
        }

        // 공개범위 설정화면

        contnetsOpenRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.radioButton_Upload_Public:
                        opento = OPEN_TO_ALL;
                        Log.e(LOGTAG, "opento = " + opento);
                        break;
                    case R.id.radioButton_Upload_LabelSelect:
                        opento = OPEN_TO_LABEL;
                        Log.e(LOGTAG, "opento = " + opento);
                        break;
                    case R.id.radioButton_Upload_Priavate:
                        opento = OPEN_TO_NOBODY;
                        Log.e(LOGTAG, "opento = " + opento);
                        break;
                }
            }
        });

        labels = user.getUserInLabelList();
        RadioButton rt = new RadioButton(this);
        rt.setText("내 계정");
        rt.isChecked();
        rt.setTextColor(getResources().getColor(R.color.text_color));
        rt.setLayoutParams(new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        labelSelectRadioGroup.addView(rt);
        rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                labelId = 0;
                Log.e(LOGTAG, "선택한 레이블 아이디 = " + labelId);
            }
        });
        for (Label label : labels) {
            RadioButton r = new RadioButton(this);
            r.setText(label.getLabelName());
            r.setTextColor(getResources().getColor(R.color.text_color));
            final int lid = label.getLabelID();
            r.setLayoutParams(new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            labelSelectRadioGroup.addView(r);
            r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    labelId = lid;
                    Log.e(LOGTAG, "선택한 레이블 아이디 = " + labelId);
                }
            });
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
                if (c != null && c.moveToNext()) {
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
                if (c != null && c.moveToNext()) {
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

        if (requestCode == MainActivity.REQUEST_MUSICUPLOAD) {
            if (resultCode == Activity.RESULT_OK) {
                uri = data.getData();
                musicFile = new File(getPathFromUri(uri));
                musicTitle = data.getStringExtra("MusicTitle");
                Log.e("뮤직업로드", musicFile + " // " + musicTitle);
                Fragment f = new UploadMusicFragment();
                Bundle bundle = new Bundle();
                bundle.putString("MusicTitle", musicTitle);
                f.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.upload_content, f).commitNowAllowingStateLoss();

            }
        }
    }

    /**
     * 임시이미지를 만들고, 임시이미지의 Uri path를 얻어옴
     * @param inImage 선택한 이미지
     * @return Uri의 path값을 파싱해서 리턴함
     */
    public static Uri writeToTempImageAndGetPathUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    /**
     *  Uri로부터 path를 가져오는 함수
     * @param uri path를 가져올 uri를 넣는다.
     * @return String path
     */
    public String getPathFromUri(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToNext();
        }
        String path = null;
        if (cursor != null) {
            path = cursor.getString(cursor.getColumnIndex("_data"));
        }
        if (cursor != null) {
            cursor.close();
        }
        return path;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}