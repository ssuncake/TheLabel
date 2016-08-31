package team.nuga.thelabel.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Contents implements Serializable{

    public static final int MUSIC = 0;
    public static final int PICTURE = 1;
    public static final int YOUTUBE = 2;
    protected int contentsType;


    private int contentsID;

    private int contentsWriterID;
    private int contentsBoardID;
    private int contentsLike;
    private int contentsPrivacy;
    private String contentsText;
    private Date contentsDate;
    private HashSet<Integer> contentsLikeUsers;
    private String contentsTitle;

    public String getContentsTitle() {
        return contentsTitle;
    }

    public void setContentsTitle(String contentsTitle) {
        this.contentsTitle = contentsTitle;
    }

    public Date getContentsDate() {
        return contentsDate;
    }

    public void setContentsDate(Date contentsDate) {
        this.contentsDate = contentsDate;
    }

    public int getContentsID() {
        return contentsID;
    }

    public void setContentsID(int contentsID) {
        this.contentsID = contentsID;
    }

    public int getContentsType() {
        return contentsType;
    }

    public void setContentsType(int contentsType) {
        this.contentsType = contentsType;
    }

    public int getContentsWriterID() {
        return contentsWriterID;
    }

    public void setContentsWriterID(int contentsWriterID) {
        this.contentsWriterID = contentsWriterID;
    }

    public int getContentsBoardID() {
        return contentsBoardID;
    }

    public void setContentsBoardID(int contentsBoardID) {
        this.contentsBoardID = contentsBoardID;
    }

    public int getContentsLike() {
        return contentsLike;
    }

    public void setContentsLike(int contentsLike) {
        this.contentsLike = contentsLike;
    }

    public int getContentsPrivacy() {
        return contentsPrivacy;
    }

    public void setContentsPrivacy(int contentsPrivacy) {
        this.contentsPrivacy = contentsPrivacy;
    }

    public String getContentsText() {
        return contentsText;
    }

    public void setContentsText(String contentsText) {
        this.contentsText = contentsText;
    }

    public HashSet<Integer> getContentsLikeUsers() {
        return contentsLikeUsers;
    }

    public void setContentsLikeUsers(HashSet<Integer> contentsLikeUsers) {
        this.contentsLikeUsers = contentsLikeUsers;
    }





}
