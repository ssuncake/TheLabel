package team.nuga.thelabel.viewholder;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaMetadataRetriever;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.RoundImageTransform;
import team.nuga.thelabel.data.User;

/**
 * Created by Tacademy on 2016-08-31.
 */
public class AccountTypeMusicViewHolder extends ParentContentsViewHolder implements View.OnClickListener, View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {
    @BindView(R.id.textView_username)
    TextView userName;
    @BindView(R.id.textView_numlike)
    TextView likeCount;
    @BindView(R.id.textView_content_time)
    TextView conetentTime;
    @BindView(R.id.imageView_profile)
    ImageView profileImage;
    @BindView(R.id.seekBar_Play)
    SeekBar playSeekbar;

    CheckBox playCheckBox;
    Contents contents;

    Context context;


    HashMap<String, String> retriever = new HashMap<>();
    MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();



    public SeekBar getPlaySeekbar() {
        return playSeekbar;
    }

    private ImageView imageViewMenu;




    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contents_change:
                Toast.makeText(itemView.getContext(), "Contents Change", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contents_delete:
                new AlertDialog.Builder(itemView.getContext())
                        .setTitle("삭제 확인")
                        .setMessage("이 게시물을 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                break;
        }
        return true;
    }
    public interface OnMusicContentsItemClick { //MediaPlayer 리스너
        void onMusicContentItemClick(View view, View parent, Contents contents, int adapterPosition);
    }

    OnMusicContentsItemClick musicContentslist;

    public void setOnMusicContentsItemClickListener(OnMusicContentsItemClick musiclistener) {
        this.musicContentslist = musiclistener;
    }

    public interface OnPlayerItemClickListener {  //Player
        public void onPlayerItemClick(View view, View parent,Contents contents, int position, boolean isChecked);
    }
    OnPlayerItemClickListener playerListener;
    public void setOnPlayerItemClickListener(OnPlayerItemClickListener playerListener) {
        this.playerListener = playerListener;
    }


    public AccountTypeMusicViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();


        playCheckBox = (CheckBox)itemView.findViewById(R.id.checkbox_player);

        playCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicContentslist!=null){
                    musicContentslist.onMusicContentItemClick(view, itemView, contents, getAdapterPosition());
                }
            }
        });


        CheckBox likeCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox_like_off);
        likeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheckd) {
                if (isCheckd) {
                    int likeCountAdd = contents.getLikeCount() + 1;
                    likeCount.setText("" + likeCountAdd);

                } else {
                    likeCount.setText("" + contents.getLikeCount());
                }
            }
        });


        itemView.setOnClickListener(this); //popup listener
        itemView.setOnLongClickListener(this);  //popup listener
        imageViewMenu = (ImageView) itemView.findViewById(R.id.imageView_menu);
        imageViewMenu.setOnClickListener(this); //imageMenu 클릭 시 메뉴 팝업메뉴가 뜨도록
    }

    @Override
    public void onClick(View view) {
        if (view == imageViewMenu) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.contents_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }
    }



    public void applyData(Contents ncontents) {
        contents = ncontents;

        String musicUri = contents.getContentsPath();
        mediaMetadataRetriever.setDataSource(musicUri,retriever);
        String s = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        Log.e("미디어 리트리버 테스트",s+"");
        if(contents.getContentsType() == Contents.MUSIC){
            contents.setMoveListener(new Contents.onPlayTimeMoveListener() {
                @Override
                public void movePlayTime() {
                    playSeekbar.setProgress(contents.getPlayedTIme());
                }
            });

            contents.setMaxListener(new Contents.onPlayTimeMaxListener() {
                @Override
                public void setMax() {
                    playSeekbar.setMax(contents.getPlayTimeMax());
                }
            });

            contents.setOnMusicState(new Contents.onMusicState() {
                @Override
                public void setChange(int state) {
                    switch (state) {
                        case Contents.PLAY:
                            playCheckBox.setChecked(true);
                            break;
                        case Contents.PUASE:
                        case Contents.STOP:
                            playCheckBox.setChecked(false);
                            break;
                    }
                }
            });

            playSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progress = -1;
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (b) {
                        progress = i;
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    progress = -1;
                    moveSeekBar.isSeeking(true);

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (progress != -1) {
                        moveSeekBar.moveSeekbar(contents,progress, getAdapterPosition());
                    }
                    moveSeekBar.isSeeking(false);
                }
            });


            conetentTime.setText(contents.getContentCreateDate());
            likeCount.setText("" + contents.getLikeCount());
            if(contents.getPlayedMode()==Contents.PLAY){
                Log.w("온바인드 뷰홀더",contents.getContentsID()+"PLAY");
                playCheckBox.setChecked(true);
                playSeekbar.setProgress(contents.getPlayedTIme());
            }else if( contents.getPlayedMode() == Contents.PUASE){
                Log.w("온바인드 뷰홀더",contents.getContentsID()+"PUASE");
                playCheckBox.setChecked(false);
                playSeekbar.setProgress(contents.getPlayedTIme());
            }else if(contents.getPlayedMode() == Contents.STOP){
                Log.w("온바인드 뷰홀더",contents.getContentsID()+"STOP");
                playCheckBox.setChecked(false);
                playSeekbar.setProgress(0);
            }
        }

        userName.setText(contents.getWriterName());
        Glide.with(profileImage.getContext())
                .load(contents.getWriterImage())
                .transform(new RoundImageTransform(profileImage.getContext()))
                .into(profileImage);



    }

    @Override
    public void applyUser(User user) {

    }

    public void resetMusic(){
        contents.setPlayedMode(Contents.STOP);
        playCheckBox.setChecked(false);
        playSeekbar.setProgress(0);
    }

    public void setSeekbarPlaytime(){
        playSeekbar.setProgress(contents.getPlayedTIme());
    }

    public void setSeekBarMax(){
        playSeekbar.setMax(contents.getPlayTimeMax());
    }

    public interface onMoveSeekBar{
        public void moveSeekbar(Contents contents,int position, int adapterPosition);
        public void isSeeking(boolean seeking);
    }

    public onMoveSeekBar moveSeekBar;

    public void setMoveSeekBar(onMoveSeekBar moveSeekBar) {
        this.moveSeekBar = moveSeekBar;
    }
}
