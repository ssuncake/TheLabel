package team.nuga.thelabel.Data;

import java.util.Date;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Notification {
    private int notificationID;
    private int notificationType;
    private String notificationText;

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public Notification(int notificationID, int notificationType, String notificationText, Date notificationDate) {

        this.notificationID = notificationID;
        this.notificationType = notificationType;
        this.notificationText = notificationText;
        this.notificationDate = notificationDate;
    }

    private Date notificationDate;
}
