package team.nuga.thelabel.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Contents implements Serializable{

    public static final int MUSIC = 0;
    public static final int PICTURE = 1;
    public static final int YOUTUBE = 2;


    @SerializedName("filetype")
    protected int contentsType;
    @SerializedName("id")
    private int contentsID;
    @SerializedName("file_path")
    private String contentsPath;

    @SerializedName("numlike")
    private int likeCount;
    @SerializedName("date")
    private String ContentTime;


    public String getContentTime() {
        return ContentTime;
    }

    public void setContentTime(String contentTime) {
        ContentTime = contentTime;
    }


    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }


    public String getContentsText() {
        return contentsText;
    }

    public void setContentsText(String contentsText) {
        this.contentsText = contentsText;
    }

    private String contentsText;
    public int getContentsType() {
        return contentsType;
    }

    public void setContentsType(int contentsType) {
        this.contentsType = contentsType;
    }

    public int getContentsID() {
        return contentsID;
    }

    public void setContentsID(int contentsID) {
        this.contentsID = contentsID;
    }

    public String getContentsPath() {
        return contentsPath;
    }

    public void setContentsPath(String contentsPath) {
        this.contentsPath = contentsPath;
    }



    }





