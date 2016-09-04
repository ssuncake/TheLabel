package team.nuga.thelabel.data;

/**
 * Created by 우리집 on 2016-09-03.
 */
public class Member {
    int user_id;
    String user_nickname; // 레이블 메인에선 이거
    String user_name; // 레이블 멤버 더보기에선 이건

    String user_possition;
    String user_imagepath;

    public int getUser_id() {
        return user_id;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public String getUser_possition() {
        return user_possition;
    }

    public String getUser_imagepath() {
        return user_imagepath;
    }

    public String getUser_name() {
        return user_name;
    }
}
