package team.nuga.thelabel.data;

/**
 * Created by Tacademy on 2016-09-12.
 */
public class NewsFeedContents {
    int page;
    int count;
    int meet;
    Contents[] meetpost;
    Contents[] post;
    ErrorMessage error;

    public Contents[] getMeetpost() {
        return meetpost;
    }

    public Contents[] getPost() {
        return post;
    }

    public boolean isError(){
        if(error == null)
            return false;
        return true;
    }

    public ErrorMessage getError() {
        return error;
    }
}
