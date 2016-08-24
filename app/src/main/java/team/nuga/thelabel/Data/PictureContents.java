package team.nuga.thelabel.Data;

import android.graphics.Bitmap;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class PictureContents {
    public Bitmap getContentsPicture() {
        return contentsPicture;
    }

    public void setContentsPicture(Bitmap contentsPicture) {
        this.contentsPicture = contentsPicture;
    }

    public PictureContents(Bitmap contentsPicture) {

        this.contentsPicture = contentsPicture;
    }

    private Bitmap contentsPicture;
}
