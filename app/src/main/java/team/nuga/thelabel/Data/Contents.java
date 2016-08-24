package team.nuga.thelabel.Data;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Contents {
    private int contentsID;
    private int contentsType;
    private int contentsWriterID;
    private int contentsBoardID;
    private int contentsLike;
    private int contentsPrivacy;
    private String contentsText;
    private Date contentsDate;

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

    public Contents(int contentsID, int contentsType, int contentsWriterID, int contentsBoardID, int contentsLike, int contentsPrivacy, String contentsText, HashSet<Integer> contentsLikeUsers) {

        this.contentsID = contentsID;
        this.contentsType = contentsType;
        this.contentsWriterID = contentsWriterID;
        this.contentsBoardID = contentsBoardID;
        this.contentsLike = contentsLike;
        this.contentsPrivacy = contentsPrivacy;
        this.contentsText = contentsText;
        this.contentsLikeUsers = contentsLikeUsers;
    }

    private HashSet<Integer> contentsLikeUsers;

}
