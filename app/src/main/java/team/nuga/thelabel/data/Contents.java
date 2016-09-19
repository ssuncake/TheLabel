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

    public static final int PLAY = 10;
    public static final int PUASE = 20;
    public static final int STOP = 30;

    int playedMode= STOP;
    int playedTIme;
    int playTimeMax;


    @SerializedName("filetype")
    protected int contentsType;
    @SerializedName("id")
    private int contentsID;
    @SerializedName("filepath")
    private String contentsPath;

    @SerializedName("numlike")
    private int likeCount;

    @SerializedName("date")
    private String ContentCreatedDate;

    @SerializedName("nickname")
    private String writerName;

    @SerializedName("imagepath")
    private String writerImage;
    @SerializedName("fileCode")
  private String fileCode;


    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }


    public String getWriterName() {
        return writerName;
    }

    public String getWriterImage() {
        return writerImage;
    }

    public int getPlayTimeMax() {
        return playTimeMax;
    }


    public void setPlayTimeMax(int playTimeMax) {
        this.playTimeMax = playTimeMax;
    }

    public String getContentCreateDate() {
        return ContentCreatedDate;
    }

    public void setContentCreateDate(String contentCreateDate) {
        ContentCreatedDate = contentCreateDate;
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


    public void setPlayedMode(int playedMode) {
        this.playedMode = playedMode;
    }

    public int getPlayedMode() {
        return playedMode;
    }

    public int getPlayedTIme() {
        return playedTIme;
    }

    public void setPlayedTIme(int playedTIme) {
        this.playedTIme = playedTIme;
    }
}





