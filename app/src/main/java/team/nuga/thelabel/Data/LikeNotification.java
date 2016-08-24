package team.nuga.thelabel.Data;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class LikeNotification {
    private int NotificationLikeUser;

    public int getNotificationLikeUser() {
        return NotificationLikeUser;
    }

    public void setNotificationLikeUser(int notificationLikeUser) {
        NotificationLikeUser = notificationLikeUser;
    }

    public LikeNotification(int notificationLikeUser) {

        NotificationLikeUser = notificationLikeUser;
    }
}
