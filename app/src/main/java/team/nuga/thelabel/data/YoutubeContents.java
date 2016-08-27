package team.nuga.thelabel.data;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class YoutubeContents implements Serializable {
    private String contentsYoutube;

    public String getContentsYoutube() {
        return contentsYoutube;
    }

    public void setContentsYoutube(String contentsYoutube) {
        this.contentsYoutube = contentsYoutube;
    }

    public YoutubeContents(String contentsYoutube) {

        this.contentsYoutube = contentsYoutube;
    }

    //
}
