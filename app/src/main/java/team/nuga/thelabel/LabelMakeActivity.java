package team.nuga.thelabel;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;
import team.nuga.thelabel.data.User;

public class LabelMakeActivity extends AppCompatActivity {

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

    @BindView(R.id.relativeLayout_MakeLabel_needPosition)
    RelativeLayout needPosition;

    @OnClick(R.id.button_LabelMake_Complete)
    public void completeMakeLabel(){
        String name = editTextName.getText().toString();
        String Text = editTextText.getText().toString();
        int genreId = selectGanre;
    }



    User user;
    int selectGanre;
    String[] ITEMS = {"선택안함","가요", "팝", "랩/힙합", "락", "어쿠스틱/포크","일렉트로니카","뉴에이지","R&B/soul","재즈","CCM"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_make);
        user = (User)getIntent().getSerializableExtra(MainActivity.MAINUSER);
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

    }

    // 니드포지션부분 클릭여부 결정
    public void positionsClickable(boolean b){
        for(int j=0 ; j<needPosition.getChildCount(); j++){
            CheckBox c =(CheckBox) needPosition.getChildAt(j);
            c.setChecked(false);
            c.setClickable(b);
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
                case R.id.editText_MakeLabel_InputName:
                    if(editTextName.getText().toString().trim().isEmpty()){
                        inputLayoutName.setError(getString(R.string.makelabel_inputName_err));
                        if(view.requestFocus())
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                    break;
            }
        }
    }
}
