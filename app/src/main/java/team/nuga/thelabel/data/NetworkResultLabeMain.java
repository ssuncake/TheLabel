package team.nuga.thelabel.data;

/**
 * Created by 우리집 on 2016-09-03.
 */
public class NetworkResultLabeMain {
    int page;
    String count;
    Label result;
    User[] member;
    Contents[] data;

    public int getPage() {
        return page;
    }

    public String getCount() {
        return count;
    }

    public Label getResult() {
        return result;
    }

    public User[] getMember() {
        return member;
    }

    public Contents[] getData() {
        return data;
    }
}
