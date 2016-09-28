package team.nuga.thelabel;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import team.nuga.thelabel.data.CheckEmailResult;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.RoundImageTransform;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.DBManager;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.manager.PropertyManager;
import team.nuga.thelabel.request.CheckNicknameRequest;
import team.nuga.thelabel.request.LoginRequest;
import team.nuga.thelabel.request.SignUpRequest;

public class SignUpEditActivity extends AppCompatActivity  {

    final int IMAGE_FROM_GALLERY = 101;
    @BindView(R.id.imageButton_uploadProfileImage)
    ImageButton imageButton_uploadProfileImage;

    @OnClick(R.id.imageButton_uploadProfileImage)
    public void onProfileImageUpload() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_FROM_GALLERY);
    }


    private static final int RC_PERMISSION = 100;

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_PERMISSION);
    }

    @BindView(R.id.ImageView_profileImage)
    ImageView imageView_profileImage;


    @BindView(R.id.radioGroup_userGender)
    RadioGroup radioGroup_userGender;//성별
    @BindView(R.id.radioButton_male)
    RadioButton radioButton_male;
    @BindView(R.id.radioButton_female)
    RadioButton radioButton_female;

    @BindView(R.id.textInput_nickname)
    TextInputLayout textInputLayout_nickname;


    public void onClickRadioGroup_userGender() {             //성별체크 라디오 버튼
        radioGroup_userGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedPosition) {
                switch (checkedPosition) {
                    case R.id.radioButton_female:
                        userGender = 2;
                        break;
                    case R.id.radioButton_male:
                        userGender = 1;
                        break;
                }
            }
        });
    }


    @BindView(R.id.spinner_city)
    Spinner spinner_city;
    @BindView(R.id.spinner_town)
    Spinner spinner_town;
    @BindView(R.id.spinner_genre)
    Spinner spinner_genre;

    @OnItemSelected(value = R.id.spinner_genre, callback = OnItemSelected.Callback.ITEM_SELECTED)
    public void onGenreSelected(int position) {
        genreId = position + 1;
    }

    @BindView(R.id.spinner_position)
    Spinner spinner_position;

    @OnItemSelected(value = R.id.spinner_position, callback = OnItemSelected.Callback.ITEM_SELECTED)
    public void onPositionSelected(int position) {
        positionId = position + 1;
    }


    @BindView(R.id.editText_signUp_userNickName)
    EditText editText_userNickName;


    @BindView(R.id.button_checkOverlap)
    Button button_checkOverlap; //닉네임 중복확인 버튼
    static final int AVAILABLE = 0;
    static final int NOT_AVAILABLE = 1;

    @OnClick(R.id.button_checkOverlap)//중복확인 버튼
    public void onClickCheckOverlap() {
        String nickname = editText_userNickName.getText().toString();
        CheckNicknameRequest nicknameRequest = new CheckNicknameRequest(this, nickname);
        NetworkManager.getInstance().getNetworkData(nicknameRequest, new NetworkManager.OnResultListener<CheckEmailResult>() {
            @Override
            public void onSuccess(NetworkRequest<CheckEmailResult> request, CheckEmailResult result) {
                if (result.getMatch() == AVAILABLE) {
                    Log.d("MATCH 값 ", "" + result.getMatch() + "   //  0 = 중복 X  / 1 = 중복 O");
                    Toast.makeText(SignUpEditActivity.this, "사용해도 좋은 닉네임입니다♪", Toast.LENGTH_SHORT).show();
                    editText_userNickName.clearFocus();
                } else if (result.getMatch() == NOT_AVAILABLE) {
                    Log.d("MATCH 값 ", "" + result.getMatch() + "   //  0 = 중복 X  / 1 = 중복 O");
                    Toast.makeText(SignUpEditActivity.this, "닉네임이 이미 있어요!!!!!!!!!!!!!!!!!!!!!!!ㅋ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(NetworkRequest<CheckEmailResult> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
    }

    @OnClick(R.id.button_signUpComplete) //가입완료 버튼
    public void onsignUpComplete() {
        Intent intent = getIntent();
        User signUp = (User) intent.getSerializableExtra("signUpInfo");
        final String email = signUp.getEmail();
        final String password = signUp.getUserPassword();
        if (Debug.debugmode) Log.e("인텐트값", "email -" + email + " , password - " + password);

        final String nickname = editText_userNickName.getText().toString();

        final int position_id = positionId;
        final int gender_id = userGender;
        final int genre_id = genreId;
        final int city_id = cityId;
        final int town_id = townId;
        Log.i(" 가입 정보 값 ", "포지션ID값 : " + position_id + ", 장르 ID값 :" + genre_id + ", 시/도 ID값 : " + city_id +
                ", 시/군/구 ID값 : " + town_id + " 성별 값:" + gender_id + ",  닉네임 :" + nickname);
        SignUpRequest request = new SignUpRequest(SignUpEditActivity.this, email, password, nickname, gender_id, position_id, genre_id, city_id, town_id, imagefile);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                result.getMessage();
                result.getId();
                if (Debug.debugmode)
                    Log.d("메세지 ", "" + result.getMessage() + ", id : " + result.getId());
                     //자동로그인 기능 : 이메일 세팅
                    PropertyManager.getInstance().setEmail(email);
                    PropertyManager.getInstance().setPassword(password);
                    String regId = PropertyManager.getInstance().getRegistrationId();
                    goMain(email,password,regId); //로그인 후 메인액티비티로 넘어감.
                finish();
                if(!LoginActivity.closeActivity.isFinishing()){  //LoginAcitivity 가 살아있으면...같이 종료
                    LoginActivity.closeActivity.finish();
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                if (Debug.debugmode)
                    Log.d("fail", errorMessage + ", 코드: " + errorCode + " Throwable : " + e);
                Toast.makeText(SignUpEditActivity.this, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void goMain(String email,String password, String regid){
        LoginRequest request = new LoginRequest(this,email,password,regid);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>(){
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                String message = result.getMessage();
                Log.e("로그인 성공",message);
                User user = result.getUser();
                DBManager.getInstance().setMainUser(user);
                Log.e("로그인 유저 정보 확인",user.getEmail()+" // "+ user.getUserName());
                startMainAc(user);
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(SignUpEditActivity.this, "로그인 실패 !+", Toast.LENGTH_SHORT).show();
                Log.e("로그인 실패",errorMessage);
            }
        });
    }
    public void startMainAc(User user){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("LoginUser",user);
        startActivity(intent);
        finish();
    }

    int townId = 0;
    int cityId = 0;
    int userGender = 0;
    int positionId = 1; //Default = 1 ; 선택안함
    int genreId = 1;  // Default = 1 ; 선택안함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sign_up);                 ////

        ButterKnife.bind(this);


        Intent intent = getIntent();
        User signUp = (User) intent.getSerializableExtra("signUpInfo");
//        String email = signUp.getEmail();
//        String password = signUp.getUserPassword();
//        if (Debug.debugmode) Log.d("인텐트값", "email -" + email + " , password - " + password);
//        RadioGroupClick();
        onClickRadioGroup_userGender();
        radioButton_male.setButtonDrawable(R.drawable.oncheck_radiobutton);
        radioButton_female.setButtonDrawable(R.drawable.oncheck_radiobutton);

        ArrayAdapter spinner_cityAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, cityList);
        spinner_cityAdapter.setDropDownViewResource(R.layout.spin);
        spinner_city.setAdapter(spinner_cityAdapter);
        ArrayAdapter positionAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, positionList);
        positionAdapter.setDropDownViewResource(R.layout.spin);
        spinner_position.setAdapter(positionAdapter);
        ArrayAdapter genreAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, genreList);
        positionAdapter.setDropDownViewResource(R.layout.spin);
        spinner_genre.setAdapter(genreAdapter);

        if (savedInstanceState != null) {
            String path = savedInstanceState.getString(UPLOAD_PROFILE_IMAGE);
            if (!TextUtils.isEmpty(path)) {
                imagefile = new File(path);
                Glide.with(this)
                        .load(imagefile)
                        .into(imageView_profileImage);
            }
        }
    }

    String UPLOAD_PROFILE_IMAGE = "uploadfile";

    File imagefile = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("log", "activity");
        Log.e("log", " requestCode : " + requestCode + "resultCode " + resultCode);
        if (requestCode == IMAGE_FROM_GALLERY) {
            Log.e("log", "IMAGE_FROM_GALLERY");
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
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

                    Glide.with(this)
                            .load(uri)
                            .transform(new RoundImageTransform(this))
                            .into(imageView_profileImage);
                }
            }
        }
    }

    public static Uri writeToTempImageAndGetPathUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (imagefile != null) {
            outState.putString(UPLOAD_PROFILE_IMAGE, imagefile.getAbsolutePath());
        }
    }

    @OnItemSelected(value = R.id.spinner_city, callback = OnItemSelected.Callback.ITEM_SELECTED)
    public void onItemSelected(int position) {
        position = position + 1;
        cityId = position;
        if (position == 1) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, Town_Default);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    townId = 1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } else if (position == 2) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, Seoul);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 1 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 3) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, Gyeonggi);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 25 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 4) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, Gangwon);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 70 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 5) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, Incheon);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 88 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 6) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, ChungBook);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 98 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 7) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, ChungNam);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 113 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 8) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, Sejong);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 129 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 9) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, DaeJeon);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 130 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 10) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, GyongBook);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 135 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 11) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, GyonNam);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 160 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 12) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, DaeGu);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 183 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 13) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, Busan);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 191 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 14) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, Ulsan);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 207 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 15) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, JeonBook);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 212 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 16) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, JeonNam);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 228 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 17) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, Gwangju);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 250 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if (position == 18) {
            townId = 1;
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, Jeju);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin);
            spinner_town.setAdapter(spinner_townAdapter);
            spinner_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != 0) {
                        townId = 255 + i;
                    } else {
                        townId = 1;
                    }
                    Log.e("town ID", townId + "" + spinner_town.getSelectedItem() + " position : " + i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }

    }


    @BindArray(R.array.positionlist)
    public String[] positionList;
    @BindArray(R.array.genrelist)
    public String[] genreList;
    @BindArray(R.array.cityList)
    public String[] cityList;
    @BindArray(R.array.townDefault)
    public String[] Town_Default;
    @BindArray(R.array.서울시)
    public String[] Seoul;
    @BindArray(R.array.경기도)
    public String[] Gyeonggi;
    @BindArray(R.array.강원도)
    public String[] Gangwon ;
    @BindArray(R.array.인천광역시)
    public String[] Incheon ;
    @BindArray(R.array.충청북도)
    public String[] ChungBook ;
    @BindArray(R.array.충청남도)
    public String[] ChungNam;
    @BindArray(R.array.세종시)
    public String[] Sejong ;
    @BindArray(R.array.대전광역시)
    public String[] DaeJeon ;
    @BindArray(R.array.경상북도)
    public String[] GyongBook ;
    @BindArray(R.array.경상남도)
    public String[] GyonNam;
    @BindArray(R.array.대구광역시)
    public String[] DaeGu ;
    @BindArray(R.array.부산광역시)
    public String[] Busan;
    @BindArray(R.array.울산광역시)
    public String[] Ulsan;
    @BindArray(R.array.전라북도)
    public String[] JeonBook;
    @BindArray(R.array.전라남도)
    public String[] JeonNam ;
    @BindArray(R.array.광주광역시)
    public String[] Gwangju;
    @BindArray(R.array.제주도)
    public String[] Jeju;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
