package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelMakeFragment extends Fragment {


    User user;

    Label newLabel;

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

    int selectGanre;


    public LabelMakeFragment() {
        // Required empty public constructor
    }


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       user = (User)getArguments().getSerializable(MainActivity.MAINUSER);
    }

    @OnClick(R.id.button_LabelMake_Complete)
    public void completeMakeLabel(){
        String name = editTextName.getText().toString();
        String Text = editTextText.getText().toString();
        int genreId = selectGanre;


    }

    @OnClick(R.id.button_LabelMake_Cancel)
    public void cancelMakeLabel(){
        LabelContainerFragment parent  = (LabelContainerFragment)getParentFragment();
        parent.backSelectLabel();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_label_make, container, false);
        ButterKnife.bind(this,view);
        String[] ITEMS = {"선택안함","가요", "팝", "랩/힙합", "락", "어쿠스틱/포크","일렉트로니카","뉴에이지","R&B/soul","재즈","CCM"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS);
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


        return view;
    }

    public void positionsClickable(boolean b){
        for(int j=0 ; j<needPosition.getChildCount(); j++){
            CheckBox c =(CheckBox) needPosition.getChildAt(j);
            c.setChecked(false);
            c.setClickable(b);
        }
    }


    private class ErrorWatcher implements TextWatcher{

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
                            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                    break;
            }
        }
    }

}
