package team.nuga.thelabel.data;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class NetworkResult<T> {
    private String message;
    private T user;


    public String getMessage(){ return this.message;}
    public T getUser() {
        return this.user;
    }

}
