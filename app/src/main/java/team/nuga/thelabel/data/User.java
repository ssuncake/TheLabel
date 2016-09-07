package team.nuga.thelabel.data;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

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



    private ArrayList<Label> userInLabelList;

    private String userSex;

    private String userProfile;
    private String userLocal;
    private Position userPosition;
    private Genre userGenre;
    private boolean userNeed;

    public void addLabelList(Label label){
            if(userInLabelList==null){
                userInLabelList = new ArrayList<Label>();
                userInLabelList.add(label);
            }else{
                if(userInLabelList.size() == 3)
                {
                    Log.e("레이블 추가", "최대 허용 할 수 있는 레이블 수 초과해서 add명령어 실행");
                    return;
                }else{
                    int id = label.getLabelID();
                    for(Label l : userInLabelList){
                        if(l.getLabelID() == id ){
                            Log.e("레이블 추가", "id가 같은 레이블존재");
                            return;
                        }
                    }
                    userInLabelList.add(label);
                }

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
