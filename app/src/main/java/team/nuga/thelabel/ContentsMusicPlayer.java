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

    public static final String LOGTAG = "ContentsMusicPlayer";

    enum PlayerState {
        IDLE,
        INITIALIED,
        PREPARED,
        STARTED,
        PAUSED,
        STOPPED,
        ERROR,
        RELEASED
    }

    Context context;

    PlayerState mState;

    MediaPlayer mPlayer;

    List<Contents> contentses;

    SeekBar mainProgressView;

    Contents playNowContents;

    int userPlayingContentsId;


    public void playToPause(Contents contents){
        Log.e(LOGTAG,"playToPause =>"+contents.getContentsID()+" / "+playNowContents.getContentsID());
        mPlayer.pause();
        mState = PlayerState.PAUSED;
        contents.setPlayedMode(Contents.PUASE);
        contents.setPlayedTIme(mainProgressView.getProgress());
    }

    public void pauseToPlay(Contents contents){
        Log.e(LOGTAG,"pauseToPlay =>"+contents.getContentsID()+" / "+playNowContents.getContentsID());
//        if(contents.getContentsID() == userPlayingContentsId) // 사용자가 중지한 컨텐츠가 맞다면
//        {
            mPlayer.seekTo(contents.getPlayedTIme());
            mainProgressView.setProgress(contents.getPlayedTIme());
            mPlayer.start();
            contents.setPlayedMode(Contents.PLAY);
            mState = PlayerState.STARTED;
            mHandler.post(progressRunnable);
//        }
    }

    public void stopToPlay(Contents contents){

        if(mPlayer != null){
            Log.e(LOGTAG,"stopToPlay =>"+contents.getContentsID()+" / "+playNowContents.getContentsID());
            mPlayer.reset();
            //다른 플레이어들을 리셋
            for (Contents c : contentses) {
                if (c.getContentsID() != contents.getContentsID()) {
                    if(c.getContentsType() == Contents.MUSIC){
                        Log.e("컨텐츠 리스트 리셋", "리셋하는 contentid ->" + c.getContentsID() + " / 지우면 안될 contentid = " + contents.getContentsID());
                        c.setPlayedMode(Contents.STOP);
                        c.setPlayedTIme(0);
                    }

                }
            }
            mainProgressView.setProgress(0);
        }else{
            Log.e(LOGTAG,"stopToPlay =>"+contents.getContentsID()+"첫시작");
        }




        // 새로시작
        mPlayer = MediaPlayer.create(context, Uri.parse(contents.getContentsPath()));
        mainProgressView.setMax(mPlayer.getDuration());
        contents.setPlayTimeMax(mPlayer.getDuration());


        mState = PlayerState.PREPARED;

        if (mState == PlayerState.INITIALIED || mState == PlayerState.STOPPED) { //INITIALIED상태나 STOPPED상태이면 prepare 상태가 아니므로 prpare을 호출하여 prepare상태로 만든다.
            try {
                mPlayer.prepare();
                mState = PlayerState.PREPARED;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mState == PlayerState.PREPARED || mState == PlayerState.PAUSED) {
            mPlayer.seekTo(mainProgressView.getProgress());
            mPlayer.start();
            mState = PlayerState.STARTED;
            contents.setPlayedMode(Contents.PLAY);
            playNowContents = contents;
            mHandler.post(progressRunnable);
        }
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
                mHandler.postDelayed(this, 100);
            }

        }
    };


    public void setRefreshContentses(List<Contents> contentses){
        this.contentses = contentses;
    }
}
