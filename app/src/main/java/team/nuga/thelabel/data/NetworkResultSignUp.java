package team.nuga.thelabel.data;

/**
 * Created by Blissun on 2016-09-05.
 */
public class NetworkResultSignUp {
    String message;
    int id;
    ErrorMessage error;

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public ErrorMessage getError() {
        return error;
    }
}
