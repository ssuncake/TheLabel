package team.nuga.thelabel.data;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class NetworkResult<T> {
    private String message;
    private T user;
    private T data;
    private T error;
    private T label;



    public boolean isError(){
        if(error==null)
            return true;
        else
            return false;
    }
    public String getMessage(){ return this.message;}
    public T getUser() {
        return this.user;
    }

    public T getData() {
        return this.data;
    }

    public T getLabel() {
        return label;
    }
}
