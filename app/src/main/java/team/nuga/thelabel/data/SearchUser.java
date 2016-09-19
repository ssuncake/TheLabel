package team.nuga.thelabel.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-09-19.
 */
public class SearchUser implements Serializable {
    @SerializedName("nickname")
    private String searchUserName;
    @SerializedName("image_path")
    private String searchUserImage;
    @SerializedName("position")
    private String searchUserPosition;
    @SerializedName("genre")
    private String searchUserGenre;
    @SerializedName("city")
    private String searchUserCity;
    @SerializedName("town")
    private String searchUserTown;
    @SerializedName("need")
    private int searchUserNeed;

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

    public String getSearchUserGenre() {
        return searchUserGenre;
    }

    public void setSearchUserGenre(String searchUserGenre) {
        this.searchUserGenre = searchUserGenre;
    }

    public String getSearchUserCity() {
        return searchUserCity;
    }

    public void setSearchUserCity(String searchUserCity) {
        this.searchUserCity = searchUserCity;
    }

    public String getSearchUserTown() {
        return searchUserTown;
    }

    public void setSearchUserTown(String searchUserTown) {
        this.searchUserTown = searchUserTown;
    }

    public int getSearchUserNeed() {
        return searchUserNeed;
    }

    public void setSearchUserNeed(int searchUserNeed) {
        this.searchUserNeed = searchUserNeed;
    }
}
