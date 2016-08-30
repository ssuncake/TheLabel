package team.nuga.thelabel.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class User implements Serializable{
    private int userID;
    @SerializedName("nickname")
    private String userName;
    @SerializedName("email")
    private String userEmail;
    @SerializedName("password")
    private String userPassword;

    private String userSex;
    private String userProfile;
    private String userLocal;

    private Position userPosition;
    private Genre userGenre;
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

    public ArrayList<Label> getUserInLabelList() {
        return userInLabelList;
    }

    public String getEmail() {
        return userEmail;
    }
}
