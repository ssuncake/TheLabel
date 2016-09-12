package team.nuga.thelabel.fragment;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.ContentsAdatper;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.NewsFeedContents;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.NewsFeedRequest;
import team.nuga.thelabel.request.TestMessageRequest;
import team.nuga.thelabel.viewholder.AccountTypeMusicViewHolder;
import team.nuga.thelabel.viewholder.ParentContentsViewHolder;
import team.nuga.thelabel.youtube.DeveloperKey;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedFragment extends Fragment {

    private static final String LOGTAG = "NewsFeedFragment ";

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
    PlayerState mState;


    ContentsAdatper contentsAdatper;
    MediaPlayer mPlayer;
    Contents[] meetcontentses;
    Contents[] contentses;

    int playingPosition = -1;
    Contents playedContents;
    List<AccountTypeMusicViewHolder> musicHolderList = new ArrayList<>();

    @BindView(R.id.recyclerview_newsfeed)
    RecyclerView contentsRecycerView;
    @BindView(R.id.seekBar_newsfeed_main)
    SeekBar mainProgressView;

    @OnClick(R.id.button_testtest)
    public void testMessage(){
        TestMessageRequest request = new TestMessageRequest(getActivity());
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {

                Log.e("안녕","메세지 성공");
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                Log.e("안녕","메세지 실패"+errorMessage);
            }
        });
    }

    public NewsFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        ButterKnife.bind(this, view);

        contentsAdatper = new ContentsAdatper();
        contentsAdatper.setonYoutubeThumnailClickListener(new ContentsAdatper.OnYoutubeThumnailClickListener() {
            @Override
            public void onYoutubeThumnailClickListener(View view, Contents contents, int position) {
                Toast.makeText(getContext(), "클릭", Toast.LENGTH_SHORT).show();
                Intent intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(), DeveloperKey.DEVELOPER_KEY, contents.getFileCode());
                getActivity().startActivity(intent);
            }

        });
        contentsAdatper.setOnSettingImageClickListener(new ContentsAdatper.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, int position) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.goProfileSetting();
            }
        });

        NewsFeedRequest request = new NewsFeedRequest(getActivity(),1,10,10);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NewsFeedContents>() {
            @Override
            public void onSuccess(NetworkRequest<NewsFeedContents> request, NewsFeedContents result) {

                if(result.isError()){
                    Log.e(LOGTAG,"error : "+result.getError().getMessage());}
                else{
                    meetcontentses = result.getMeetpost();
                    for(Contents c : meetcontentses){
                        Log.d("게시글 ID", "" + c.getContentsID());
                        Log.d("파일경로", "" + c.getContentsPath());
                        Log.d("파일타입", "" + c.getContentsType());
                        Log.d("좋아요 개수", "" + c.getLikeCount());
                        contentsAdatper.add(c);
                    }
                    contentses = result.getPost();
                    for(Contents c : contentses){
                        Log.d("게시글 ID", "" + c.getContentsID());
                        Log.d("파일경로", "" + c.getContentsPath());
                        Log.d("파일타입", "" + c.getContentsType());
                        Log.d("좋아요 개수", "" + c.getLikeCount());
                        contentsAdatper.add(c);
                    }
                }


            }

            @Override
            public void onFail(NetworkRequest<NewsFeedContents> request, int errorCode, String errorMessage, Throwable e) {
                Log.e(LOGTAG,"뉴스피드 네트워크 받아오기 실패 "+errorMessage);
            }
        });


        contentsAdatper.setOnPlayerItemClickListener(new ContentsAdatper.OnPlayerItemClickListener() {
            @Override
            public void onPlayerItemClick(View checkbox, View holderview, Contents contents, int position) {


                for(int i = 0; i< contentsAdatper.getItemCount() ; i++){
                    RecyclerView.ViewHolder rvh = contentsRecycerView.findViewHolderForAdapterPosition(i);
                    ParentContentsViewHolder pvh = (ParentContentsViewHolder) rvh;
                    if (pvh instanceof AccountTypeMusicViewHolder) {
                        AccountTypeMusicViewHolder mvh = (AccountTypeMusicViewHolder) pvh;
                        musicHolderList.add(mvh);
                    }

                }

                if (contents.getPlayedMode() == Contents.PLAY) {
                    Log.w("뮤직플레이어", "playMode : play ->pause / contentid = " + contents.getContentsID() + "/ position =" + position + " /playingPosition = " + playingPosition);
                    pause(contents);

                } else if (contents.getPlayedMode() == Contents.PUASE) {
                    if (position == playingPosition) {
                        Log.w("뮤직플레이어", "playMode : pause -> play / contentid = " + contents.getContentsID() + "/ position =" + position + " /playingPosition = " + playingPosition);
                        mPlayer.seekTo(contents.getPlayedTIme());
                        mainProgressView.setProgress(contents.getPlayedTIme());
                        mPlayer.start();
                        contents.setPlayedMode(Contents.PLAY);
                        mState = PlayerState.STARTED;
                        mHandler.post(progressRunnable);
                    }



                } else if (contents.getPlayedMode() == Contents.STOP) {

                    if (playingPosition == -1) { // 가장 첫실행
                        Log.w("뮤직플레이어", "playMode : stop -> playfrist / contentid = " + contents.getContentsID() + "/ position =" + position + " /playingPosition = " + playingPosition);
                        play(contents, position);
                    } else if (position != playingPosition) { // 실행했던 플레이어의 포지션과 실행하려는 포지션이 틀릴때
                        Log.w("뮤직플레이어", "playMode : stop -> playOther / contentid = " + contents.getContentsID() + "/ position =" + position + " /playingPosition = " + playingPosition);
                        resetPlayer(contents, position);
                        play(contents, position);
                    } else { // 실행했던 플레이어의 포지션과 실행하려는 포지션이 같을때 -
                        // 실행했던 플레이어는 stop으로 돌아가지 않는다.
                    }
                }

                mainProgressView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int progress = -1;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            this.progress = progress;
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        progress = -1;
                        isSeeking = true;
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        if (progress != -1) {
                            if (mState == PlayerState.STARTED) {
                                mPlayer.seekTo(progress);
                            }
                        }
                        isSeeking = false;
                    }
                });
            }

            public String milliSecondsToTimer(long milliseconds) {
                String finalTimerString = "";
                String secondsString = "";

// Convert total duration into time
                int hours = (int) (milliseconds / (1000 * 60 * 60));
                int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
                int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
// Add hours if there
                if (hours > 0) {
                    finalTimerString = hours + ":";
                }

// Prepending 0 to seconds if it is one digit
                if (seconds < 10) {
                    secondsString = "0" + seconds;
                } else {
                    secondsString = "" + seconds;
                }

                finalTimerString = finalTimerString + minutes + ":" + secondsString;

// return timer string
                return finalTimerString;
            }
        });



        contentsRecycerView.setHasFixedSize(true);
        contentsRecycerView.setAdapter(contentsAdatper);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        contentsRecycerView.setLayoutManager(manager);

        return view;

    }


    // 뮤직 플레이어 관련

    boolean isSeeking = false;
    Handler mHandler = new Handler(Looper.getMainLooper());
    Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            if (mState == PlayerState.STARTED) {
                if (!isSeeking) {
                    int position = mPlayer.getCurrentPosition();
                    mainProgressView.setProgress(position);
                    playedContents.setPlayedTIme(position);

                    for(AccountTypeMusicViewHolder hv : musicHolderList)
                    {
                        hv.setSeekbarPlaytime();
                    }

                }
                mHandler.postDelayed(this, 100);
            }

        }
    };


    private void play(Contents contents, int position) {


        mPlayer = MediaPlayer.create(getContext(), Uri.parse(contents.getContentsPath()));
        mainProgressView.setMax(mPlayer.getDuration());

        contents.setPlayTimeMax(mPlayer.getDuration());
        RecyclerView.ViewHolder rvh = contentsRecycerView.findViewHolderForAdapterPosition(position);
        ParentContentsViewHolder pvh = (ParentContentsViewHolder) rvh;
        if (pvh instanceof AccountTypeMusicViewHolder) {
            AccountTypeMusicViewHolder mvh = (AccountTypeMusicViewHolder) pvh;
            mvh.setSeekBarMax();
        }

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
            playedContents = contents;
            playingPosition = position;
            mHandler.post(progressRunnable);
        }

    }

    private void pause(Contents contents) {
        if (mState == PlayerState.STARTED) { //pause는 start일때만 처리 해준다.
            mPlayer.pause();
            mState = PlayerState.PAUSED;
            contents.setPlayedMode(Contents.PUASE);
            contents.setPlayedTIme(mainProgressView.getProgress());
        }
    }

    public void resetPlayer(Contents contents, int position) {

        mPlayer.reset();
        for (int i = 0; i < contentsAdatper.getItemCount(); i++) {
            RecyclerView.ViewHolder holder = contentsRecycerView.findViewHolderForAdapterPosition(i);

            if (holder != null && i != position) {
                ParentContentsViewHolder pvh = (ParentContentsViewHolder) holder;
                Log.d("보이는 홀더 리셋", "position =" + position + " i = " + i);
                if (pvh instanceof AccountTypeMusicViewHolder) {
                    AccountTypeMusicViewHolder avh = (AccountTypeMusicViewHolder) pvh;
                    avh.resetMusic();
                }
            }
        }

        for (Contents c : contentses) {
            if (c.getContentsID() != contents.getContentsID()) {
                Log.d("컨텐츠 리스트 리셋", "리셋하는 contentid ->" + c.getContentsID() + " / 지우면 안될 contentid = " + contents.getContentsID());
                c.setPlayedMode(Contents.STOP);
                c.setPlayedTIme(0);
            }
        }
        mainProgressView.setProgress(0);
    }

    public void mediaStop() {
        if(mPlayer!=null){
            mPlayer.reset();
            mPlayer=null;
        }
        if(mHandler !=null){
            mHandler.removeCallbacks(progressRunnable);
        }
        if(contentsAdatper!=null){
            for(int i = 0; i< contentsAdatper.getItemCount(); i++){
                RecyclerView.ViewHolder rvh= contentsRecycerView.findViewHolderForAdapterPosition(i);
                ParentContentsViewHolder pvh = (ParentContentsViewHolder)rvh;
                if(pvh instanceof AccountTypeMusicViewHolder){
                    AccountTypeMusicViewHolder mvh = (AccountTypeMusicViewHolder)pvh;
                    mvh.resetMusic();
                }
            }

        }
        if(mainProgressView!=null){
            mainProgressView.setProgress(0);
        }



    }


    }


