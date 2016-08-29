package team.nuga.thelabel.data;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class User implements Serializable{
    private int userID;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userSex;
    private String userProfile;
    private String userLocal;
    private Drawable userProfileImage;
    private Position userPosition;
    private Ganre userGanre;
    private ArrayList<Label> userInLabelList;
   private HashSet<Integer> userLikeContents;
    private HashSet<Integer> userLikeLabel;
    private boolean userNeed;

    public void addLabelList(Label label){
            if(userInLabelList==null){
                userInLabelList = new ArrayList<Label>();
                userInLabelList.add(label);
            }else{
                userInLabelList.add(label);
            }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Drawable getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(Drawable userProfileImage) {
        this.userProfileImage = userProfileImage;
    }



    public ArrayList<Label> getUserInLabelList() {
        return userInLabelList;
    }




}
