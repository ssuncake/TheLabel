package team.nuga.thelabel.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class SearchLabel implements Serializable {

//    텍스트 레이블 검색
    @SerializedName("id")
    private int searchLabelId;
    @SerializedName("name")
    private String searchLabelName;
    @SerializedName("image_path")
    private String searchLabelImage;
    @SerializedName("genre")
    private String searchLabelGenre;
    @SerializedName("needposition")
    private String searchLabelPosition;
    @SerializedName("need")
    private int searchLabelNeed;
    @SerializedName("like")
    private int searchLike;


    public int getSearchLike() {
        return searchLike;
    }

    public void setSearchLike(int searchLike) {
        this.searchLike = searchLike;
    }


    public int getSearchLabelNeed() {
        return searchLabelNeed;
    }

    public void setSearchLabelNeed(int searchLabelNeed) {
        this.searchLabelNeed = searchLabelNeed;
    }

    public String getSearchLabelPosition() {
        return searchLabelPosition;
    }

    public void setSearchLabelPosition(String searchLabelPosition) {
        this.searchLabelPosition = searchLabelPosition;
    }

    public int getSearchLabelId() {
        return searchLabelId;
    }

    public void setSearchLabelId(int searchLabelId) {
        this.searchLabelId = searchLabelId;
    }

    public String getSearchLabelName() {
        return searchLabelName;
    }

    public void setSearchLabelName(String searchLabelName) {
        this.searchLabelName = searchLabelName;
    }

    public String getSearchLabelImage() {
        return searchLabelImage;
    }

    public void setSearchLabelImage(String searchLabelImage) {
        this.searchLabelImage = searchLabelImage;
    }

    public String getSearchLabelGenre() {
        return searchLabelGenre;
    }

    public void setSearchLabelGenre(String searchLabelGenre) {
        this.searchLabelGenre = searchLabelGenre;
    }

}
