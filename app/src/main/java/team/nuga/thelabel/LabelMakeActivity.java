package team.nuga.thelabel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;
import team.nuga.thelabel.data.NetworkLabelNameCheck;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.fragment.MainFragment;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.LabelMakeRequest;
import team.nuga.thelabel.request.LabelNameCheckRequest;

public class LabelMakeActivity extends AppCompatActivity {

    public static int REQEST_IMAGESETTING = 550;
    public static final String LOGTAG = "LabelMakeActivity ";

    @BindView(R.id.textLayout_MakeLabel_InputName)
    TextInputLayout inputLayoutName;
    @BindView(R.id.textLaout_MakeLabel_InputText)
    TextInputLayout inputLayoutText;
    @BindView(R.id.editText_MakeLabel_InputName)
    EditText editTextName;
    @BindView(R.id.editText_MakeLabel_InputText)
    EditText editTextText;
    @BindView(R.id.spinnerView_MakeLabel_Genre)
    MaterialSpinner spinner;
    @BindView(R.id.radioGroup_MakeLabel_Need)
    RadioGroup needSelect;
    @BindView(R.id.imageView_MakeLabel_LabelImage)
    ImageView labelImage;

    @BindView(R.id.relativeLayout_MakeLabel_needPosition)
    RelativeLayout needPosition;
    @BindView(R.id.imageButton_LabelMake_ImageSetting)
    ImageView imageSetting;


    // 체크박스 부분
    @BindView(R.id.checkBox_MakeLabel_vocal) // 2
    CheckBox vocal;
    @BindView(R.id.checkBox_MakeLabel_acoustic) // 3
    CheckBox acoustic;
    @BindView(R.id.checkBox_MakeLabel_base) // 4
    CheckBox base;
    @BindView(R.id.checkBox_MakeLabel_elec)  // 5
    CheckBox elec;
    @BindView(R.id.checkBox_MakeLabel_drum) // 6
    CheckBox drum;
    @BindView(R.id.checkBox_MakeLabel_piano) // 7
            CheckBox keyboard;
    @BindView(R.id.checkBox_MakeLabel_etc) // 8
    CheckBox etc;



    @OnClick(R.id.imageButton_LabelMake_ImageSetting)
    public void imageSetting(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQEST_IMAGESETTING);
    }

    @OnClick(R.id.button_LabelName_Check)
    public void checkLabelId(){
        inputLabelName = editTextName.getText().toString();
        LabelNameCheckRequest request = new LabelNameCheckRequest(inputLabelName);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkLabelNameCheck>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkLabelNameCheck> request, NetworkLabelNameCheck result) {
                if(result.isError()){
                    Log.e(LOGTAG,"레이블 이름 중복 네트워크는 성공"+result.getError().getMessage());
                }else{
                    if(result.getMatch() == 0){
                        Toast.makeText(LabelMakeActivity.this, "중복되는 아이디 없습니다.", Toast.LENGTH_SHORT).show();
                        labelNameCheck = true;
                    }else{
                        Toast.makeText(LabelMakeActivity.this, "아이디가 중복됩니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkLabelNameCheck> request, int errorCode, String errorMessage, Throwable e) {
                Log.e(LOGTAG,"레이블 이름 중복 체크실패"+errorMessage);
            }
        });
    }


    @OnClick(R.id.button_LabelMake_Complete)
    public void completeMakeLabel(){
        inputLabelName = editTextName.getText().toString();

        if(labelNameCheck){
            if(!inputLabelName.equals("")){
                inputText = editTextText.getText().toString();
                if(inputPositions.size()==0 ) {
                    inputPositions.add(1);
                }else if(inputPositions.size()==1){
                }else{
                    if(inputPositions.get(0).equals(1)){
                        inputPositions.remove((Object)Integer.valueOf(1));
                    }
                }
                Collections.sort(inputPositions);
                if(selectGanre==0){
                    selectGanre=1;
                }

                LabelMakeRequest request = new LabelMakeRequest(this,inputLabelName,selectGanre,inputText,imagefile,inputPositions);
                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                        if(result.isError()){
                            Log.w(LOGTAG,"에러발생 : "+result.getError().getMessage());
                        }else{
                            Toast.makeText(LabelMakeActivity.this,result.getMessage().toString()+"",Toast.LENGTH_SHORT);
                            Intent intent = new Intent();
                            intent.putExtra(MainActivity.TABINDEX, MainFragment.LABELTAB);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                        Log.w(LOGTAG,"레이블: "+errorMessage);
                    }
                });
            }else {
                Toast.makeText(LabelMakeActivity.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
            }
        }
    }



    User user;
    Boolean labelNameCheck = false;
    String[] ITEMS = {"선택안함","가요", "팝", "랩/힙합", "락", "어쿠스틱/포크","일렉트로니카","뉴에이지","R&B/soul","재즈","CCM"};


    File imagefile = null;
    String inputLabelName;

    int selectGanre;
    String inputText;
    List<Integer> inputPositions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_make);
        user = (User)getIntent().getSerializableExtra(MainActivity.MAINUSER);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_LabelMake);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        //장르선택 스피너 부분
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectGanre = 1+i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectGanre = 1;
            }
        });


        // NEED 설정 부분
        needPosition.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        positionsClickable(false);
        editTextName.addTextChangedListener(new ErrorWatcher(editTextName));

        needSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (needSelect.getCheckedRadioButtonId()){
                    case R.id.radioButton_MakeLabel_On :
                        Log.e("레이블 생성","need on");
                        positionsClickable(true);
                        break;
                    case R.id.radioButton_MakeLabel_Off:
                        Log.e("레이블 생성","need off");
                        positionsClickable(false);

                        break;
                }
            }
        });


        vocal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    inputPositions.add(2);
                else
                    inputPositions.remove((Object)Integer.valueOf(2));
            }
        });
        acoustic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    inputPositions.add(3);
                else
                    inputPositions.remove((Object)Integer.valueOf(3));
            }
        });
        base.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    inputPositions.add(4);
                else
                    inputPositions.remove((Object)Integer.valueOf(4));
            }
        });
        elec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    inputPositions.add(5);
                else
                    inputPositions.remove((Object)Integer.valueOf(5));
            }
        });
        drum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    inputPositions.add(6);
                else
                    inputPositions.remove((Object)Integer.valueOf(6));
            }
        });
        keyboard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    inputPositions.add(7);
                else
                    inputPositions.remove((Object)Integer.valueOf(7));
            }
        });
        etc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    inputPositions.add(8);
                else
                    inputPositions.remove((Object)Integer.valueOf(8));
            }
        });



    }

    // 니드포지션부분 클릭여부 결정
    public void positionsClickable(boolean b){
        for(int j=0 ; j<needPosition.getChildCount(); j++){
            CheckBox c =(CheckBox) needPosition.getChildAt(j);
            c.setChecked(false);
            c.setClickable(b);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQEST_IMAGESETTING){
            if(resultCode == Activity.RESULT_OK){
                Uri uri = data.getData();
                Cursor c = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    imagefile = new File(path);
                    Glide.with(this)
                            .load(imagefile)
                            .into(labelImage);
                }

            }
        }
    }

    //에딧 텍스트 이벤트 처리부분
    private class ErrorWatcher implements TextWatcher {

        View view;

        public ErrorWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
//                case R.id.editText_MakeLabel_InputName:
//                    if(editTextName.getText().toString().trim().isEmpty()){
//                        inputLayoutName.setError(getString(R.string.makelabel_inputName_err));
//                        if(view.requestFocus())
//                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                    }
//                break;
            }
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
