package team.nuga.thelabel.data;

/**
 * Created by Tacademy on 2016-09-19.
 */
public class NetworkResultOtherUser {
    public SearchUser getUser() {
        return user;
    }

    public void setUser(SearchUser user) {
        this.user = user;
    }

    SearchUser user;
    Contents[] post;


    public Contents[] getPost() {
        return post;
    }

    public void setPost(Contents[] post) {
        this.post = post;
    }
}
