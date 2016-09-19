package team.nuga.thelabel.data;

/**
 * Created by Tacademy on 2016-09-19.
 */
public class NetworkResultOtherUser {
    User user;
    Contents[] post;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contents[] getPost() {
        return post;
    }

    public void setPost(Contents[] post) {
        this.post = post;
    }
}
