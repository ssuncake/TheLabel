package team.nuga.thelabel.Data;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Label {
    private int labelID;
    private int labelILike;
    private int labelLeaderID;
    private boolean labelNeed;
    private String labelName;
    private String labelProfile;
    private ArrayList<User> labelMemberList;
    private ArrayList<Position> labelNeedPositionList;
    private ArrayList<Ganre> labelGanre;
    private HashSet<Integer> labelLikeUser;

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

    public boolean isLabelNeed() {
        return labelNeed;
    }

    public void setLabelNeed(boolean labelNeed) {
        this.labelNeed = labelNeed;
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

    public ArrayList<User> getLabelMemberList() {
        return labelMemberList;
    }

    public void setLabelMemberList(ArrayList<User> labelMemberList) {
        this.labelMemberList = labelMemberList;
    }

    public ArrayList<Position> getLabelNeedPositionList() {
        return labelNeedPositionList;
    }

    public void setLabelNeedPositionList(ArrayList<Position> labelNeedPositionList) {
        this.labelNeedPositionList = labelNeedPositionList;
    }

    public ArrayList<Ganre> getLabelGanre() {
        return labelGanre;
    }

    public void setLabelGanre(ArrayList<Ganre> labelGanre) {
        this.labelGanre = labelGanre;
    }

    public HashSet<Integer> getLabelLikeUser() {
        return labelLikeUser;
    }

    public void setLabelLikeUser(HashSet<Integer> labelLikeUser) {
        this.labelLikeUser = labelLikeUser;
    }


}
