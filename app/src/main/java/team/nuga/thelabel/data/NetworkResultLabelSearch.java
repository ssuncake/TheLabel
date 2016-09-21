package team.nuga.thelabel.data;

/**
 * Created by Tacademy on 2016-09-09.
 */
public class NetworkResultLabelSearch  {
    public SearchLabel[] getLabel() {
        return label;
    }

    public void setLabel(SearchLabel[] label) {
        this.label = label;
    }

    SearchLabel[] label;

    ErrorMessage error;

    public boolean isError(){
        if(error==null)
            return false;
        return true;
    }

    public ErrorMessage getError() {
        return error;
    }
}
