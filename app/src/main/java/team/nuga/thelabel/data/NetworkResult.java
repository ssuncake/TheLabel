package team.nuga.thelabel.data;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class NetworkResult<T> {
    private String message;
    private int id;
    ErrorMessage errorMessage;
    int resultCode;
    private T user;
    private T data;
    private ErrorMessage error;
    private T label;

    public int getResultCode() {
        return resultCode;
    }


    public int getId() {
        return id;
    }





    public boolean isError(){
        if(error==null)
            return false;
        else
            return true;
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

    public ErrorMessage getError() {
        return error;
    }


}
