package team.nuga.thelabel;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import team.nuga.thelabel.data.CheckEmailResult;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.RoundImageTransform;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.CheckNicknameRequest;
import team.nuga.thelabel.request.SignUpRequest;

public class SignUpEditActivity extends AppCompatActivity {

   final int IMAGE_FROM_GALLERY = 101;
    @BindView(R.id.imageButton_uploadProfileImage)
    ImageButton imageButton_uploadProfileImage;
    @OnClick(R.id.imageButton_uploadProfileImage)
    public void onProfileImageUpload(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_FROM_GALLERY);
    }
    @BindView(R.id.ImageView_profileImage)
    ImageView imageView_profileImage;

    @OnClick(R.id.imageButton_uploadProfileImage)
    public void setImageButton_uploadProfileImage() {
        Toast.makeText(SignUpEditActivity.this, "이미지를 골라주세요 ♪", Toast.LENGTH_SHORT).show();
    }

    @BindView(R.id.radioGroup_userSex)
    RadioGroup radioGroup_userSex;                  //성별


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

    @OnClick(R.id.button_checkOverlap)
    public void onClickCheckOverlap() {
        String nickname = editText_userNickName.getText().toString();
        CheckNicknameRequest nicknameRequest = new CheckNicknameRequest(this, nickname);
        NetworkManager.getInstance().getNetworkData(nicknameRequest, new NetworkManager.OnResultListener<CheckEmailResult>() {
            @Override
            public void onSuccess(NetworkRequest<CheckEmailResult> request, CheckEmailResult result) {
                if (result.getMatch() == AVAILABLE) {
                    Log.d("MATCH 값 ", "" + result.getMatch() + "   //  0 = 중복 X  / 1 = 중복 O");
                    Toast.makeText(SignUpEditActivity.this, "사용해도 좋은 닉네임입니다♪", Toast.LENGTH_SHORT).show();
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

    @BindView(R.id.button_signUpComplete)
    Button button_signUpComplete; //가입완료 버튼
    @OnClick(R.id.button_signUpComplete)
    public void onsignUpComplete() {
        Intent intent = getIntent();
        User signUp = (User) intent.getSerializableExtra("signUpInfo");
        String email = signUp.getEmail();
        String password = signUp.getUserPassword();
        if(Debug.debugmode)Log.e("인텐트값", "email -" + email + " , password - " + password);

        final String nickname = editText_userNickName.getText().toString();

        final int position_id = positionId;
        final int gender_id = userGender;
        final int genre_id = genreId;
        final int city_id = cityId;
        final int town_id = townId;
        Log.i(" 가입 정보 값 ", "포지션ID값 : " + position_id + ", 장르 ID값 :" + genre_id + ", 시/도 ID값 : " + city_id +
                ", 시/군/구 ID값 : " + town_id + " 성별 값:" + gender_id + ",  닉네임 :" + nickname);
        SignUpRequest request = new SignUpRequest(SignUpEditActivity.this , email, password, nickname, gender_id, position_id, genre_id, city_id, town_id,imagefile);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                result.getMessage();
                result.getId();
                if(Debug.debugmode)Log.d("메세지 ", "" + result.getMessage() + ", id : " + result.getId());

            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                if(Debug.debugmode)Log.d("fail", errorMessage +", 코드: "+errorCode +" Throwable : "+ e);
            }
        });

    }

    int townId = 0;
    int cityId = 0;
    int userGender = 0;
    int positionId = 1; //Default = 1 ; 선택안함
    int genreId = 1;  // Default = 1 ; 선택안함


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sign_up);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        User signUp = (User) intent.getSerializableExtra("signUpInfo");
        String email = signUp.getEmail();
        String password = signUp.getUserPassword();
        if(Debug.debugmode)Log.d("인텐트값", "email -" + email + " , password - " + password);
//        RadioGroupClick();
        String[] positionList = getResources().getStringArray(R.array.positionlist);
        String[] genreList = getResources().getStringArray(R.array.positionlist);

        ArrayAdapter spinner_cityAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, cityList);
        spinner_cityAdapter.setDropDownViewResource(R.layout.spin);
        spinner_city.setAdapter(spinner_cityAdapter);

        ArrayAdapter positionAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, positionList);
        positionAdapter.setDropDownViewResource(R.layout.spin);
        spinner_position.setAdapter(positionAdapter);
        ArrayAdapter genreAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, genreList);
        positionAdapter.setDropDownViewResource(R.layout.spin);
        spinner_genre.setAdapter(genreAdapter);

    }

