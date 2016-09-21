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
import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.ContentsAdatper;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.NewsFeedContents;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.NewsFeedRequest;
import team.nuga.thelabel.youtube.DeveloperKey;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedFragment extends Fragment {

    private static final String LOGTAG = "NewsFeedFragment ";

    boolean isLastItem;
    private static int PAGE; //페이지
    private static int COUNT = 10; //카운트 수
    private static int MEET = 10;


    ContentsAdatper contentsAdatper;

    Contents[] meetcontentses;
    Contents[] contentses;
    ContentsMusicPlayer musicPlayer;



    @BindView(R.id.recyclerview_newsfeed)
    RecyclerView contentsRecycerView;

    SeekBar mainProgressView;


    public NewsFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        ButterKnife.bind(this, view);
        PAGE = 1;
        mainProgressView = new SeekBar(getActivity());
        contentsAdatper = new ContentsAdatper();
        musicPlayer = new ContentsMusicPlayer(getActivity(),contentsAdatper.getMcontentslist(),mainProgressView);



        contentsAdatper.setonYoutubeThumnailClickListener(new ContentsAdatper.OnYoutubeThumnailClickListener() {
            @Override
            public void onYoutubeThumnailClickListener(View view, Contents contents, int position) {
                Toast.makeText(getContext(), "클릭", Toast.LENGTH_SHORT).show();
                Intent intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(), DeveloperKey.DEVELOPER_KEY, contents.getFileCode());
                getActivity().startActivity(intent);
                musicPlayer.allReset();
            }

        });


        NewsFeedRequest request = new NewsFeedRequest(getActivity(), PAGE, COUNT, MEET);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NewsFeedContents>() {
            @Override
            public void onSuccess(NetworkRequest<NewsFeedContents> request, NewsFeedContents result) {

                if (result.isError()) {
                    Log.e(LOGTAG, "error : " + result.getError().getMessage());
                } else {
                    meetcontentses = result.getMeetpost();
                    for (Contents c : meetcontentses) {
                        Log.d("게시글 ID", "" + c.getContentsID());
                        Log.d("파일경로", "" + c.getContentsPath());
                        Log.d("파일타입", "" + c.getContentsType());
                        Log.d("좋아요 개수", "" + c.getLikeCount());
                        contentsAdatper.add(c);
                    }
                    contentses = result.getPost();
                    for (Contents c : contentses) {
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
                Log.e(LOGTAG, "뉴스피드 네트워크 받아오기 실패 " + errorMessage);
            }
        });


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
                if (contents.getContentsID() == musicPlayer.getPlayedContentsId()) {
                    mainProgressView.setProgress(progress);
                    musicPlayer.setMusicProgress(progress);
                } else {
                    contents.setPlayedTIme(progress);
                }

            }

            @Override
            public void isSeeking(boolean seeking) {
                musicPlayer.setSeeking(seeking);
            }
        });


        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        contentsRecycerView.setLayoutManager(linearLayoutManager);
        contentsRecycerView.setHasFixedSize(true);
        contentsRecycerView.setAdapter(contentsAdatper);
        addItem(PAGE, COUNT);
        contentsRecycerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLastItem && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    addItem(PAGE, COUNT);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (totalItemCount > 0 && lastVisibleItemPosition != RecyclerView.NO_POSITION && (totalItemCount - 1 <= lastVisibleItemPosition)) {
                    isLastItem = true;
                } else {
                    isLastItem = false;
                }
            }
        });
        checkresume = true;

        return view;

    }

    public void addItem(int page, int count) {
        NewsFeedRequest request = new NewsFeedRequest(getActivity(), page, count, MEET);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NewsFeedContents>() {
            @Override
            public void onSuccess(NetworkRequest<NewsFeedContents> request, NewsFeedContents result) {

                if (result.isError()) {
                    Log.e(LOGTAG, "error : " + result.getError().getMessage());
                } else {
                    meetcontentses = result.getMeetpost();
                    for (Contents c : meetcontentses) {
                        Log.d("게시글 ID", "" + c.getContentsID());
                        Log.d("파일경로", "" + c.getContentsPath());
                        Log.d("파일타입", "" + c.getContentsType());
                        Log.d("좋아요 개수", "" + c.getLikeCount());
                        contentsAdatper.add(c);
                    }
                    contentses = result.getPost();
                    for (Contents c : contentses) {
                        Log.d("게시글 ID", "" + c.getContentsID());
                        Log.d("파일경로", "" + c.getContentsPath());
                        Log.d("파일타입", "" + c.getContentsType());
                        Log.d("좋아요 개수", "" + c.getLikeCount());
                        contentsAdatper.add(c);
                    }
                }
                musicPlayer.setRefreshContentses(contentsAdatper.getMcontentslist());
            }

            @Override
            public void onFail(NetworkRequest<NewsFeedContents> request, int errorCode, String errorMessage, Throwable e) {
                Log.e(LOGTAG, "뉴스피드 네트워크 받아오기 실패 " + errorMessage);
            }
        });

    }

    Boolean checkresume;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (checkresume) {
                // 다시보이면 할일
            }
        } else {
            if (musicPlayer != null) {
                musicPlayer.allReset(); // 보이지않을경우 리셋
            }
            checkresume = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkresume = true;
    }

}


