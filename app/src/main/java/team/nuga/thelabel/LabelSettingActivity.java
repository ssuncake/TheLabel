package team.nuga.thelabel;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.GetLabelByIdSettingRequest;

public class LabelSettingActivity extends AppCompatActivity {

    public static final String LOGTAG = "LabelSettingActivity ";
    public static int REQEST_IMAGESETTING = 550;
    public static final int REQUEST_ENTRUSTLEADER = 510;
    public static final int REQUEST_FIREMEMBER = 520;

    @BindView(R.id.imageView_LabelSetting_Image)
    ImageView imageView;
    @BindView(R.id.textLayout_LabelSetting_InputText)
    TextInputLayout inputLayoutText;
    @BindView(R.id.editText_LabelSetting_InputText)
    EditText editTextText;
    @BindView(R.id.spinnerView_LabelSetting_Genre)
    MaterialSpinner spinner;
    @BindView(R.id.radioGroup_LabelSetting_Need)
    RadioGroup needSelect;

    @BindView(R.id.relativeLayout_LabelSetting_needPosition)
    RelativeLayout needPosition;

    // 체크박스 부분
    @BindView(R.id.checkBox_LabelSetting_vocal) // 2
            CheckBox vocal;
    @BindView(R.id.checkBox_LabelSetting_acoustic) // 3
            CheckBox acoustic;
    @BindView(R.id.checkBox_LabelSetting_base) // 4
            CheckBox base;
    @BindView(R.id.checkBox_LabelSetting_elec)  // 5
            CheckBox elec;
    @BindView(R.id.checkBox_LabelSetting_drum) // 6
            CheckBox drum;
    @BindView(R.id.checkBox_LabelSetting_piano) // 7
            CheckBox keyboard;
    @BindView(R.id.checkBox_LabelSetting_etc) // 8
            CheckBox etc;

    @OnClick(R.id.imageButton_LabelSetting_ImageSetting)
    public void imageSetting(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQEST_IMAGESETTING);
    }

    @OnClick(R.id.button_LabelSetting_Complete)
    public void completeMakeLabel(){

        String Text = editTextText.getText().toString();
        int genreId = selectGanre;
    }

    @OnClick(R.id.button_LabelSetting_Entrust)
    public void entrustMember(){
        Intent intent = new Intent(this,EntrustLeaderActivity.class);
        intent.putExtra(MainActivity.SELECTLABEL,label);
        startActivityForResult(intent,REQUEST_ENTRUSTLEADER);
    }

    @OnClick(R.id.button_LabelSetting_Fire)
    public void fireMember(){
        Intent intent = new Intent(this,FireMemberActivity.class);
        intent.putExtra(MainActivity.SELECTLABEL,label);
        startActivityForResult(intent,REQUEST_FIREMEMBER);
    }



    Label label;
    Label newLabel;
    User user;
    int selectGanre;
    String[] ITEMS = {"선택안함","가요", "팝", "랩/힙합", "락", "어쿠스틱/포크","일렉트로니카","뉴에이지","R&B/soul","재즈","CCM"};
//    @BindArray(R.array.genrelist)    String[] ITEMS;


    String inputText;
    List<Integer> inputPositions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_setting);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_LabelSetting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        label=(Label)getIntent().getSerializableExtra(MainActivity.SELECTLABEL);
        int labelId = label.getLabelID();
        Log.e(LOGTAG,"전달받은 레이블 id : "+labelId);

        GetLabelByIdSettingRequest request = new GetLabelByIdSettingRequest(this,label.getLabelID());

        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<Label>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<Label>> request, NetworkResult<Label> result) {
                newLabel = result.getLabel();
                Log.e(LOGTAG,"네트워크받은 레이블 이름 : "+newLabel.getLabelName());
                settingLabel(newLabel);
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<Label>> request, int errorCode, String errorMessage, Throwable e) {
                Log.e(LOGTAG,"네트워크 실패: "+errorMessage);
            }
        });



        //장르선택 스피너 부분
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectGanre = 1+i;
                Log.e("레이블 생성","장르 선택 : "+selectGanre);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectGanre = 1;
                Log.e("레이블 생성","장르 선택 안함");
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

        needSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (needSelect.getCheckedRadioButtonId()){
                    case R.id.radioButton_LabelSetting_On :
                        Log.e("레이블 생성","need on");
                        positionsClickable(true);
                        break;
                    case R.id.radioButton_LabelSetting_Off:
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void settingLabel(Label label){
        if(label!=null) {
            editTextText.setText(label.getLabelProfile());
            spinner.setSelection(label.getGenre_id());
            Glide.with(this)
                    .load(label.getImage_path())
                    .into(imageView);
        }
    }

    File imagefile = null;

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
                            .into(imageView);
                }

            }
        }
        else if(requestCode == REQUEST_ENTRUSTLEADER) {
            if(requestCode == RESULT_OK){
                finish();
            }
        }

    }

}
