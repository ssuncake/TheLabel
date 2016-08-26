package team.nuga.thelabel.data;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class InviteNotification extends Notification implements Serializable {

    private int inviteMode;
    private Label sendLabel;
    private User joinUser;
    public static final int INVITELABEL = 100;
    public static final int JOINRQUEST = 200;

    public int getInviteMode() {
        return inviteMode;
    }

    public void setInviteModeInviteLabel() {
        this.inviteMode = INVITELABEL;
    }
    public void setInviteModeJoinRequest() {
        this.inviteMode = JOINRQUEST;
    }

    public Label getSendLabel() {
        return sendLabel;
    }

    public void setSendLabel(Label sendLabel) {
        this.sendLabel = sendLabel;
    }

    public User getJoinUser() {
        return joinUser;
    }

    public void setJoinUser(User joinUser) {
        this.joinUser = joinUser;
    }
}
