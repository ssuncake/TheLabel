package team.nuga.thelabel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import java.util.ArrayList;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.viewholder.AccountTypeMusicViewHolder;
import team.nuga.thelabel.viewholder.AccountTypePictureViewHolder;
import team.nuga.thelabel.viewholder.AccountTypeProfileViewHolder;
import team.nuga.thelabel.viewholder.AccountTypeYoutubeViewHolder;
import team.nuga.thelabel.viewholder.ParentContentsViewHolder;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class ContentsAdatper extends RecyclerView.Adapter<ParentContentsViewHolder>
        implements AccountTypeMusicViewHolder.OnMusicContentsItemClick, AccountTypePictureViewHolder.OnPictureContentsItemClick, AccountTypeYoutubeViewHolder.OnYoutubeContentsItemClick,
        YouTubePlayer.OnInitializedListener,AccountTypeProfileViewHolder.OnSettingImageClick{
//    List<User> userList = new ArrayList<>();
//    List<Contents> contentsList = new ArrayList<>();
//    MusicContents musicContents;
//    PictureContents pictureContents;
//    YoutubeContents youtubeContents;
////    public void add(User user){
//        muserlist.add(user);
//        notifyDataSetChanged();
//    }

//    private ArrayList<User> muserlist = new ArrayList<>();
    private ArrayList<Contents> mcontentslist = new ArrayList<>();
    User user;
    public void add(Contents contents){
        mcontentslist.add(contents);
        notifyDataSetChanged();
    }

    public void setUser(User user){
        this.user = user;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;

        if(position == 0){  //viewType의 position
            return -1;  // 0일경우 Music이 보이고 -1일경우는 Music, Picture, Youtube중에 없기 때문에 default인 Profile이 뜬다.
        }
        else {
        viewType = mcontentslist.get(position-1).getContentsType(); //listview position
//        viewType = mcontentslist.get(position).getContentsType();

        return viewType;}
    }

    @Override
    public ParentContentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case Contents.MUSIC:
                ViewGroup viewOne = (ViewGroup) layoutInflater.inflate(R.layout.cardview_contents_type_music, parent,false);
                AccountTypeMusicViewHolder accounttypeOne = new AccountTypeMusicViewHolder(viewOne);
                accounttypeOne.setOnMusicContentsItemClickListener(this);
                return accounttypeOne;
            case Contents.PICTURE:
                ViewGroup viewTwo = (ViewGroup) layoutInflater.inflate(R.layout.cardview_contents_type_picture, parent,false);
                AccountTypePictureViewHolder accounttypeTwo = new AccountTypePictureViewHolder(viewTwo);
                return accounttypeTwo;
            case Contents.YOUTUBE:
                ViewGroup viewThree = (ViewGroup) layoutInflater.inflate(R.layout.cardview_contents_type_youtube, parent,false);
                AccountTypeYoutubeViewHolder accounttypeThree = new AccountTypeYoutubeViewHolder(viewThree);
                return accounttypeThree;
            default:
                ViewGroup viewFour = (ViewGroup) layoutInflater.inflate(R.layout.cardview_account_type_profile, parent,false);
               AccountTypeProfileViewHolder accounttypeFour = new AccountTypeProfileViewHolder(viewFour);
                accounttypeFour.setOnSettingImageClick(this);
                return accounttypeFour;
        }
    }

    @Override
    public void onBindViewHolder(ParentContentsViewHolder holder, int position) {
        if (position == 0) {
            holder.setProfile(user);
        }else {
            holder.setData(user, mcontentslist.get(position - 1));
        }
    }
    @Override
    public int getItemCount() {
        return mcontentslist.size()+1; //profileviewholder 추가
    }

    @Override //youtube
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }



    public interface OnSettingItemClickListener{ //SettingImage
        public void onSettingItemClick(View view, int position);
    }
    OnSettingItemClickListener settingItemClickListener;
    public void setOnSettingImageClickListener(OnSettingItemClickListener imageClickListener){
        this.settingItemClickListener = imageClickListener;
    }

    @Override
    public void onSettingImageClick(View view, int adapterPosition) {
        if(settingItemClickListener!=null){
            settingItemClickListener.onSettingItemClick(view, adapterPosition);
        }
    }

    public interface OnContentsItemClickListener {
        public void onContentsItemClick(View view, Contents user, int position);
    }

    OnContentsItemClickListener listener;

    public void setOnAdapterItemClickListener(OnContentsItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnPlayerItemClickListener {  //Player
        public void onPlayerItemClick(View view, View parent,Contents contents, int position, boolean isChecked);
    }
    OnPlayerItemClickListener playerListener;
    public void setOnPlayerItemClickListener(OnPlayerItemClickListener playerListener) {
        this.playerListener = playerListener;
    }
    @Override
    public void onMusicContentItemClick(View view,View parent, Contents contents, int adapterPosition, boolean isChecked) {
        if (playerListener != null) {
            playerListener.onPlayerItemClick(view ,parent,contents,adapterPosition, isChecked);
        }
    }

    @Override
    public void onPictureContentsItemClick(View view, Contents contents, int adapterPosition) {
        if (listener != null) {
            listener.onContentsItemClick(view, contents, adapterPosition);
        }

    }

    @Override
    public void onYoutubeContentsItemClick(View view, Contents contents, int adapterPosition) {
        if (listener != null) {
            listener.onContentsItemClick(view, contents, adapterPosition);
        }
    }
}
