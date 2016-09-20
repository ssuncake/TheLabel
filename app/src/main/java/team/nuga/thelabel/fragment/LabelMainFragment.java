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
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.ContentsMusicPlayer;
import team.nuga.thelabel.LabelSettingActivity;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.MemberListActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.ContentsAdatper;
import team.nuga.thelabel.adapter.LabelMainListAdapter;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.Member;
import team.nuga.thelabel.data.NetworkResultLabeMain;
import team.nuga.thelabel.data.NetworkResultMyAccount;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.ContentsRequest;
import team.nuga.thelabel.request.GetLabelByIdMainRequest;
import team.nuga.thelabel.wiget.LabelMainTop;
import team.nuga.thelabel.youtube.DeveloperKey;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelMainFragment extends Fragment {


    public static final String LOGTAG = "LabelMainFragment ";
    public static final String LEADERID = "leaderid";

    User user;
    Label label;
    LabelMainListAdapter adapter;
    Member[] members;



    ContentsAdatper contentsAdatper;
    Contents[] contentses;


    boolean isLastItem;
    private static int PAGE; //페이지
    private static String COUNT="10"; //카운트 수
    ContentsMusicPlayer musicPlayer;


    SeekBar mainProgressView;

    @BindView(R.id.view_LabelMainTop)
    LabelMainTop labelMainTop;
    @BindView(R.id.recyclerView_LabelMain_Member)
    RecyclerView memberRecycler;
    @BindView(R.id.recyclerView_LabelMain_Contetns)
    RecyclerView contentsRecycler;
    @BindView(R.id.button_LabelMain_goSetting)
    Button goLabelSetting;

    @OnClick(R.id.button_LabelMain_back)
    public void clickBackButton(){
        LabelContainerFragment parent  = (LabelContainerFragment)getParentFragment();
        parent.backSelectLabel();
        // 레이블선택으로 돌아가기위해 역시 부모 프래그먼트를 얻어와 메소드를 실행
    }
    @OnClick(R.id.button_LabelMain_goSetting)
    public void clickSettingButton(){
        Intent intent = new Intent(getActivity(), LabelSettingActivity.class);
        intent.putExtra(MainActivity.SELECTLABEL,label);
        startActivityForResult(intent,MainActivity.REQUEST_SETTINGLABEL);
    }

    @OnClick(R.id.button_LabelMain_Memberlist)
    public void clickMemberList(){
        Intent intent = new Intent(getActivity(), MemberListActivity.class);
        intent.putExtra(MainActivity.LABELID,label.getLabelID());
        intent.putExtra(LEADERID,label.getLabelLeaderID());
        Log.w(LOGTAG,"전달해 주는 leaderid = "+label.getLabelLeaderID());
        startActivity(intent);
    }





    public LabelMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_label_main, container, false);
        ButterKnife.bind(this,view);

        PAGE =1;
        mainProgressView = new SeekBar(getActivity());
        contentsAdatper = new ContentsAdatper();
        musicPlayer = new ContentsMusicPlayer(getActivity(),contentsAdatper.getMcontentslist(),mainProgressView);

        int leaderId = getArguments().getInt(MainActivity.LABELID);
        User user = (User)getArguments().getSerializable(MainActivity.MAINUSER);

        int userid = user.getUserID();
        goLabelSetting.setVisibility(View.INVISIBLE);
        if(leaderId==userid){

            goLabelSetting.setVisibility(View.VISIBLE);
        }
        GetLabelByIdMainRequest request = new GetLabelByIdMainRequest(getActivity(),leaderId);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResultLabeMain>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResultLabeMain> request, NetworkResultLabeMain result) {

                if(result.isError()){
                    Log.e(LOGTAG,"error : "+result.getError().getMessage());
                }else{
                    label = result.getResult();
                    labelMainTop.setLabel(label);
                    labelMainTop.setIsMyLabel(true);
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
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        memberRecycler.setLayoutManager(manager);

        contentsAdatper.setonYoutubeThumnailClickListener(new ContentsAdatper.OnYoutubeThumnailClickListener() {
            @Override
            public void onYoutubeThumnailClickListener(View view, Contents contents, int position) {
                Toast.makeText(getContext(), "클릭", Toast.LENGTH_SHORT).show();
                Intent intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(), DeveloperKey.DEVELOPER_KEY, contents.getFileCode());
                getActivity().startActivity(intent);
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

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        contentsRecycler.setLayoutManager(linearLayoutManager);
        contentsRecycler.setHasFixedSize(true);
        contentsRecycler.setAdapter(contentsAdatper);
        addItem(""+PAGE,COUNT);
        contentsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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




    @Override
    public void onStop() {
        super.onStop();
        if(musicPlayer!=null){
            musicPlayer.allReset();
        }
    }

}
