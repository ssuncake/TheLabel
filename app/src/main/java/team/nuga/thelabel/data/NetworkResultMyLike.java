package team.nuga.thelabel.data;

/**
 * Created by Tacademy on 2016-09-13.
 */
public class NetworkResultMyLike {
    int page;
    int count;
    Contents[] post;
    ErrorMessage error;

    public boolean isError(){
        if(error==null)
            return false;
        return true;
    }

    public ErrorMessage getError(){
        return error;
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    public Contents[] getPost() {
        return post;
    }
}
