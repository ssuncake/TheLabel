package team.nuga.thelabel.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class User implements Serializable {
    //내계정 유저 기본정보
    private int id;
    @SerializedName("nickname")
    private String userName;
    @SerializedName("email")
    private String userEmail;
    @SerializedName("password")
    private String userPassword;
    @SerializedName("imagepath")
    private String imageUrl;

    public String getUserEmail() {
        return userEmail;
    }

    @SerializedName("user_id")

    private int userID;

    //프로필 기본정보
    @SerializedName("text")
    private String User_intro_text;//유저의 자기소개 //140자 제한
    @SerializedName("need")
    private int need;

    public int getNeed() {
        return need;
    }

    public void setNeed(int need) {
        this.need = need;
    }

    @SerializedName("position_id")
    private int postition; //포지션
    @SerializedName("genre_id")
    private int genre;//장르
    @SerializedName("city_id")
    private int city;
    @SerializedName("town_id")
    private int town;
    @SerializedName("gender_id")
    private int user_gender;


    public String getUserPassword() {
        return userPassword;
    }
    private Label[] userInLabelList;


    private String userProfile;
    private String userLocal;
//    private Position userPosition;
//    private Genre userGenre;
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

    public long getLongUserID() {
        return (long) id;
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

    public String getUser_intro_text() {
        return User_intro_text;
    }

    public void setUser_intro_text(String user_intro_text) {
        User_intro_text = user_intro_text;
    }

    public int getPostition() {
        return postition;
    }

    public void setPostition(int postition) {
        this.postition = postition;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getTown() {
        return town;
    }

    public void setTown(int town) {
        this.town = town;
    }

    public int getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(int user_gender) {
        this.user_gender = user_gender;
    }
    public String getText() {
        return User_intro_text;
    }

    public void setText(String text) {
        this.User_intro_text = text;
    }

    public boolean isMyLabel(int labelId){
        if(userInLabelList==null){
            return false;
        }else{
            for(Label l : userInLabelList){
                if(l.getLabelID() == labelId)
                    return true;
            }
            return false;
        }
    }

}
