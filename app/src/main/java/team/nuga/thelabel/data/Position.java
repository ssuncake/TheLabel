package team.nuga.thelabel.data;

import java.io.Serializable;

import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Position implements Serializable {

    public static String[] position = {String.valueOf(R.string.default_choice),    //1 선택하지않음
             String.valueOf(R.string.text_position_vocal),            //2 보컬
             String.valueOf(R.string.text_position_guitar),           //3 기타
             String.valueOf(R.string.text_position_base),             //4 베이스
             String.valueOf(R.string.text_position_electricGuitar),   //5 일렉기타
             String.valueOf(R.string.text_position_drum),             //6 드럼
             String.valueOf(R.string.text_position_keyboard),         //7 키보드
             String.valueOf(R.string.text_position_etc),              //8 etc...
     };
}
