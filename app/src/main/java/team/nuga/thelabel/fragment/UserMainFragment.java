package team.nuga.thelabel.fragment;


import android.content.Context;
import android.media.AudioManager;
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
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.ContentsAdatper;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.NetworkResultMyAccount;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.ContentsRequest;
import team.nuga.thelabel.viewholder.AccountTypeMusicViewHolder;
import team.nuga.thelabel.viewholder.ParentContentsViewHolder;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserMainFragment extends Fragment {
    ContentsAdatper accountAdatper;
    User user;
    static MediaPlayer mPlayer;

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

    AudioManager audioManager;
    PlayerState mState;
    SeekBar progressView;
    TextView currentTimeView, totalTimeView;
    RecyclerView recyclerView;

    @BindView(R.id.textView_UserMain_UsrName)
    TextView userName;

    Contents[] contentses;


    public UserMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_main, container, false);
        ButterKnife.bind(this,view);

        mPlayer = new MediaPlayer();
        ContentsRequest contentsRequest = new ContentsRequest(getContext(),2,10);
        NetworkManager.getInstance().getNetworkData(contentsRequest, new NetworkManager.OnResultListener<NetworkResultMyAccount>() {
            @Override

            public void onSuccess(NetworkRequest<NetworkResultMyAccount> request,NetworkResultMyAccount result) {
                contentses = result.getData();
//                accountAdatper.setSize(contentses.length+1);
                User user = result.getResult();
                Log.e("유저메인",user.getUserName());
                accountAdatper.setUser(user);
                for(Contents c : contentses){
                    Log.e("게시글 ID",""+ c.getContentsID());
                    Log.e("파일경로", ""+c.getContentsPath());
                    Log.e("파일타입", ""+c.getContentsType());
                    Log.e("좋아요 개수",""+c.getLikeCount());
                   accountAdatper.add(c);
                }

            }

            @Override
            public void onFail(NetworkRequest<NetworkResultMyAccount> request, int errorCode, String errorMessage, Throwable e) {
                Log.e("유저메인 실패",errorMessage);
            }
        });
//        user = (User)getArguments().getSerializable(MainActivity.MAINUSER);
//        userName.setText(user.getUserName()+" 의 계정입니다.");
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_user_main);
        accountAdatper = new ContentsAdatper();
        accountAdatper.setOnSettingImageClickListener(new ContentsAdatper.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, int position) {
                MainActivity mainActivity  = (MainActivity)getActivity();
                mainActivity.goProfileSetting();
            }
        });


