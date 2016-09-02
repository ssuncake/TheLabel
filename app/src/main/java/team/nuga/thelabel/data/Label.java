package team.nuga.thelabel.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Label implements Serializable {
    @SerializedName("id")
    private int labelID;
    private int labelILike;
    private int labelLeaderID;
    private boolean labelNeed;
    @SerializedName("label_name")
    private String labelName;
    @SerializedName("text")
    private String labelProfile;

    private ArrayList<User> labelMemberList;
    private ArrayList<Position> labelNeedPositionList;
    @SerializedName("need_genre")
    private String labelGenre;

    private HashSet<Integer> labelLikeUser;

    public int getLabelID() {
        return labelID;
    }


    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelProfile() {
        return labelProfile;
    }


    public String getLabelGenre() {
        return labelGenre;
    }





}
