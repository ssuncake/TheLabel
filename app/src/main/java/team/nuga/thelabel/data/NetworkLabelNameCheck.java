package team.nuga.thelabel.data;

/**
 * Created by 우리집 on 2016-09-17.
 */
public class NetworkLabelNameCheck {
    ErrorMessage error;
    int match;

    public boolean isError(){
        if(error==null)
            return false;
        return true;
    }

    public ErrorMessage getError() {
        return error;
    }

    public int getMatch() {
        return match;
    }
}
