package team.nuga.thelabel.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class User implements Serializable{
    //내계정 유저
    private int id;
    @SerializedName("nickname")
    private String userName;
    @SerializedName("email")
    private String userEmail;
    @SerializedName("password")
    private String userPassword;
    @SerializedName("imagepath")
    private String imageUrl;
    @SerializedName("user_id")
    private int userID;


    //Search User 부분
    @SerializedName("user_nickname")
    private String searchUserName;
    @SerializedName("user_image_path")
    private String searchUserImage;
    @SerializedName("user_position")
    private String searchUserPosition;
    @SerializedName("user_genre")
    private String searchUserGenre;
    @SerializedName("user_city")
    private String searchUserCity;
    @SerializedName("user_town")
    private String searchUserTown;

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
    public String getSearchUserName() {
        return searchUserName;
    }

    public void setSearchUserName(String searchUserName) {
        this.searchUserName = searchUserName;
    }
    public String getSearchUserImage() {
        return searchUserImage;
    }

    public void setSearchUserImage(String searchUserImage) {
        this.searchUserImage = searchUserImage;
    }
    public String getSearchUserPosition() {
        return searchUserPosition;
    }

    public void setSearchUserPosition(String searchUserPosition) {
        this.searchUserPosition = searchUserPosition;
    }

    public String getSearchUserTown() {
        return searchUserTown;
    }

    public void setSearchUserTown(String searchUserTown) {
        this.searchUserTown = searchUserTown;
    }

    public String getSearchUserCity() {
        return searchUserCity;
    }

    public void setSearchUserCity(String searchUserCity) {
        this.searchUserCity = searchUserCity;
    }

    public String getSearchUserGenre() {
        return searchUserGenre;
    }

    public void setSearchUserGenre(String searchUserGenre) {
        this.searchUserGenre = searchUserGenre;
    }

}
