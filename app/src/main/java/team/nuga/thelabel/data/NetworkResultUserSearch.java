package team.nuga.thelabel.data;

/**
 * Created by kuhyebin on 2016-09-11.
 */
public class NetworkResultUserSearch {
    int page;
    int count;
    User[] result;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public User[] getResult() {
        return result;
    }

    public void setResult(User[] result) {
        this.result = result;
    }
}
