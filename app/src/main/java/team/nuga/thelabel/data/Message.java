package team.nuga.thelabel.data;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Message implements Serializable {
   int id; // 메세지아이디

    int user_id; // 기기아이디
    int you_user_id;
    String text;
    String date;

    public int getYou_user_id() {
        return you_user_id;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}
