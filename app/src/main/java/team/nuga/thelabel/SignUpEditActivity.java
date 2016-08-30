package team.nuga.thelabel;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpEditActivity extends AppCompatActivity {
    @BindView(R.id.radioGroup_userSex)
    RadioGroup radioGroup_userSex;
    @BindView(R.id.radioGroup_position_first)
    RadioGroup radioGroup_position_first;
    @BindView(R.id.radioGroup_position_second)
    RadioGroup radioGroup_position_second;
    @BindView(R.id.radioGroup_genre_firstLine)
    RadioGroup radioGroup_genre_firstLine;
    @BindView(R.id.radioGroup_genre_secondLine)
    RadioGroup radioGroup_genre_secondLine;
    @BindView(R.id.radioGroup_genre_thirdLine)
    RadioGroup radioGroup_genre_thirdLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sign_up);
        ButterKnife.bind(this);

        RadioGroupClick();


    }


    boolean isCheckedRadioGroup_position_first = false;   //포지션 라디오그룹 체크상태
    boolean isCheckedRadioGroup_position_second = false;   //포지션 라디오그룹 체크상태
    boolean isCheckedRadioGroup_genre_first = false;      //장르 라디오그룹 체크상태
    boolean isCheckedRadioGroup_genre_second = false;      //장르 라디오그룹 체크상태
    boolean isCheckedRadioGroup_genre_third = false;      //장르 라디오그룹 체크상태

    int positionId = 1;
    int genreId = 1;

    private void RadioGroupClick() {
        radioGroup_position_first.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedPosition) {
                switch (checkedPosition) {
                    case R.id.radioButton_vocal:
                        isCheckedRadioGroup_position_first = true;
                        if (isCheckedRadioGroup_position_second == true) {
                            onRadioGroup_position_first();
                            radioGroup_position_first.check(checkedPosition);
                        }
                        break;
                    case R.id.radioButton_piano:
                        isCheckedRadioGroup_position_first = true;
                        if (isCheckedRadioGroup_position_second == true) {
                            onRadioGroup_position_first();
                            radioGroup_position_first.check(checkedPosition);
                        }
                        break;
                    case R.id.radioButton_accoustic:
                        isCheckedRadioGroup_position_first = true;
                        if (isCheckedRadioGroup_position_second == true) {
                            onRadioGroup_position_first();
                            radioGroup_position_first.check(checkedPosition);
                        }
                        break;
                }
            }
        });
        radioGroup_position_second.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedPosition) {
                switch (checkedPosition) {
                    case R.id.radioButton_elec:
                        isCheckedRadioGroup_position_second = true;
                        if (isCheckedRadioGroup_position_first == true) {
                            onRadioGroup_position_second();
                            radioGroup_position_second.check(checkedPosition);
                        }
                        break;
                    case R.id.radioButton_base:
                        isCheckedRadioGroup_position_second = true;
                        if (isCheckedRadioGroup_position_first == true) {
                            onRadioGroup_position_second();
                            radioGroup_position_second.check(checkedPosition);
                        }
                        break;
                    case R.id.radioButton_etc:
                        isCheckedRadioGroup_position_second = true;
                        if (isCheckedRadioGroup_position_first == true) {
                            onRadioGroup_position_second();
                            radioGroup_position_second.check(checkedPosition);
                        }
                        break;
                }
            }
        });
        radioGroup_genre_firstLine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedGenre) {
                switch (checkedGenre) {
                    case R.id.radioButton_Song:
                        isCheckedRadioGroup_genre_first = true;
                        onRadioGroup_genre_first();
                        radioGroup_genre_firstLine.check(checkedGenre);
                    case R.id.radioButton_RnBSOUL:
                        isCheckedRadioGroup_genre_first = true;
                        onRadioGroup_genre_first();
                        radioGroup_genre_firstLine.check(checkedGenre);
                    case R.id.radioButton_Rock:
                        isCheckedRadioGroup_genre_first = true;
                        onRadioGroup_genre_first();
                        radioGroup_genre_firstLine.check(checkedGenre);
                    case R.id.radioButton_Jazz:
                        isCheckedRadioGroup_genre_first = true;
                        onRadioGroup_genre_first();
                        radioGroup_genre_firstLine.check(checkedGenre);
                }
            }
        });
        radioGroup_genre_secondLine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedGenre) {
                switch (checkedGenre) {
                    case R.id.radioButton_RapHiphop:
                        isCheckedRadioGroup_genre_second = true;
                        onRadioGroup_genre_second();
                        radioGroup_genre_secondLine.check(checkedGenre);
                    case R.id.radioButton_NewAge:
                        isCheckedRadioGroup_genre_second = true;
                        onRadioGroup_genre_second();
                        radioGroup_genre_secondLine.check(checkedGenre);
                    case R.id.radioButton_Pop:
                        isCheckedRadioGroup_genre_second = true;
                        onRadioGroup_genre_second();
                        radioGroup_genre_secondLine.check(checkedGenre);
                    case R.id.radioButton_CCM:
                        isCheckedRadioGroup_genre_second = true;
                        onRadioGroup_genre_second();
                        radioGroup_genre_secondLine.check(checkedGenre);
                }
            }
        });
        radioGroup_genre_thirdLine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedGenre) {
                switch (checkedGenre) {
                    case R.id.radioButton_Electronica:
                        isCheckedRadioGroup_genre_third = true;
                        onRadioGroup_genre_third();
                        radioGroup_genre_thirdLine.check(checkedGenre);
                    case R.id.radioButton_AcousticPork:
                        isCheckedRadioGroup_genre_third = true;
                        onRadioGroup_genre_third();
                        radioGroup_genre_thirdLine.check(checkedGenre);
                }
            }
        });
    }


    private void onRadioGroup_position_first() {
        radioGroup_position_second.clearCheck();
        isCheckedRadioGroup_position_second = false;
        isCheckedRadioGroup_position_first = true;
        Toast.makeText(SignUpEditActivity.this, "position_fist", Toast.LENGTH_SHORT).show();
    }

    private void onRadioGroup_position_second() {
        radioGroup_position_first.clearCheck();
        isCheckedRadioGroup_position_first = false;
        isCheckedRadioGroup_position_second = true;
        Toast.makeText(SignUpEditActivity.this, "position_second", Toast.LENGTH_SHORT).show();
    }

    private void onRadioGroup_genre_first() {
        radioGroup_genre_secondLine.clearCheck();
        radioGroup_genre_thirdLine.clearCheck();
        isCheckedRadioGroup_genre_second = false;
        isCheckedRadioGroup_genre_third = false;
        isCheckedRadioGroup_genre_first = true;
        Toast.makeText(SignUpEditActivity.this, "genre_1st", Toast.LENGTH_SHORT).show();
    }

    private void onRadioGroup_genre_second() {
        radioGroup_genre_firstLine.clearCheck();
        radioGroup_genre_thirdLine.clearCheck();
        isCheckedRadioGroup_genre_first = false;
        isCheckedRadioGroup_genre_third = false;
        isCheckedRadioGroup_genre_second = true;
        Toast.makeText(SignUpEditActivity.this, "genre_2nd", Toast.LENGTH_SHORT).show();
    }

    private void onRadioGroup_genre_third() {
        radioGroup_genre_firstLine.clearCheck();
        radioGroup_genre_secondLine.clearCheck();
        isCheckedRadioGroup_genre_first = false;
        isCheckedRadioGroup_genre_second = false;
        isCheckedRadioGroup_genre_third = true;
        Toast.makeText(SignUpEditActivity.this, "genre_3rd", Toast.LENGTH_SHORT).show();
    }
}
