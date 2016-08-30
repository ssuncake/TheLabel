package team.nuga.thelabel.data;

import java.io.Serializable;

import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Genre implements Serializable {

    String[] genre = {
            String.valueOf(R.string.default_choice),                //1 선택하지 않음
            String.valueOf(R.string.text_genre_Song),               //2 가요
            String.valueOf(R.string.text_genre_Pop),                //3 팝
            String.valueOf(R.string.text_genre_RapHiphop),          //4 랩/힙합
            String.valueOf(R.string.text_genre_Rock),               //5 락
            String.valueOf(R.string.text_genre_AcousticPork),       //6 어쿠스틱/포크
            String.valueOf(R.string.text_genre_Electronica),        //7 일렉트로니카
            String.valueOf(R.string.text_genre_NewAge),             //8 뉴에이지
            String.valueOf(R.string.text_genre_RnBSoul),            //9 R&B / Soul
            String.valueOf(R.string.text_genre_Jazz),               //10 재즈
            String.valueOf(R.string.text_genre_CCM),                //11 CCM
    };
}
