package team.nuga.thelabel.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import team.nuga.thelabel.Debug;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.CheckEmailResult;
import team.nuga.thelabel.data.RoundImageTransform;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.CheckNicknameRequest;

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
        Toast.makeText(getContext(), "설정완료", Toast.LENGTH_SHORT).show();
        if (Debug.debugmode)
            Log.i("profile Info", "---------------------------------------------------");
////        String inputUserName= inputName.getText().toString();
//        user.setUserName(inputUserName);
        if (Debug.debugmode)
            Log.i(" 유저 정보1 ", " 닉네임 : " + nickname + ", 사용자 ID" + user.getUserID());
        if (Debug.debugmode) Log.i(" 유저 정보2 ", " 자기소개 :" + textInputEditText_introText.getText());
        if (Debug.debugmode) Log.i(" 유저 정보3 ", " 이미지Url:" + user.getImageUrl());
        if (Debug.debugmode)
            Log.i(" 유저 정보4 ", ", 포지션:" + positionId + ", 장르:" + genreId + ", 시/도 :" + cityId + ", 시군구:" + townId + ", Need :" + need);
////        MainActivity mainActivity = (MainActivity)getActivity();
////        mainActivity.drawerUserSetting(inputUserName);
////        mainActivity.goMainFragment(MainFragment.USERTAB);
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

    }



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

    public void preUserInfoSetting(){
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
        spinner_position.setSelection(user.getPostition()-1);
        spinner_genre.setSelection(user.getGenre()-1);
        spinner_city.setSelection(user.getCity()-1);

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

        String[] positionList = getResources().getStringArray(R.array.positionlist);
        String[] genreList = getResources().getStringArray(R.array.genrelist);

        ArrayAdapter spinner_cityAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, cityList);
        spinner_cityAdapter.setDropDownViewResource(R.layout.spin_profile);
        spinner_city.setAdapter(spinner_cityAdapter);
        ArrayAdapter positionAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, positionList);
        positionAdapter.setDropDownViewResource(R.layout.spin_profile);
        spinner_position.setAdapter(positionAdapter);
        ArrayAdapter genreAdapter = new ArrayAdapter(getContext(), R.layout.spin_profile, genreList);
        positionAdapter.setDropDownViewResource(R.layout.spin_profile);
        spinner_genre.setAdapter(genreAdapter);
        int color = Color.parseColor("#ffffffff");
        textInputEditText_introText.addTextChangedListener(introTextWatcher);
        textInputEditText_introText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        textInputEditText_introText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(getContext(), "Action Done", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        textInputLayout_introtext.setCounterEnabled(true);
        textInputLayout_introtext.setCounterMaxLength(60);

        editText_userNickName.addTextChangedListener(nicknameWatcher);
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
                editText_userNickName.setSelection(textInputEditText_introText.length());
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
                textInputLayout_introtext.setError("error");
            } else {
                textInputLayout_introtext.setError(null);
            }
            if (textInputEditText_introText.getLineCount() > LIMITTED_TEXTLINE) { //라인수 제한
                textInputEditText_introText.setText(before);
                textInputEditText_introText.setSelection(textInputEditText_introText.length());
            }
        }
    };


    int townId = 0;
    int cityId = 0;
    int positionId = 1; //Default = 1 ; 선택안함
    int genreId = 1;  // Default = 1 ; 선택안함




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
