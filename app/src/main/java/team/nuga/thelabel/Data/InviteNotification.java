package team.nuga.thelabel.data;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class InviteNotification extends Notification {
    private int NotificationInviteLabel;

    public int getNotificationInviteLabel() {
        return NotificationInviteLabel;
    }

    public void setNotificationInviteLabel(int notificationInviteLabel) {
        NotificationInviteLabel = notificationInviteLabel;
    }

    public InviteNotification(int notificationInviteLabel) {

        NotificationInviteLabel = notificationInviteLabel;
    }
}
