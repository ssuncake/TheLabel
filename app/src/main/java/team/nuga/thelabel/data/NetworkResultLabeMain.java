package team.nuga.thelabel.data;

/**
 * Created by 우리집 on 2016-09-03.
 */
public class NetworkResultLabeMain {
    int page;
    String count;
    Label label;
    Member[] member;
    Contents[] data;
    ErrorMessage error;

    public boolean isError(){
        if(error == null)
            return false;
        return true;
    }

    public int getPage() {
        return page;
    }

    public String getCount() {
        return count;
    }

    public Label getResult() {
        return label;
    }

    public Member[] getMember() {
        return member;
    }

    public Contents[] getData() {
        return data;
    }

    public ErrorMessage getError() {
        return error;
    }
}
