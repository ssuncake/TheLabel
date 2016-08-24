package team.nuga.thelabel.Data;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class User {
    private int userID;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userSex;
    private String userProfile;
    private String userLocal;
    private Drawable userProfileImage;
    private ArrayList<Position> userPositionList;
    private ArrayList<Ganre> userGanreList;
    private ArrayList<Label> userInLabelList;
   private HashSet<Integer> userLikeContents;
    private HashSet<Integer> userLikeLabel;
    private boolean userNeed;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserLocal() {
        return userLocal;
    }

    public void setUserLocal(String userLocal) {
        this.userLocal = userLocal;
    }

    public Drawable getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(Drawable userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public ArrayList<Position> getUserPositionList() {
        return userPositionList;
    }

    public void setUserPositionList(ArrayList<Position> userPositionList) {
        this.userPositionList = userPositionList;
    }

    public ArrayList<Ganre> getUserGanreList() {
        return userGanreList;
    }

    public void setUserGanreList(ArrayList<Ganre> userGanreList) {
        this.userGanreList = userGanreList;
    }

    public ArrayList<Label> getUserInLabelList() {
        return userInLabelList;
    }

    public void setUserInLabelList(ArrayList<Label> userInLabelList) {
        this.userInLabelList = userInLabelList;
    }

    public HashSet<Integer> getUserLikeContents() {
        return userLikeContents;
    }

    public void setUserLikeContents(HashSet<Integer> userLikeContents) {
        this.userLikeContents = userLikeContents;
    }

    public HashSet<Integer> getUserLikeLabel() {
        return userLikeLabel;
    }

    public void setUserLikeLabel(HashSet<Integer> userLikeLabel) {
        this.userLikeLabel = userLikeLabel;
    }

    public boolean isUserNeed() {
        return userNeed;
    }

    public void setUserNeed(boolean userNeed) {
        this.userNeed = userNeed;
    }

    public User() {

        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userSex = userSex;
        this.userProfile = userProfile;
        this.userLocal = userLocal;
        this.userProfileImage = userProfileImage;
        this.userPositionList = userPositionList;
        this.userGanreList = userGanreList;
        this.userInLabelList = userInLabelList;
        this.userLikeContents = userLikeContents;
        this.userLikeLabel = userLikeLabel;
        this.userNeed = userNeed;
    }
}
