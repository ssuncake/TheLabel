package team.nuga.thelabel.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Label implements Serializable {
    @SerializedName("label_id")
    private int labelID; // 레이블 셀렉에서 이걸로사용

    @SerializedName("authority")
    private int labelLeaderID;

    @SerializedName("label_name")
    private String labelName;

    @SerializedName("text")
    private String labelProfile;

    @SerializedName("label_need_position")
    private String[] labelNeedPositionList;

    @SerializedName("label_genre")
    private String labelGenre;

    @SerializedName("label_image_path")
    private String image_path;

    private int labelILike;


    public boolean isNeed(){
       if(labelNeedPositionList.length==0 || labelNeedPositionList==null || labelNeedPositionList[0].equals("선택하지않음"))
           return false;
        return true;
    }

    public int getLabelID() {
        return labelID;
    }

    public void setLabelID(int labelID) {
        this.labelID = labelID;
    }

    public int getLabelILike() {
        return labelILike;
    }

    public void setLabelILike(int labelILike) {
        this.labelILike = labelILike;
    }

    public int getLabelLeaderID() {
        return labelLeaderID;
    }

    public void setLabelLeaderID(int labelLeaderID) {
        this.labelLeaderID = labelLeaderID;
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

    public void setLabelProfile(String labelProfile) {
        this.labelProfile = labelProfile;
    }

    public String[] getLabelNeedPositionList() {
        return labelNeedPositionList;
    }

    public void setLabelNeedPositionList(String[] labelNeedPositionList) {
        this.labelNeedPositionList = labelNeedPositionList;
    }

    public String getLabelGenre() {
        return labelGenre;
    }

    public void setLabelGenre(String labelGenre) {
        this.labelGenre = labelGenre;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }



}
