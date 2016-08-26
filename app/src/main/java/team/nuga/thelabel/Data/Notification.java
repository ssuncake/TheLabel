package team.nuga.thelabel.data;

import java.util.Date;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class Notification {

    private static final int NOTIFICATION_LIKE = 1;
    private static final int NOTIFICATION_INVITE = 2;

    private int notificationID;
    private int notificationType;
    private String notificationText;
    private Date notificationDate;

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
}
