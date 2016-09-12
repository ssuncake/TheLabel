package team.nuga.thelabel.viewholder;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.RoundImageTransform;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.youtube.DeveloperKey;

/**
 * Created by Tacademy on 2016-08-31.
 */
public class AccountTypeYoutubeViewHolder extends ParentContentsViewHolder implements View.OnClickListener, View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {
    @BindView(R.id.textView_numlike)
    TextView likeCount;
    @BindView(R.id.textView_username)
    TextView userName;
    @BindView(R.id.textView_content_time)
    TextView conetentTime;
    @BindView(R.id.imageView_profile)
    ImageView profileImage;
    private ImageView imageViewMenu;
    @BindView(R.id.imageView_youtubeClick)
    ImageView imageView_youtubeClick;

    @BindView(R.id.youtubePlayer_view)
    YouTubeThumbnailView youTubePlayer;
    String youtubeVideoId;


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


    public interface OnYoutubeContentsItemClick {
        void onYoutubeContentsItemClick(View view, Contents contents, int adapterPosition);
    }

    OnYoutubeContentsItemClick youtubeContentslist;

    public void setOnYoutubeContentsItemClick(OnYoutubeContentsItemClick youtubelistener) {
        this.youtubeContentslist = youtubelistener;
    }

    public AccountTypeYoutubeViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

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
        imageView_youtubeClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(youtubeThumListener != null){
                    youtubeThumListener.onYoutubeThumnailClickListener(view, contents, getAdapterPosition());
                    Log.e("썸클릭","썸썸");
                }
            }
        });

        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                youTubePlayer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                Log.e("썸네일 에러"," 썸네일 에러");
            }
        };
        youTubePlayer.initialize(DeveloperKey.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(youtubeVideoId);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("썸네일 초기화 에러"," 썸네일 페일");
            }
        });

    }

    public interface OnYoutubeThumnailClickListener { // 유튜브
        void onYoutubeThumnailClickListener(View view, Contents contents , int position);
    }

    OnYoutubeThumnailClickListener youtubeThumListener;
    public void setonYoutubeThumnailClickListener(OnYoutubeThumnailClickListener thumnailClickListener) {
        this.youtubeThumListener = thumnailClickListener;
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


    public void setYoutubeContents(Contents contents) {
        this.contents = contents;
    }


    @Override
    public void applyData(Contents contents) {
        likeCount.setText("" + contents.getLikeCount());
        userName.setText(contents.getWriterName());
        Glide.with(profileImage.getContext())
                .load(contents.getWriterImage())
                .transform(new RoundImageTransform(profileImage.getContext()))
                .into(profileImage);
        conetentTime.setText(contents.getContentCreateDate());
        youtubeVideoId = contents.getFileCode();
    }

    @Override
    public void applyUser(User user) {

    }
}
