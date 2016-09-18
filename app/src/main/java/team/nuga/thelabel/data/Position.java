package team.nuga.thelabel.data;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Position implements Serializable {
    public static final int EMPTY = 1;
    public static final int VOCAL = 2;
    public static final int GUITAR = 3;
    public static final int BASE = 4;
    public static final int ELEC = 5;
    public static final int DRUM= 6;
    public static final int KEYBOARD = 7;
    public static final int ETC = 8;

    public static final String[] POSITIONS = {"선택안함","보컬","기타","베이스","일렉기타","드럼","키보드","Etc"};
    
    private int id;
    private String position;

    public int getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }
}
