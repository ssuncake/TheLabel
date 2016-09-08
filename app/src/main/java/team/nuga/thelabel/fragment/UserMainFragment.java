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


/**
 * A simple {@link Fragment} subclass.
 */
public class UserMainFragment extends Fragment {
    ContentsAdatper accountAdatper;
    User user;
    MediaPlayer mPlayer;

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

    @BindView(R.id.textView_UserMain_UsrName)
    TextView userName;

    public UserMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_main, container, false);
        ButterKnife.bind(this,view);

        ContentsRequest contentsRequest = new ContentsRequest(getContext(),2,10);
        NetworkManager.getInstance().getNetworkData(contentsRequest, new NetworkManager.OnResultListener<NetworkResultMyAccount>() {
            @Override

            public void onSuccess(NetworkRequest<NetworkResultMyAccount> request,NetworkResultMyAccount result) {
                Contents[] contentses = result.getData();
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
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_user_main);
        accountAdatper = new ContentsAdatper();
        accountAdatper.setOnSettingImageClickListener(new ContentsAdatper.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, int position) {
                MainActivity mainActivity  = (MainActivity)getActivity();
                mainActivity.goProfileSetting();
            }
        });

        mPlayer = MediaPlayer.create(getContext(), Uri.parse("http://ec2-52-78-137-47.ap-northeast-2.compute.amazonaws.com/avs/whiparam.mp3"));
        mState = PlayerState.PREPARED;
        accountAdatper.setOnPlayerItemClickListener(new ContentsAdatper.OnPlayerItemClickListener() {
            @Override
            public void onPlayerItemClick(View view, View parent, Contents contents, int position, boolean isChecked) {
                progressView = (SeekBar) parent.findViewById(R.id.seekBar_Play);
                audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
                progressView.setMax(mPlayer.getDuration());

                if (isChecked==true) {
                    Log.e("체크확인", "chekced");
                    play();

                } else {
                    Log.e("노체크", "no checked");
                    pause();

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


        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(accountAdatper);
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
                }
                mHandler.postDelayed(this, 100);
            }
        }
    };

    private void play() {
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

            mHandler.post(progressRunnable);
        }

    }

    private void pause() {
        if (mState == PlayerState.STARTED) { //pause는 start일때만 처리 해준다.
            mPlayer.pause();
            mState = PlayerState.PAUSED;
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.release();
        mState = PlayerState.RELEASED;
        mPlayer = null;
    }
}
