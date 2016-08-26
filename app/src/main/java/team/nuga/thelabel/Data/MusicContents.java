package team.nuga.thelabel.data;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class MusicContents implements Serializable {
    private URL contentsMusic;

    public URL getContentsMusic() {
        return contentsMusic;
    }

    public void setContentsMusic(URL contentsMusic) {
        this.contentsMusic = contentsMusic;
    }

    public MusicContents(URL contentsMusic) {

        this.contentsMusic = contentsMusic;
    }
}
