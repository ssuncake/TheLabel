package team.nuga.thelabel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;
import team.nuga.thelabel.data.Label;

public class LabelSettingActivity extends AppCompatActivity {

    public static final int REQUEST_ENTRUSTLEADER = 510;

    @BindView(R.id.textLayout_LabelSetting_InputName)
    TextInputLayout inputLayoutName;
    @BindView(R.id.textLayout_LabelSetting_InputText)
    TextInputLayout inputLayoutText;
    @BindView(R.id.editText_LabelSetting_InputName)
    EditText editTextName;
    @BindView(R.id.editText_LabelSetting_InputText)
    EditText editTextText;
    @BindView(R.id.spinnerView_LabelSetting_Genre)
    MaterialSpinner spinner;
    @BindView(R.id.radioGroup_LabelSetting_Need)
    RadioGroup needSelect;

    @BindView(R.id.relativeLayout_LabelSetting_needPosition)
    RelativeLayout needPosition;

    @OnClick(R.id.button_LabelMake_Complete)
    public void completeMakeLabel(){
        String name = editTextName.getText().toString();
        String Text = editTextText.getText().toString();
        int genreId = selectGanre;
    }

    @OnClick(R.id.button_LabelSetting_Entrust)
            public void EntrustMember(){
        Intent intent = new Intent(this,EntrustLeaderActivity.class);
        intent.putExtra(MainActivity.SELECTLABEL,label);
        startActivityForResult(intent,REQUEST_ENTRUSTLEADER);
    }



    Label label;
    int selectGanre;
    String[] ITEMS = {"선택안함","가요", "팝", "랩/힙합", "락", "어쿠스틱/포크","일렉트로니카","뉴에이지","R&B/soul","재즈","CCM"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_setting);
        ButterKnife.bind(this);

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
                Log.e("레이블 생성","장르 선택 : "+selectGanre);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_ENTRUSTLEADER :
                if(requestCode == RESULT_OK){
                    setResult(MainActivity.REQUEST_SETTINGLABEL,data);
                    finish();
                }
        }
    }
}
