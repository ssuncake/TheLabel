package team.nuga.thelabel.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Label implements Serializable {
    @SerializedName("id")
    private int labelID; // 레이블 셀렉에서 이걸로사용

    private int label_id; // 레이블 메인에선 이거사용용
   private int labelILike;
    private int labelLeaderID;

    private boolean labelNeed;
    @SerializedName("label_name")
    private String labelName;
    @SerializedName("text")
    private String labelProfile;


    @SerializedName("need_position")
    private String[] labelNeedPositionList;

    @SerializedName("label_genre")
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

    public int getLabel_id() {
        return label_id;
    }
}
