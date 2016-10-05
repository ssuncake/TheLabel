package team.nuga.thelabel;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.List;

import team.nuga.thelabel.data.Contents;

/**
 * Created by 우리집 on 2016-09-20.
 */
public class ContentsMusicPlayer {

    public ContentsMusicPlayer(Context context, List<Contents> contentses, SeekBar mainProgressView) {
        this.context = context;
        this.contentses = contentses;
        this.mainProgressView = mainProgressView;
    }

    public static final String LOG_MUSIC_PLAYER = "ContentsMusicPlayer";

    enum PlayerState {
        IDLE,
        INITIALIZED,
        PREPARED,
        STARTED,
        PAUSED,
        STOPPED,
        ERROR,
        RELEASED
    }

    Context context;
    private PlayerState mState;
    private MediaPlayer mPlayer;
    private List<Contents> contentses;
    private SeekBar mainProgressView;
    private Contents playNowContents;


    public void setMusicProgress(int progress) {
        mPlayer.seekTo(progress);
        playNowContents.setPlayedTIme(progress);
    }


    public void playToPause(Contents contents) {
        Log.d(LOG_MUSIC_PLAYER, "playToPause =>" + contents.getContentsID() + " / " + playNowContents.getContentsID());
        mPlayer.pause();
        mState = PlayerState.PAUSED;
        contents.setPlayedMode(Contents.PUASE);
        contents.setPlayedTIme(mainProgressView.getProgress());
    }

    public void pauseToPlay(Contents contents) {
        Log.d(LOG_MUSIC_PLAYER, "pauseToPlay =>" + contents.getContentsID() + " / " + playNowContents.getContentsID());

        mPlayer.seekTo(contents.getPlayedTIme());
        mainProgressView.setProgress(contents.getPlayedTIme());
        mPlayer.start();
        contents.setPlayedMode(Contents.PLAY);
        mState = PlayerState.STARTED;
        mHandler.post(progressRunnable);
    }

    public void stopToPlay(Contents contents) {
        if (mPlayer != null) {
            Log.d(LOG_MUSIC_PLAYER, "stopToPlay =>" + contents.getContentsID() + " / " + playNowContents.getContentsID());
            mPlayer.reset();
            //다른 플레이어들을 리셋
            for (Contents c : contentses) {
                if (c.getContentsID() != contents.getContentsID()&&c.getContentsType() == Contents.MUSIC) {
                        c.setPlayedMode(Contents.STOP);
                        c.setPlayedTIme(0);

                }
            }
            mainProgressView.setProgress(0);
        }


        // 새로시작
        mPlayer = MediaPlayer.create(context, Uri.parse(contents.getContentsPath()));
        mainProgressView.setMax(mPlayer.getDuration());
        contents.setPlayTimeMax(mPlayer.getDuration());


        mState = PlayerState.PREPARED;

        if (mState == PlayerState.INITIALIZED || mState == PlayerState.STOPPED) { //INITIALIED상태나 STOPPED상태이면 prepare 상태가 아니므로 prpare을 호출하여 prepare상태로 만든다.
            try {
                mPlayer.prepare();
                mState = PlayerState.PREPARED;
            } catch (IOException e) {

            }
        }

        if (mState == PlayerState.PREPARED || mState == PlayerState.PAUSED) {
            mPlayer.seekTo(contents.getPlayedTIme());
            mPlayer.start();
            mState = PlayerState.STARTED;
            contents.setPlayedMode(Contents.PLAY);
            playNowContents = contents;
            mHandler.post(progressRunnable);
        }
    }

    public void setSeeking(boolean seeking) {
        isSeeking = seeking;
    }

    boolean isSeeking = false;
    Handler mHandler = new Handler(Looper.getMainLooper());
    Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {

            if (mState == PlayerState.STARTED) {
                if (!isSeeking) {
                    int position = mPlayer.getCurrentPosition();
                    mainProgressView.setProgress(position);
                    playNowContents.setPlayedTIme(position);
                }
                if (playNowContents.getPlayedTIme() >= playNowContents.getPlayTimeMax() - 200 || mainProgressView.getProgress() >= mainProgressView.getMax() - 200) {
                    allReset();
                }
                mHandler.postDelayed(this, 100);
            }

        }
    };


    public void setRefreshContentses(List<Contents> contentses) {
        this.contentses = contentses;
    }

    public void allReset() {
        if (mPlayer != null) {
            mPlayer.reset();
            mState = PlayerState.IDLE;
            mPlayer = null;
        }
        if (mHandler != null) {
            mHandler.removeCallbacks(progressRunnable);
        }
        if (mainProgressView != null) {
            mainProgressView.setProgress(0);
        }
        for (Contents c : contentses) {
            if (c.getContentsType() == Contents.MUSIC) {
                c.setPlayedMode(Contents.STOP);
                c.setPlayedTIme(0);
            }
        }

        Log.e(LOG_MUSIC_PLAYER, "올 리셋");
    }

    public int getPlayedContentsId() {
        if (playNowContents == null) {
            return -1;
        } else {
            return playNowContents.getContentsID();
        }

    }
}
