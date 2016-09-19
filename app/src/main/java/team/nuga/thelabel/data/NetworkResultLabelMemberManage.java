package team.nuga.thelabel.data;

/**
 * Created by Tacademy on 2016-09-19.
 */
public class NetworkResultLabelMemberManage {
    User[] users;
    ErrorMessage error;


    public boolean isError(){
        if(error==null)
            return false;
        return true;
    }

    public User[] getUsers() {
        return users;
    }
}
