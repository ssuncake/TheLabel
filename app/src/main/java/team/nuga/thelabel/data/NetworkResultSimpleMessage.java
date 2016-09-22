package team.nuga.thelabel.data;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class NetworkResultSimpleMessage {
    private String message;

    private ErrorMessage error;

    public boolean isError(){
        if(error==null)
            return false;
        else
            return true;
    }
    public String getMessage(){ return this.message;}

    public ErrorMessage getError() {
        return error;
    }


}
