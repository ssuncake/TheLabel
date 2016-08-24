package team.nuga.thelabel.Data;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Message {
    private int messageId;
    private int messageReceiverID;
    private int messageSenderID;
    private String messageText;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getMessageReceiverID() {
        return messageReceiverID;
    }

    public void setMessageReceiverID(int messageReceiverID) {
        this.messageReceiverID = messageReceiverID;
    }

    public int getMessageSenderID() {
        return messageSenderID;
    }

    public void setMessageSenderID(int messageSenderID) {
        this.messageSenderID = messageSenderID;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Message(int messageId, int messageReceiverID, int messageSenderID, String messageText) {

        this.messageId = messageId;
        this.messageReceiverID = messageReceiverID;
        this.messageSenderID = messageSenderID;
        this.messageText = messageText;
    }
}
