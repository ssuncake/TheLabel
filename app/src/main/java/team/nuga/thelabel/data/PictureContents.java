package team.nuga.thelabel.data;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class PictureContents extends Contents implements Serializable {

    public PictureContents() {
        super();
        super.contentsType = Contents.PICTURE;
    }
}
