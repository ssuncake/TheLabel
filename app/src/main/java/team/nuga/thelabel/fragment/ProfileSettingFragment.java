package team.nuga.thelabel.fragment;


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
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import team.nuga.thelabel.Debug;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.CheckEmailResult;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.RoundImageTransform;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.CheckNicknameRequest;
import team.nuga.thelabel.request.ProfileSetRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSettingFragment extends Fragment {


    User user = null;
    @BindView(R.id.textInput_nickname)
    TextInputLayout textinput_nickname;
    @BindView(R.id.editText_signUp_userNickName)
    EditText editText_userNickName;
    @BindView(R.id.ImageView_profileImage)
    ImageView imageView_profileImage;

    @BindView(R.id.button_checkOverlap)
    Button button_checkOverlap; //닉네임 중복확인 버튼

    @OnClick(R.id.button_checkOverlap)//중복확인 버튼
    public void onClickCheckOverlap() {
        nickname = editText_userNickName.getText().toString();
        CheckNicknameRequest nicknameRequest = new CheckNicknameRequest(getContext(), nickname);
        NetworkManager.getInstance().getNetworkData(nicknameRequest, new NetworkManager.OnResultListener<CheckEmailResult>() {
            @Override
            public void onSuccess(NetworkRequest<CheckEmailResult> request, CheckEmailResult result) {
                if (result.getMatch() == AVAILABLE) {
                    Log.d("MATCH 값 ", "" + result.getMatch() + "   //  0 = 중복 X  / 1 = 중복 O");
                    Toast.makeText(getContext(), "사용해도 좋은 닉네임입니다♪", Toast.LENGTH_SHORT).show();
                    nicknameCheck = true;
                } else if (result.getMatch() == NOT_AVAILABLE) {
                    Log.d("MATCH 값 ", "" + result.getMatch() + "   //  0 = 중복 X  / 1 = 중복 O");
                    editText_userNickName.setError("cccc");
                    nicknameCheck = false;
                }
            }

            @Override
            public void onFail(NetworkRequest<CheckEmailResult> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getContext(), "네트워크 확인..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.imageButton_profileSet)
    public void ClickComplete() //수정완료버튼
    {

        if (Debug.debugmode)
            Log.i("profile Info", "---------------------------------------------------");
        if (Debug.debugmode)
            Log.i(" 유저 정보1 ", " 닉네임 : " + nickname + ", 사용자 ID" + user.getUserID());
        if (Debug.debugmode) Log.i(" 유저 정보2 ", " 자기소개 :" + textInputEditText_introText.getText());
        String text = textInputEditText_introText.getText().toString();
//        if (Debug.debugmode) Log.i(" 유저 정보3 ", " 이미지Url:" + imagefile.toString());
        if (Debug.debugmode)
            Log.i(" 유저 정보4 ", ", 포지션:" + positionId + ", 장르:" + genreId + ", 시/도 :" + cityId +
                    ", 시군구:" + townId + ", Need :" + need);
        ProfileSetRequest request = new ProfileSetRequest(nickname, positionId, genreId, text,
                cityId, townId, imagefile, need);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult> request, NetworkResult result) {
                Toast.makeText(getContext(), "설정완료", Toast.LENGTH_SHORT).show();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.drawerUserSetting(nickname); //드로워 유저 세팅 변경
                mainActivity.goMainFragment(MainFragment.USERTAB); //메인 이동.
            }
            @Override
            public void onFail(NetworkRequest<NetworkResult> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getContext(), "데이터를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                if (Debug.debugmode) Log.e("Profile Setting Fail ", errorMessage + errorCode);
            }
        });
////        String inputUserName= inputName.getText().toString();
    }

    @BindView(R.id.radioGroup_ProfileSetting_need)
    RadioGroup radioGroup_need;
    @BindView(R.id.radioButton_needOn)
    RadioButton radioButton_needOn;
    @BindView(R.id.radioButton_needOff)
    RadioButton radioButton_needOff;

    @BindView(R.id.textView_userProfileEmail)
    TextView textView_userEmail;
    @BindView(R.id.textInput_introText)
    TextInputLayout textInputLayout_introtext;
    @BindView(R.id.editText_introText)
    TextInputEditText textInputEditText_introText;

    @BindView(R.id.spinner_genre_profile)
    Spinner spinner_genre;

    @OnItemSelected(value = R.id.spinner_genre_profile, callback = OnItemSelected.Callback.ITEM_SELECTED)
    public void onGenreSelected(int position) {
        genreId = position + 1;
    }

    @BindView(R.id.spinner_position_profile)
    Spinner spinner_position;

    @OnItemSelected(value = R.id.spinner_position_profile, callback = OnItemSelected.Callback.ITEM_SELECTED)
    public void onPositionSelected(int position) {
        positionId = position + 1;
    }

    @BindView(R.id.spinner_city_profile)
    Spinner spinner_city;
    @BindView(R.id.spinner_town_profile)
    Spinner spinner_town;

    @OnItemSelected(value = R.id.spinner_city_profile, callback = OnItemSelected.Callback.ITEM_SELECTED)
    public void onItemSelected(int position) {
        position = position + 1;
        cityId = position;
        if (position == 1) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, Town_Default);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, Seoul);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, Gyeonggi);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, Gangwon);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, Incheon);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, ChungBook);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, ChungNam);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, Sejong);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, DaeJeon);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, GyongBook);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, GyonNam);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, DaeGu);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, Busan);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, Ulsan);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, JeonBook);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, JeonNam);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, Gwangju);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, Jeju);
            spinner_townAdapter.setDropDownViewResource(R.layout.spin_profile);
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
        if (isFirstSet==true){
            spinner_town.setSelection(current_townId);
        }
        isFirstSet=false;

    }
    boolean isFirstSet=true;


    static final int AVAILABLE = 0;
    static final int NOT_AVAILABLE = 1;
    boolean nicknameCheck = false;
    private String nickname;

    int need = 0; //Default = 0; 선택 안함

    public void inProfileImageView() {
        String imageUrl = user.getImageUrl();
        Glide.with(this)
                .load(imageUrl)
                .transform(new RoundImageTransform(getContext()))
                .into(imageView_profileImage);
    }

    public void onClickRadioGroup_need()   //NEED 체크 라디오 버튼
    {
        radioGroup_need.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedPosition) {
                switch (checkedPosition) {
                    case R.id.radioButton_needOn:
                        need = 1;
                        if (Debug.debugmode)
                            Toast.makeText(getContext(), "need On", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButton_needOff:
                        need = 0;
                        if (Debug.debugmode)
                            Toast.makeText(getContext(), "need OFF", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        if (user.getNeed() == 0) {
            radioButton_needOff.setChecked(true);
        } else if (user.getNeed() == 1) {
            radioButton_needOn.setChecked(true);
            need = 1;
        }
    }


    public ProfileSettingFragment() {
        // Required empty public constructor
    }

    int IMAGE_FROM_GALLERY = 101;

    @OnClick(R.id.imageButton_uploadProfileImage) //이미지 업로드 버튼
    public void onProfileImageUpload() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_FROM_GALLERY);
    }

    File imagefile;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("log", "activity");
        Log.e("log", " requestCode : " + requestCode + "resultCode " + resultCode);
        if (requestCode == IMAGE_FROM_GALLERY) {
            Log.e("log", "IMAGE_FROM_GALLERY");
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                Bitmap savebitmap;
                InputStream inputStream = null;
                try {
                    inputStream = getContext().getContentResolver().openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                savebitmap = BitmapFactory.decodeStream(inputStream);
                Uri bitmapUri = writeToTempImageAndGetPathUri(getContext(), savebitmap);

                Cursor c = getContext().getContentResolver().query(bitmapUri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    imagefile = new File(path);

                    Glide.with(this)
                            .load(uri)
                            .transform(new RoundImageTransform(getContext()))
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

    public void preUserInfoSetting() {
        if (Debug.debugmode)
            Log.i(" 유저 정보1 ", " 닉네임 : " + user.getUserName() + ", 사용자 ID" + user.getUserID());
        if (Debug.debugmode)
            Log.i(" 유저 정보2 ", " 자기소개 :" + user.getText() + ", 이미지Url:" + user.getImageUrl());
        if (Debug.debugmode)
            Log.i(" 유저 정보3 ", " 포지션:" + user.getPostition() + ", 장르:" + user.getGenre());
        if (Debug.debugmode)
            Log.i(" 유저 정보4 ", " 시/도 :" + user.getCity() + ", 시군구 :" + user.getTown() + ", Need :" + user.getNeed());
        editText_userNickName.setText(user.getUserName());
        textInputEditText_introText.setText(user.getText());
        inProfileImageView();
        spinner_position.setSelection(user.getPostition() - 1);
        spinner_genre.setSelection(user.getGenre() - 1);
        spinner_city.setSelection(user.getCity() - 1);
        if(user.getTown()>1)townSetting();
        spinner_town.setSelection(current_townId);
    }
    int current_townId =0;
    public void townSetting(){
        String[][] town = new String[][] {Town_Default,Seoul, Gyeonggi, Gangwon, Incheon,
                ChungBook, ChungNam, Sejong, DaeJeon, GyongBook,
                GyonNam, DaeGu, Busan, Ulsan, JeonBook,
                JeonNam, Gwangju, Jeju
        };
        int sum = 1;
        int townSum =1;

        int userTownId = user.getTown();
        for(int i = 1; i<town.length;i++){
            townSum += town[i-1].length;
            townSum -=1;
            for(int j=0; j<town[i].length;j++){
                if(j!=0){
                    sum++;
                    current_townId = sum-townSum;
                    if(Debug.debugmode)Log.i("town ","sum :"+sum+", townSum :"+townSum+", 현재 타운 값"+ current_townId);
                    if(userTownId-sum == 0){

                    }
                }
            }
        }
    }


    public static int LIMITTED_TEXTLINE = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_setting, container, false);
        ButterKnife.bind(this, view);

        user = (User) getArguments().getSerializable(MainActivity.PROFILEUSER);
        if (Debug.debugmode)
            Log.i(" 유저 정보1 ", " 닉네임 : " + user.getUserName() + ", 사용자 ID" + user.getUserID());
        if (Debug.debugmode)
            Log.i(" 유저 정보2 ", " 자기소개 :" + user.getText() + ", 이미지Url:" + user.getImageUrl());
        if (Debug.debugmode)
            Log.i(" 유저 정보3 ", " 포지션:" + user.getPostition() + ", 장르:" + user.getGenre());
        if (Debug.debugmode)
            Log.i(" 유저 정보4 ", " 시/도 :" + user.getCity() + ", 시군구 :" + user.getTown() + ", Need :" + user.getNeed());

        ArrayAdapter spinner_cityAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, cityList);
        spinner_cityAdapter.setDropDownViewResource(R.layout.spin_profile);
        spinner_city.setAdapter(spinner_cityAdapter);
        ArrayAdapter positionAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, positionList);
        positionAdapter.setDropDownViewResource(R.layout.spin_profile);
        spinner_position.setAdapter(positionAdapter);
        ArrayAdapter genreAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, genreList);
        positionAdapter.setDropDownViewResource(R.layout.spin_profile);
        spinner_genre.setAdapter(genreAdapter);

        int color = Color.parseColor("#ffff3653");

        textInputEditText_introText.addTextChangedListener(introTextWatcher);
