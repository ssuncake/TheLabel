package team.nuga.thelabel.fragment;


import android.content.Intent;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.ContentsMusicPlayer;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.ContentsAdatper;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.NetworkResultMyAccount;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.ContentsRequest;
import team.nuga.thelabel.youtube.DeveloperKey;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserMainFragment extends Fragment {

    public static final String LOGTAG = "UserMainFragemnt ";

    ContentsAdatper contentsAdatper;

    User user;


    boolean isLastItem;
    private static int PAGE; //페이지
    private static String COUNT="10"; //카운트 수
    ContentsMusicPlayer musicPlayer;
    @BindView(R.id.recyclerview_user_main)
    RecyclerView contentsRecycerView;

    SeekBar mainProgressView ;

    Contents[] contentses;





    public UserMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_main, container, false);
        ButterKnife.bind(this, view);
        PAGE =1;
        mainProgressView = new SeekBar(getActivity());
        contentsAdatper = new ContentsAdatper();
        musicPlayer = new ContentsMusicPlayer(getActivity(),contentsAdatper.getMcontentslist(),mainProgressView);

        user = (User)getArguments().getSerializable(MainActivity.MAINUSER);
        contentsAdatper.setUser(user);



        contentsAdatper.setOnSettingImageClickListener(new ContentsAdatper.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, int position) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.goProfileSetting();
            }
        });

        // 유튜브 설정부분
        contentsAdatper.setonYoutubeThumnailClickListener(new ContentsAdatper.OnYoutubeThumnailClickListener() {
            @Override
            public void onYoutubeThumnailClickListener(View view, Contents contents, int position) {
                Toast.makeText(getContext(), "클릭", Toast.LENGTH_SHORT).show();
                Intent intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(), DeveloperKey.DEVELOPER_KEY, contents.getFileCode());
                getActivity().startActivity(intent);
                musicPlayer.allReset();
            }

        });


        //뮤직 플레이어 설정부분


        contentsAdatper.setOnPlayerItemClickListener(new ContentsAdatper.OnPlayerItemClickListener() {
            @Override
            public void onPlayerItemClick(View checkbox, View holderview, Contents contents, int position) {
                if (contents.getPlayedMode() == Contents.PLAY) {
                    musicPlayer.playToPause(contents);
                } else if (contents.getPlayedMode() == Contents.PUASE) {
                    musicPlayer.pauseToPlay(contents);
                } else if (contents.getPlayedMode() == Contents.STOP) {
                    musicPlayer.stopToPlay(contents);
                }
            }

        });


        contentsAdatper.setOnProgressBarChangeListener(new ContentsAdatper.onProgressBarChangeListener() {
            @Override
            public void progressBarChange(Contents contents, int progress, int position) {
              if(contents.getContentsID() == musicPlayer.getPlayedContentsId()) {
                  mainProgressView.setProgress(progress);
                  musicPlayer.setMusicProgress(progress);
              }else{
                  contents.setPlayedTIme(progress);
              }

            }
            @Override
            public void isSeeking(boolean seeking) {
                musicPlayer.setSeeking(seeking);
            }
        });


        //무한 자료불러오기부분

       final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        contentsRecycerView.setLayoutManager(linearLayoutManager);
        contentsRecycerView.setHasFixedSize(true);
        contentsRecycerView.setAdapter(contentsAdatper);
        addItem(""+PAGE,COUNT);
        contentsRecycerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLastItem && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    addItem(""+PAGE,COUNT);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if(totalItemCount>0 && lastVisibleItemPosition !=RecyclerView.NO_POSITION &&(totalItemCount-1<=lastVisibleItemPosition)){
                    isLastItem =true;
                }else {
                    isLastItem =false;
                }
            }
        });
        checkresume = true;
        return view;
    }

    public void addItem(String page, String count) {
        ContentsRequest contentsRequest = new ContentsRequest(getContext(), page, count);
        NetworkManager.getInstance().getNetworkData(contentsRequest, new NetworkManager.OnResultListener<NetworkResultMyAccount>() {
            @Override

            public void onSuccess(NetworkRequest<NetworkResultMyAccount> request, NetworkResultMyAccount result) {
                contentses = result.getData();
                User user = result.getResult();
                contentsAdatper.setUser(user);
                for (Contents c : contentses) {
                    contentsAdatper.add(c);
                }
                PAGE +=1;

                musicPlayer.setRefreshContentses(contentsAdatper.getMcontentslist());
            }

            @Override
            public void onFail(NetworkRequest<NetworkResultMyAccount> request, int errorCode, String errorMessage, Throwable e) {
                Log.e("유저메인 실패", errorMessage);
            }
        });

    }

    Boolean checkresume;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(checkresume){
                // 다시보이면 할일
            }
        }else{
            if(musicPlayer != null){
                musicPlayer.allReset(); // 보이지않을경우 리셋
            }
            checkresume=false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkresume = true;
    }

}


