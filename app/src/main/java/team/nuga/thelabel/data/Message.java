package team.nuga.thelabel.data;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Message {
    public static final int ME=100;
    public static final int OTHER = 200;
    public static final int DATE = 300;

    private int MessageType;
    private String message;
    private int id;
    private String date;

    public int getMessageType() {
        return MessageType;
    }

    public void setMessageType(int messageType) {
        MessageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
