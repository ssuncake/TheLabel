package team.nuga.thelabel.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tacademy on 2016-09-05.
 */
public class NetworkResultMyAccount {
    User result;
    @SerializedName("post")
    Contents[] data;

    public User getResult() {
        return result;
    }

    public Contents[] getData() {
        return data;
    }
}