//        textInputEditText_introText.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        textInputEditText_introText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if (i == EditorInfo.IME_ACTION_DONE) {
//                    Toast.makeText(getContext(), "Action Done", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                return false;
//            }
//        });
        textInputLayout_introtext.setCounterEnabled(true);
        textInputLayout_introtext.setCounterMaxLength(60);

        editText_userNickName.addTextChangedListener(nicknameWatcher);
//        editText_userNickName.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_OUT);
        textView_userEmail.setText(user.getEmail());
        onClickRadioGroup_need();
        preUserInfoSetting();
        return view;
    }

    TextWatcher nicknameWatcher = new TextWatcher() {
        String before = "";

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            before = charSequence.toString();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 8) {
                editText_userNickName.setText(before);
                editText_userNickName.setSelection(textInputEditText_introText.length()+1);
                editText_userNickName.setError("2~8자로 입력해주세요");
            } else if (editable.length() < 2) {
                editText_userNickName.setError("2~8자로 입력해주세요");
            }


        }
    };

    TextWatcher introTextWatcher = new TextWatcher() {
        String before = "";

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            before = charSequence.toString();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 60) {
//                textInputLayout_introtext.setError("error");
            } else {
                textInputLayout_introtext.setError(null);
            }
            if (textInputEditText_introText.getLineCount() > LIMITTED_TEXTLINE) { //라인수 제한
//                textInputEditText_introText.setText(before);
//                textInputEditText_introText.setSelection(textInputEditText_introText.length());
            }
        }
    };

    int townId = 0;
    int cityId = 0;
    int positionId = 1; //Default = 1 ; 선택안함
    int genreId = 1;  // Default = 1 ; 선택안함


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


}