//        long totalDuration = mPlayer.getDuration();
//        long currentDuration = mPlayer.getCurrentPosition();
        accountAdatper.setOnPlayerItemClickListener(new ContentsAdatper.OnPlayerItemClickListener() {
            @Override
            public void onPlayerItemClick(View checkbox, View holderview, Contents contents, int position, boolean isChecked) {
                progressView = (SeekBar) holderview.findViewById(R.id.seekBar_Play);
                currentTimeView = (TextView)holderview.findViewById(R.id.textView_currentTime);
                totalTimeView = (TextView)holderview.findViewById(R.id.textView_totalTime);

                audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


//                if (isChecked==true) {
//                    if(contents.getPlayedMode()==Contents.PLAY){
//                        Log.e("뮤직플레이어","isChecked true -> play");
//                    }else if(contents.getPlayedMode() == Contents.PUASE){
//                        Log.e("뮤직플레이어","isChecked true -> puase");
//                        mPlayer.seekTo(contents.getPlayedTIme());
//                    }else if(contents.getPlayedMode() == Contents.STOP){
//                        Log.e("뮤직플레이어","isChecked true -> stop");
//                        resetPlayer(contents, contents.getContentsID(),position);
//                        play();
//                    }
//                } else {
//                    Log.e("뮤직플레이어","isChecked false");
//                    pause(contents);
//
//                }

                    if(contents.getPlayedMode()==Contents.PLAY){
                        Log.w("뮤직플레이어","playMode -> play / contentid = "+contents.getContentsID()+"/ position ="+position);
                        pause(contents);
                    }else if(contents.getPlayedMode() == Contents.PUASE){
                        Log.w("뮤직플레이어","playMode -> PUASE / contentid = "+contents.getContentsID()+"/ position ="+position);

                        mPlayer.seekTo(contents.getPlayedTIme());
                        progressView.setProgress(contents.getPlayedTIme());
                        mPlayer.start();
                        contents.setPlayedMode(Contents.PLAY);
                        mState = PlayerState.STARTED;

                        mHandler.post(progressRunnable);
                    }else if(contents.getPlayedMode() == Contents.STOP){
                        Log.w("뮤직플레이어","playMode -> STOP / contentid = "+contents.getContentsID()+"/ position ="+position);
                        resetPlayer(contents, contents.getContentsID(),position);
                        play(contents);
                    }

                progressView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                }   else {
                    secondsString = "" + seconds;
                }

                finalTimerString = finalTimerString + minutes + ":" + secondsString;

// return timer string
                return finalTimerString;
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(accountAdatper);
//        initData();
        return view;

    }



    boolean isSeeking = false;
    Handler mHandler = new Handler(Looper.getMainLooper());
    Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            if (mState == PlayerState.STARTED) {
                if (!isSeeking) {
                    int position = mPlayer.getCurrentPosition();
                    progressView.setProgress(position);
//                    currentTimeView.setText(position);
                }
                mHandler.postDelayed(this, 100);
            }

        }
    };

    private void play(Contents contents) {
            if (mState == PlayerState.INITIALIED || mState == PlayerState.STOPPED) { //INITIALIED상태나 STOPPED상태이면 prepare 상태가 아니므로 prpare을 호출하여 prepare상태로 만든다.
                try {
                    mPlayer.prepare();
                    mState = PlayerState.PREPARED;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (mState == PlayerState.PREPARED || mState == PlayerState.PAUSED) {
                mPlayer.seekTo(progressView.getProgress());
                mPlayer.start();
                mState = PlayerState.STARTED;
                contents.setPlayedMode(Contents.PLAY);

                mHandler.post(progressRunnable);

            }
            if(mPlayer.isPlaying()){
                currentTimeView.setText("current time");
            }
    }

    private void pause(Contents contents) {
            if (mState == PlayerState.STARTED) { //pause는 start일때만 처리 해준다.
                mPlayer.pause();
                mState = PlayerState.PAUSED;
                contents.setPlayedMode(Contents.PUASE);
                contents.setPlayedTIme(progressView.getProgress());
            }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.release();
        mState = PlayerState.RELEASED;
        mPlayer = null;
    }


    public void resetPlayer(Contents contents,int ContentsId, int position ){
        if(mPlayer!=null){
            mPlayer.reset();
        }
        for(int i =0 ;i<accountAdatper.getItemCount();i++){
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);

            if(holder!=null && i!=position){
                ParentContentsViewHolder pvh = (ParentContentsViewHolder)holder;
                if(pvh instanceof AccountTypeMusicViewHolder){
                    AccountTypeMusicViewHolder avh = (AccountTypeMusicViewHolder)pvh;
                    Log.e("보이는 홀더 리셋","position ="+position+" i = "+i);
                    avh.resetMusic();
                }
            }
        }

        for(Contents c : contentses){
            if(c.getContentsID() != ContentsId){
                Log.e("컨텐츠 리스트 리셋","리셋하는 contentid ->"+c.getContentsID()+" / 지우면 안될 contentid = "+ContentsId);
                c.setPlayedMode(Contents.STOP);
                c.setPlayedTIme(0);
            }
        }

//        musicPlayerNumeber = ContentsId;
//        accountAdatper.setPlayedPosition(position);
        mPlayer=MediaPlayer.create(getContext(), Uri.parse(contents.getContentsPath()));
        progressView.setMax(mPlayer.getDuration());
        mState = PlayerState.PREPARED;
    }
}
