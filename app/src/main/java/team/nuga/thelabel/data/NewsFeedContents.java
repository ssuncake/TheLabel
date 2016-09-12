package team.nuga.thelabel.data;

/**
 * Created by Tacademy on 2016-09-12.
 */
public class NewsFeedContents {
    int page;
    int count;
    int meet;
    Contents[] meetdata;
    Contents[] data;

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    public int getMeet() {
        return meet;
    }

    public Contents[] getMeetdata() {
        return meetdata;
    }

    public Contents[] getData() {
        return data;
    }
}
