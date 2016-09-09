package team.nuga.thelabel.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class User implements Serializable{

    private int id;
    @SerializedName("nickname")
    private String userName;
    @SerializedName("email")
    private String userEmail;
    @SerializedName("password")
    private String userPassword;
    @SerializedName("image_path")
    private String imageUrl;

    public String getUserPassword() {
        return userPassword;
    }



    private Label[] userInLabelList;

    private String userSex;

    private String userProfile;
    private String userLocal;
    private Position userPosition;
    private Genre userGenre;
    private boolean userNeed;

    public Label[] getUserInLabelList() {
        return userInLabelList;
    }

    public void setUserInLabelList(Label[] userInLabelList) {
        this.userInLabelList = userInLabelList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getEmail() {
        return userEmail;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public int getUserID() {
        return id;
    }
    public long getLongUserID(){
        return (long)id;
    }

    public void setUserID(int userID) {
        this.id = userID;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
