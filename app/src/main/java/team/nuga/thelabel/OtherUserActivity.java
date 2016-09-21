package team.nuga.thelabel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.adapter.ContentsAdatper;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.NetworkResultOtherUser;
import team.nuga.thelabel.data.RoundImageTransform;
import team.nuga.thelabel.data.SearchUser;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.OtherUserRequest;
import team.nuga.thelabel.youtube.DeveloperKey;

public class OtherUserActivity extends AppCompatActivity {
    ContentsAdatper contentsAdatper;
    RecyclerView otherUserContents;
    ImageView imageView;
    Contents[] contentses;
    SearchUser searchUser;
    SeekBar mainProgressView ;
    ContentsMusicPlayer musicPlayer;
    boolean isLastItem;
    private static int PAGE; //페이지
    private static String COUNT="10"; //카운트 수
    int id;
    int[] labelID;

    @BindView(R.id.textView_profile_otherUserName)
    TextView otherUserName;
    @BindView(R.id.textView_otherUser_position)
    TextView  otherUserPosition;
    @BindView(R.id.textView_otherUser_city)
    TextView otherUserCity;
    @BindView(R.id.textView_otherUser_town)
    TextView otherUserTown;
    @BindView(R.id.textView_otherUser_Genre)
    TextView otherUserGenre;
    @BindView(R.id.imageView_OtherUser_profile)
    ImageView otherUserProfile;
    @BindView(R.id.imageView_otheruser_need)
    ImageView otherUserNeed;
    @OnClick(R.id.imageButton_back)
    public void backButton() {
        finish();
    }

//    @OnClick(R.id.imageView_message)
//    public void Message() {
//        Intent intent = new Intent(OtherUserActivity.this, MessageActivity.class);
//        intent.putExtra("user", searchUser);
//        startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);
        ButterKnife.bind(this);
        contentsAdatper = new ContentsAdatper();
        PAGE =1;
        mainProgressView = new SeekBar(this);
        musicPlayer = new ContentsMusicPlayer(this,contentsAdatper.getMcontentslist(),mainProgressView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_otheruser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
         id = intent.getIntExtra("id",0);
        setResult(RESULT_OK, intent);

        otherUserContents = (RecyclerView)findViewById(R.id.recyclerView_otherUser);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        otherUserContents.setLayoutManager(manager);
        otherUserContents.setAdapter(contentsAdatper);
//        Button button = (Button) findViewById(R.id.button_label);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AlertDialog.Builder(OtherUserActivity.this)
//                        .setMessage("참여한 레이블이 없습니다.")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        })
//                .show();
//            }
//        });
//
        TextView label = (TextView) findViewById(R.id.textView_otheruser_label);
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                labelID = searchUser.getLabel_id();
                 final CharSequence item[] = {""+labelID[0],""+labelID[1],""+labelID[2]};
                new AlertDialog.Builder(OtherUserActivity.this)
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                switch (item) {
                                    case 0:
                                        Intent intent = new Intent(OtherUserActivity.this, OtherLabelActivity.class);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        Intent intent1 = new Intent(OtherUserActivity.this, OtherLabelActivity.class);
                                        startActivity(intent1);
                                        break;
                                    case 2:
                                        Intent intent2 = new Intent(OtherUserActivity.this, OtherLabelActivity.class);
                                        startActivity(intent2);
                                        break;
                                }
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        })

                        .show();
            }

        });
//

        TextView labelInvite = (TextView) findViewById(R.id.textView_otheruser_label_invite);
        labelInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(OtherUserActivity.this)
                        .setMessage("권한이 없습니다.")

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });

        contentsAdatper.setonYoutubeThumnailClickListener(new ContentsAdatper.OnYoutubeThumnailClickListener() {
            @Override
            public void onYoutubeThumnailClickListener(View view, Contents contents, int position) {
                Toast.makeText(OtherUserActivity.this, "클릭", Toast.LENGTH_SHORT).show();
                Intent intent = YouTubeStandalonePlayer.createVideoIntent(OtherUserActivity.this, DeveloperKey.DEVELOPER_KEY, contents.getFileCode());
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
        otherUserContents.setLayoutManager(linearLayoutManager);
        otherUserContents.setHasFixedSize(true);
        otherUserContents.setAdapter(contentsAdatper);
        addItem(""+PAGE,COUNT);
        otherUserContents.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
//        button = (Button)findViewById(R.id.button_onelabel);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(OtherUserActivity.this, OtherLabelActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    public void addItem(String page, String count) {
        OtherUserRequest otherUserRequest = new OtherUserRequest(this, id, 1, 10);
        NetworkManager.getInstance().getNetworkData(otherUserRequest, new NetworkManager.OnResultListener<NetworkResultOtherUser>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResultOtherUser> request, NetworkResultOtherUser result) {
                contentses = result.getPost();
                searchUser = result.getUser();
                if(searchUser != null){
                    setSearchOtherUser();
                }
                for (Contents c : contentses) {
                    contentsAdatper.add(c);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResultOtherUser> request, int errorCode, String errorMessage, Throwable e) {
            }
        });

    }

    public void setSearchOtherUser() {
        otherUserName.setText(searchUser.getSearchUserName());
        otherUserCity.setText(" #"+searchUser.getSearchUserCity());
        otherUserTown.setText(" "+searchUser.getSearchUserTown());
        otherUserGenre.setText(searchUser.getSearchUserGenre());
        otherUserPosition.setText("#"+searchUser.getSearchUserPosition());
        Glide.with(this)
                .load(searchUser.getSearchUserImage())
                .transform(new RoundImageTransform(this))
                .into(otherUserProfile);
        int need = searchUser.getSearchUserNeed();
        switch (need){
            case 0:
                otherUserNeed.setVisibility(View.INVISIBLE);
                break;
            case 1:
                otherUserNeed.setVisibility(View.VISIBLE);
                break;
        }
//        labelID = searchUser.getLabel_id();
    }
}
