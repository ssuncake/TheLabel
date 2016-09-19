package team.nuga.thelabel.data;

/**
 * Created by Tacademy on 2016-09-19.
 */
public class NetworkResultMessageList {
    private ErrorMessage error;
    public boolean isError(){
        if(error==null)
            return false;
        else
            return true;
    }
    public ErrorMessage getError() {
        return error;
    }


    Message[] message;

    public Message[] getMessage() {
        return message;
    }
}
