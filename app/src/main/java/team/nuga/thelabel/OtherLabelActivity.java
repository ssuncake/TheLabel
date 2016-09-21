package team.nuga.thelabel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.adapter.ContentsAdatper;
import team.nuga.thelabel.adapter.LabelMainListAdapter;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.Member;
import team.nuga.thelabel.data.NetworkResultLabeMain;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.GetLabelByIdMainRequest;
import team.nuga.thelabel.wiget.LabelMainTop;
import team.nuga.thelabel.youtube.DeveloperKey;

public class OtherLabelActivity extends AppCompatActivity {
    public static final String LOGTAG = "LabelMainFragment ";
    public static final String LEADERID = "leaderid";

    User user;
    Label label;
    int labelLeader;
    LabelMainListAdapter adapter;
    Member[] members;

    ContentsAdatper contentsAdatper;
    Contents[] contentses;

    boolean isLastItem;
    private static int PAGE; //페이지
    private static int COUNT=10; //카운트 수
    ContentsMusicPlayer musicPlayer;
    SeekBar mainProgressView;
    int labelId;

    @BindView(R.id.view_OtherLabelMainTop)
    LabelMainTop labelMainTop;
    @BindView(R.id.recyclerView_OtherLabelMain_Member)
    RecyclerView memberRecycler;
    @BindView(R.id.recyclerView_OtherLabelMain_Contetns)
    RecyclerView contentsRecycler;

    @OnClick(R.id.button_OtherLabelMain_Memberlist)
    public void clickMemberList(){
        Intent intent = new Intent(this, MemberListActivity.class);
        intent.putExtra(MainActivity.LABELID,label.getLabelID());
        intent.putExtra(LEADERID,label.getLabelLeaderID());
        Log.w(LOGTAG,"전달해 주는 leaderid = "+label.getLabelLeaderID());
        startActivity(intent);
    }

    @OnClick(R.id.imageButton_back)
    public void backButton(){
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_label);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_otherlabel);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        TextView text = (TextView) findViewById(R.id.text_otherlabel);
        String s = intent.getStringExtra("other");
        text.setText(s + "");

        PAGE =1;
        mainProgressView = new SeekBar(this);
        contentsAdatper = new ContentsAdatper();
        musicPlayer = new ContentsMusicPlayer(this,contentsAdatper.getMcontentslist(),mainProgressView);


        // 레이블 레이블아이디와 로그인 유저정보를 받아야함

        labelId = getIntent().getIntExtra(MainActivity.LABELID,-1);
        User user = (User)getIntent().getSerializableExtra(MainActivity.MAINUSER);


        GetLabelByIdMainRequest request = new GetLabelByIdMainRequest(this,labelId,PAGE,COUNT);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResultLabeMain>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResultLabeMain> request, NetworkResultLabeMain result) {

                if(result.isError()){
                    Log.e(LOGTAG,"error : "+result.getError().getMessage());
                }else{
                    label = result.getResult();
                    labelMainTop.setLabel(label);
                    labelMainTop.setIsMyLabel(true);
                    labelLeader = label.getLabelLeaderID();
                    Log.w(LOGTAG," leaderid = "+label.getLabelLeaderID()+"labelneed :"+label.getLabelNeedPositionList().toString());

                    members = result.getMember();
                    for(Member m : members)
                        adapter.addUser(m);
                    contentses = result.getData();
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
            public void onFail(NetworkRequest<NetworkResultLabeMain> request, int errorCode, String errorMessage, Throwable e) {
                Log.e(LOGTAG,"레이블 찾기 실패 : "+errorMessage);
            }
        });

        adapter = new LabelMainListAdapter();
        memberRecycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        memberRecycler.setLayoutManager(manager);

        contentsAdatper.setonYoutubeThumnailClickListener(new ContentsAdatper.OnYoutubeThumnailClickListener() {
            @Override
            public void onYoutubeThumnailClickListener(View view, Contents contents, int position) {
                Intent intent = YouTubeStandalonePlayer.createVideoIntent(OtherLabelActivity.this, DeveloperKey.DEVELOPER_KEY, contents.getFileCode());
                startActivity(intent);
                musicPlayer.allReset();
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

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        contentsRecycler.setLayoutManager(linearLayoutManager);
        contentsRecycler.setHasFixedSize(true);
        contentsRecycler.setAdapter(contentsAdatper);
        addItem(PAGE,COUNT);
        contentsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLastItem && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    addItem(PAGE,COUNT);
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

    }
    public void addItem(int page, final int count) {
        GetLabelByIdMainRequest request = new GetLabelByIdMainRequest(this,labelId,page,count);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResultLabeMain>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResultLabeMain> request, NetworkResultLabeMain result) {

                if(result.isError()){
                    Log.e(LOGTAG,"error : "+result.getError().getMessage());
                }else{
                    Contents[] newcontentses = result.getData();
                    for (Contents c : newcontentses) {
                        Log.d("게시글 ID", "" + c.getContentsID());
                        Log.d("파일경로", "" + c.getContentsPath());
                        Log.d("파일타입", "" + c.getContentsType());
                        Log.d("좋아요 개수", "" + c.getLikeCount());
                        contentsAdatper.add(c);
                    }
                    musicPlayer.setRefreshContentses(contentsAdatper.getMcontentslist());

                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResultLabeMain> request, int errorCode, String errorMessage, Throwable e) {
                Log.e(LOGTAG,"레이블 찾기 실패 : "+errorMessage);
            }
        });

    }




    @Override
    public void onStop() {
        super.onStop();
        if(musicPlayer!=null){
            musicPlayer.allReset();
        }
    }
}
