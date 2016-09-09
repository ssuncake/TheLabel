package team.nuga.thelabel.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 우리집 on 2016-09-03.
 */
public class Member {
    @SerializedName("id")
    int user_id;

    @SerializedName("nickname")
    String user_nickname; // 레이블 메인에선 이거

    @SerializedName("imagepath")
    String user_imagepath;

    @SerializedName("position")
    String user_possition;

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

}
