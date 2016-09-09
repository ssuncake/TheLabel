package team.nuga.thelabel.viewholder;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
        void onMusicContentItemClick(View view, View parent, Contents contents, int adapterPosition,boolean isChecked);
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

        playCheckBox = (CheckBox)itemView.findViewById(R.id.checkbox_player);
        playCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(musicContentslist!=null){
                    musicContentslist.onMusicContentItemClick(compoundButton , itemView, contents, getAdapterPosition(),isChecked);
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


//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (musicContentslist != null) {
//                    musicContentslist.onMusicContentItemClick(view, contents, getAdapterPosition());
//                }
//                Log.v("Music", "test success");
//            }
//        });
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

    public void setMusicContents(Contents contents) {
        this.contents = contents;
    }


    public void applyData(User user, Contents contents) {
            userName.setText(user.getUserName());
        Glide.with(profileImage.getContext())
                .load(user.getImageUrl())
                .transform(new RoundImageTransform(profileImage.getContext()))
                .into(profileImage);
//        Log.e("시간", "Time"+contents.getContentTime());

            conetentTime.setText(contents.getContentTime());
            likeCount.setText("" + contents.getLikeCount());
        if(contents.getPlayedMode()==Contents.PLAY ){
            playCheckBox.setChecked(true);
            playSeekbar.setProgress(contents.getPlayedTIme());
        }else if( contents.getPlayedMode() == Contents.PUASE){
            playCheckBox.setChecked(false);
            playSeekbar.setProgress(contents.getPlayedTIme());
        }else if(contents.getPlayedMode() == Contents.STOP){
            resetMusic();
        }


    }


    public void resetMusic(){
        contents.setPlayedMode(Contents.STOP);
        playCheckBox.setChecked(false);
        playSeekbar.setProgress(0);
    }
}