File imagefile=null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("log","activity");
        Log.e("log"," requestCode : "+requestCode + "resultCode "+resultCode);
        if(requestCode ==IMAGE_FROM_GALLERY){
            Log.e("log","IMAGE_FROM_GALLERY");
            if(resultCode== Activity.RESULT_OK)
            {
                Uri uri = data.getData();
                Cursor c = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA},null,null,null);
        if(c.moveToNext()){
            String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
            imagefile = new File(path);
            Glide.with(this)
                    .load(imagefile)
                    .transform(new RoundImageTransform(this))
                    .into(imageView_profileImage);
        }

//            Bitmap bitmap = null;
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),data.getData());
//                    imagefile = new File(String.valueOf(data.getData()));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Glide.with(this)
//                        .load(data.getData())
//                        .transform(new RoundImageTransform(this))
//                        .into(imageView_profileImage);
            }
        }
    }








    @OnItemSelected(value = R.id.spinner_city, callback = OnItemSelected.Callback.ITEM_SELECTED)
    public void onItemSelected(int position) {
        position = position + 1;
        cityId = position;
        if (position == 1) {
            ArrayAdapter spinner_townAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spin, Town_Default);
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
            spinner_townAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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

//
//    boolean isCheckedRadioGroup_position_first = false;   //포지션 라디오그룹 체크상태
//    boolean isCheckedRadioGroup_position_second = false;   //포지션 라디오그룹 체크상태
//    boolean isCheckedRadioGroup_position_third = false;   //포지션 라디오그룹 체크상태
//
//    boolean isCheckedRadioGroup_genre_first = false;      //장르 라디오그룹 체크상태
//    boolean isCheckedRadioGroup_genre_second = false;      //장르 라디오그룹 체크상태
//    boolean isCheckedRadioGroup_genre_third = false;      //장르 라디오그룹 체크상태
//
//    private void RadioGroupClick() {
//        radioGroup_position_first.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                                                                 @Override
//                                                                 public void onCheckedChanged(RadioGroup radioGroup, int checkedPosition) {
//                                                                     switch (checkedPosition) {
//                                                                         case R.id.radioButton_vocal:
//                                                                             isCheckedRadioGroup_position_first = true;
//                                                                             onRadioGroup_position_first();
//                                                                             radioGroup_position_first.check(checkedPosition);
//                                                                             positionId = 2;
//                                                                             break;
//                                                                         case R.id.radioButton_guitar:
//                                                                             isCheckedRadioGroup_position_first = true;
//                                                                             onRadioGroup_position_first();
//                                                                             radioGroup_position_first.check(checkedPosition);
//                                                                             positionId = 3;
//                                                                             break;
//                                                                         case R.id.radioButton_base:
//                                                                             isCheckedRadioGroup_position_first = true;
//                                                                             onRadioGroup_position_first();
//                                                                             radioGroup_position_first.check(checkedPosition);
//                                                                             positionId = 4;
//                                                                             break;
//                                                                     }
//                                                                 }
//                                                             }
//        );
//        radioGroup_position_second.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//
//                                                              {
//                                                                  @Override
//                                                                  public void onCheckedChanged(RadioGroup radioGroup, int checkedPosition) {
//                                                                      switch (checkedPosition) {
//                                                                          case R.id.radioButton_elec:
//                                                                              isCheckedRadioGroup_position_second = true;
//                                                                              onRadioGroup_position_second();
//                                                                              radioGroup_position_second.check(checkedPosition);
//                                                                              positionId = 5;
//                                                                              break;
//                                                                          case R.id.radioButton_drum:
//                                                                              isCheckedRadioGroup_position_second = true;
//                                                                              onRadioGroup_position_second();
//                                                                              radioGroup_position_second.check(checkedPosition);
//                                                                              positionId = 6;
//                                                                              break;
//                                                                          case R.id.radioButton_keyboard:
//                                                                              isCheckedRadioGroup_position_second = true;
//                                                                              onRadioGroup_position_second();
//                                                                              radioGroup_position_second.check(checkedPosition);
//                                                                              positionId = 7;
//                                                                              break;
//                                                                      }
//                                                                  }
//                                                              }
//
//        );
//        radioGroup_position_third.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                                                                 @Override
//                                                                 public void onCheckedChanged(RadioGroup radioGroup, int checkedPosition) {
//                                                                     switch (checkedPosition) {
//                                                                         case R.id.radioButton_etc:
//                                                                             isCheckedRadioGroup_position_third = true;
//                                                                             onRadioGroup_position_third();
//                                                                             radioGroup_position_third.check(checkedPosition);
//                                                                             positionId = 8;
//                                                                             break;
////                    case R.id.radioButton_
////                        isCheckedRadioGroup_position_second = true;
////                        if (isCheckedRadioGroup_position_first == true) {
////                            onRadioGroup_position_second();
////                            radioGroup_position_second.check(checkedPosition);
////                        }
////                        break;
////                    case R.id.radioButton_:
////                        isCheckedRadioGroup_position_second = true;
////                        if (isCheckedRadioGroup_position_first == true) {
////                            onRadioGroup_position_second();
////                            radioGroup_position_second.check(checkedPosition);
////                        }
////                        break;
//                                                                     }
//                                                                 }
//                                                             }
//
//        );
//
//
//        radioGroup_genre_firstLine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//
//                                                              {
//                                                                  @Override
//                                                                  public void onCheckedChanged(RadioGroup radioGroup, int checkedGenre) {
//                                                                      switch (checkedGenre) {
//                                                                          case R.id.radioButton_Song:
//                                                                              isCheckedRadioGroup_genre_first = true;
//                                                                              onRadioGroup_genre_first();
//                                                                              radioGroup_genre_firstLine.check(checkedGenre);
//                                                                              genreId = 2;
//                                                                              break;
//                                                                          case R.id.radioButton_RnBSOUL:
//                                                                              isCheckedRadioGroup_genre_first = true;
//                                                                              onRadioGroup_genre_first();
//                                                                              radioGroup_genre_firstLine.check(checkedGenre);
//                                                                              genreId = 9;
//                                                                              break;
//                                                                          case R.id.radioButton_Rock:
//                                                                              isCheckedRadioGroup_genre_first = true;
//                                                                              onRadioGroup_genre_first();
//                                                                              radioGroup_genre_firstLine.check(checkedGenre);
//                                                                              genreId = 5;
//                                                                              break;
//                                                                          case R.id.radioButton_Jazz:
//                                                                              isCheckedRadioGroup_genre_first = true;
//                                                                              onRadioGroup_genre_first();
//                                                                              radioGroup_genre_firstLine.check(checkedGenre);
//                                                                              genreId = 10;
//                                                                              break;
//                                                                      }
//                                                                  }
//                                                              }
//
//        );
//        radioGroup_genre_secondLine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//
//                                                               {
//                                                                   @Override
//                                                                   public void onCheckedChanged(RadioGroup radioGroup, int checkedGenre) {
//                                                                       switch (checkedGenre) {
//                                                                           case R.id.radioButton_RapHiphop:
//                                                                               isCheckedRadioGroup_genre_second = true;
//                                                                               onRadioGroup_genre_second();
//                                                                               radioGroup_genre_secondLine.check(checkedGenre);
//                                                                               genreId = 4;
//                                                                               break;
//                                                                           case R.id.radioButton_NewAge:
//                                                                               isCheckedRadioGroup_genre_second = true;
//                                                                               onRadioGroup_genre_second();
//                                                                               radioGroup_genre_secondLine.check(checkedGenre);
//                                                                               genreId = 8;
//                                                                               break;
//                                                                           case R.id.radioButton_Pop:
//                                                                               isCheckedRadioGroup_genre_second = true;
//                                                                               onRadioGroup_genre_second();
//                                                                               radioGroup_genre_secondLine.check(checkedGenre);
//                                                                               genreId = 3;
//                                                                               break;
//                                                                           case R.id.radioButton_CCM:
//                                                                               isCheckedRadioGroup_genre_second = true;
//                                                                               onRadioGroup_genre_second();
//                                                                               radioGroup_genre_secondLine.check(checkedGenre);
//                                                                               genreId = 11;
//                                                                               break;
//                                                                       }
//                                                                   }
//                                                               }
//
//        );
//        radioGroup_genre_thirdLine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//
//                                                              {
//                                                                  @Override
//                                                                  public void onCheckedChanged(RadioGroup radioGroup, int checkedGenre) {
//                                                                      switch (checkedGenre) {
//                                                                          case R.id.radioButton_Electronica:
//                                                                              isCheckedRadioGroup_genre_third = true;
//                                                                              onRadioGroup_genre_third();
//                                                                              radioGroup_genre_thirdLine.check(checkedGenre);
//                                                                              genreId = 7;
//                                                                              break;
//                                                                          case R.id.radioButton_AcousticPork:
//                                                                              isCheckedRadioGroup_genre_third = true;
//                                                                              onRadioGroup_genre_third();
//                                                                              radioGroup_genre_thirdLine.check(checkedGenre);
//                                                                              genreId = 6;
//                                                                              break;
//                                                                      }
//                                                                  }
//                                                              }
//
//        );
//    }
//
//
//    private void onRadioGroup_position_first() {
//        radioGroup_position_second.clearCheck();
//        radioGroup_position_third.clearCheck();
//        isCheckedRadioGroup_position_second = false;
//        isCheckedRadioGroup_position_third = false;
//        isCheckedRadioGroup_position_first = true;
//    }
//
//    private void onRadioGroup_position_second() {
//        radioGroup_position_first.clearCheck();
//        radioGroup_position_third.clearCheck();
//        isCheckedRadioGroup_position_first = false;
//        isCheckedRadioGroup_position_third = false;
//        isCheckedRadioGroup_position_second = true;
//    }
//
//    private void onRadioGroup_position_third() {
//        radioGroup_position_third.clearCheck();
//        radioGroup_position_second.clearCheck();
//        isCheckedRadioGroup_position_first = false;
//        isCheckedRadioGroup_position_second = false;
//        isCheckedRadioGroup_position_third = true;
//    }
//
//    private void onRadioGroup_genre_first() {
//        radioGroup_genre_secondLine.clearCheck();
//        radioGroup_genre_thirdLine.clearCheck();
//        isCheckedRadioGroup_genre_second = false;
//        isCheckedRadioGroup_genre_third = false;
//        isCheckedRadioGroup_genre_first = true;
//    }
//
//    private void onRadioGroup_genre_second() {
//        radioGroup_genre_firstLine.clearCheck();
//        radioGroup_genre_thirdLine.clearCheck();
//        isCheckedRadioGroup_genre_first = false;
//        isCheckedRadioGroup_genre_third = false;
//        isCheckedRadioGroup_genre_second = true;
//    }
//
//    private void onRadioGroup_genre_third() {
//        radioGroup_genre_firstLine.clearCheck();
//        radioGroup_genre_secondLine.clearCheck();
//        isCheckedRadioGroup_genre_first = false;
//        isCheckedRadioGroup_genre_second = false;
//        isCheckedRadioGroup_genre_third = true;
//    }


    public String[] cityList = {
            "선택하지않음", "서울특별시", "경기도", "강원도", "인천광역시",
            "충청북도", "충청남도", "세종특별시", "대전광역시", "경상북도",
            "경상남도", "대구광역시", "부산광역시", "울산광역시", "전라북도",
            "전라남도", "광주광역시", "제주특별자치도"
    };

    public String[] Town_Default = {
            "선택하지않음"
    };


    // 선택하지않음 = 1
    // 서울시특별시 = 2
    public String[] Seoul = {"선택하지않음",
            "강남구", "강동구", "강서구", "강북구", "관악구",
            "광진구", "구로구", "금천구", "노원구", "도봉구",
            "동대문구", "동작구", "마포구", "서대문구", "서초구",
            "성동구", "성북구", "송파구", "양천구", "영등포구",
            "용산구", "은평구", "중량구", "중구"
    };
    // 경기도 = 3
    public String[] Gyeonggi = {"선택하지않음",
            "가평군", "고양시 덕양구", "고양시 일산동구", "고양시 일산서구", "과천시",
            "광명시", "광주시", "구리시", "군포시", "김포시",
            "남양주시", "동두천시", "부천시", "부천시 소사구", "부천시 오정구",
            "부천시 원미구", "성남시 분당구", "성남시 수정구", "성남시 중원구", "수원시 권선구",
            "수원시 영통구", "수원시 장안구", "수원시 팔달구", "시흥시", "안산시 단원구",
            "안산시 상록구", "안성시", "안양시 동안구", "안양시 만안구", "양주시",
            "양평군", "여주시", "연천군", "오산시", "용인시 기흥구",
            "용인시 수지구", "용인시 처인구", "의왕시", "의정부시", "이천시",
            "파주시", "평택시", "포천시", "하남시", "화성시",
    };
    //강원도 = 4
    public String[] Gangwon = {"선택하지않음",
            "강릉시", "고성군", "삼척시", "동해시", "속초시",
            "양양군", "양구군", "영월군", "원주시", "인제군",
            "정선군", "철원군", "춘천시", "태백시", "평창군",
            "화천군", "홍천군", "횡성군"
    };
    //인천광역시 = 5
    public String[] Incheon = {"선택하지않음",
            "중구", "동구", "남구", "연수구", "남동구",
            "부평구", "계양구", "서구", "강화군", "옹진군"
    };
    //충청북도 = 6
    public String[] ChungBook = {"선택하지않음",
            "청주시", "청주시 상당구", "청주시 서원구", "청주시 흥덕구", "청주시 청원구",
            "충주시", "제천시", "보은군", "옥천군", "영동군",
            "진천군", "괴산군", "음성군", "단양군", "증평군"
    };
    //충청남도 = 7
    public String[] ChungNam = {"선택하지않음",
            "천안시 동남구", "천안시 서북구", "공주시", "보령시", "아산시",
            "서산시", "논산시", "계룡시", "당진시", "금산군",
            "부여군", "서천군", "청양군", "홍성군", "예산군",
            "태안군"
    };
    //세종특별자치시 = 8
    public String[] Sejong = {"선택하지않음",
            "세종시"
    };
    //대전 = 9
    public String[] DaeJeon = {"선택하지않음",
            "중구", "동구", "서구", "유성구", "대덕구"
    };
    //경상북도 = 10
    public String[] GyongBook = {"선택하지않음",
            "포항시", "포항시 남구", "포항시 북구", "경주시", "김천시",
            "안동시", "구미시", "영주시", "영천시", "상주시",
            "문경시", "경산시", "군위군", "의성군", "청송군",
            "영양군", "영덕군", "청도군", "고령군", "성주군",
            "칠곡군", "예천군", "봉화군", "울진군", "울릉군",
    };
    //경상남도 = 11
    public String[] GyonNam = {"선택하지않음",
            "창원시", "창원시 의창구", "창원시 성산구", "창원시 마산합포구", "창원시 마산회권구",
            "창원시 진해구", "진주시", "통영시", "사천시", "김해시",
            "밀양시", "거제시", "양산시", "의령군", "함안군",
            "창녕군", "고성군", "남해군", "하동군", "산청군",
            "함양군", "거창군", "합천군"
    };
    //대구 = 12
    public String[] DaeGu = {"선택하지않음",
            "중구", "동구", "서구", "남구", "북구",
            "수성구", "달서구", "달성군"
    };
    //부산 = 13
    public String[] Busan = {"선택하지않음",
            "중구", "서구", "동구", "영도구", "부산진구",
            "동래구", "남구", "북구", "해운대구", "사하구",
            "금정구", "강서구", "연제구", "수영구", "사상구",
            "기장군"
    };
    //울산 = 14
    public String[] Ulsan = {"선택하지않음",
            "중구", "남구", "동구", "북구", "울주군"
    };
    //전라북도 = 15
    public String[] JeonBook = {"선택하지않음",
            "전주시", "전주시 완산구", "전주시 덕진구", "군산시", "익산시",
            "정읍시", "남원시", "김제시", "완주군", "진안군",
            "무주군", "장수군", "임실군", "순창군", "고창군",
            "부안군"
    };
    //전라남도 = 16
    public String[] JeonNam = {"선택하지않음",
            "목포시", "여수시", "순천시", "나주시", "광양시",
            "담양군", "곡성군", "구례군", "고흥군", "보성군",
            "화순군", "장흥군", "강진군", "해남군", "영양군",
            "무안군", "함평군", "영광군", "장성군", "완도군",
            "진도군", "신안군"
    };
    //광주 = 17
    public String[] Gwangju = {"선택하지않음",
            "동구", "서구", "남구", "북구", "광산구"
    };
    //제주특별자치도 = 18
    public String[] Jeju = {"선택하지않음",
            "제주시", "서귀포시"
    };




}
