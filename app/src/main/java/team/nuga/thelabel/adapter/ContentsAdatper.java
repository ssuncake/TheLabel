package team.nuga.thelabel.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        implements AccountTypeMusicViewHolder.OnMusicContentsItemClick, AccountTypePictureViewHolder.OnPictureContentsItemClick, AccountTypeYoutubeViewHolder.OnYoutubeThumnailClickListener, AccountTypeProfileViewHolder.OnSettingImageClick {

    private ArrayList<Contents> mcontentslist = new ArrayList<>();
    User user;

    public ArrayList<Contents> getMcontentslist() {
        return mcontentslist;
    }

    public void add(Contents contents) {
        mcontentslist.add(contents);
        notifyDataSetChanged();
    }

    public void setUser(User user) {
        if (user != null) {
            this.user = user;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (user == null) {
            viewType = mcontentslist.get(position).getContentsType();
            return viewType;
        } else {
            if (position == 0) {  //viewType의 position
                return -1;  // 0일경우 Music이 보이고 -1일경우는 Music, Picture, Youtube중에 없기 때문에 default인 Profile이 뜬다.
            } else {
                viewType = mcontentslist.get(position - 1).getContentsType(); //listview position
                return viewType;
            }
        }
    }

    @Override
    public ParentContentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case Contents.MUSIC:
                ViewGroup viewOne = (ViewGroup) layoutInflater.inflate(R.layout.cardview_contents_type_music, parent, false);
                AccountTypeMusicViewHolder accounttypeOne = new AccountTypeMusicViewHolder(viewOne);
                accounttypeOne.setOnMusicContentsItemClickListener(this);
                return accounttypeOne;
            case Contents.PICTURE:
                ViewGroup viewTwo = (ViewGroup) layoutInflater.inflate(R.layout.cardview_contents_type_picture, parent, false);
                AccountTypePictureViewHolder accounttypeTwo = new AccountTypePictureViewHolder(viewTwo);
                return accounttypeTwo;
            case Contents.YOUTUBE:
                ViewGroup viewThree = (ViewGroup) layoutInflater.inflate(R.layout.cardview_contents_type_youtube, parent, false);
                AccountTypeYoutubeViewHolder accounttypeThree = new AccountTypeYoutubeViewHolder(viewThree);
                accounttypeThree.setonYoutubeThumnailClickListener(this);
                return accounttypeThree;
            default:
                ViewGroup viewFour = (ViewGroup) layoutInflater.inflate(R.layout.cardview_account_type_profile, parent, false);
                AccountTypeProfileViewHolder accounttypeFour = new AccountTypeProfileViewHolder(viewFour);
                accounttypeFour.setOnSettingImageClick(this);
                return accounttypeFour;
        }
    }

    @Override
    public void onBindViewHolder(final ParentContentsViewHolder holder, int position) {
        if (user == null) {
            holder.setData(mcontentslist.get(position));
        } else {
            Log.w("ContentAdapter", "온바인드 뷰홀더 ->" + position);
            if (position == 0) {
                holder.setProfile(user);
            } else {
                holder.setData(mcontentslist.get(position - 1));
            }
        }


    }


    @Override
    public int getItemCount() {
        if (user == null) {
            return mcontentslist.size(); //profileviewholder 추가
        } else {
            return mcontentslist.size() + 1; //profileviewholder 추가
        }

    }

    @Override
    public void onYoutubeThumnailClickListener(View view, Contents contents, int position) {
        if(youtubeThumListener != null){
            youtubeThumListener.onYoutubeThumnailClickListener(view, contents, position);
        }
    }


    public interface OnYoutubeThumnailClickListener { // 유튜브
        public void onYoutubeThumnailClickListener(View view, Contents contents , int position);
    }
    OnYoutubeThumnailClickListener youtubeThumListener;
    public void setonYoutubeThumnailClickListener(OnYoutubeThumnailClickListener thumnailClickListener) {
        this.youtubeThumListener = thumnailClickListener;
    }


    public interface OnSettingItemClickListener { //SettingImage
        public void onSettingItemClick(View view, int position);
    }
    OnSettingItemClickListener settingItemClickListener;
    public void setOnSettingImageClickListener(OnSettingItemClickListener imageClickListener) {
        this.settingItemClickListener = imageClickListener;
    }

    @Override
    public void onSettingImageClick(View view, int adapterPosition) {
        if (settingItemClickListener != null) {
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
        public void onPlayerItemClick(View view, View parent, Contents contents, int position);
    }

    OnPlayerItemClickListener playerListener;

    public void setOnPlayerItemClickListener(OnPlayerItemClickListener playerListener) {
        this.playerListener = playerListener;
    }


    @Override
    public void onMusicContentItemClick(View view, View parent, Contents contents, int adapterPosition) {
        if (playerListener != null) {
            playerListener.onPlayerItemClick(view, parent, contents, adapterPosition);
        }
    }

    @Override
    public void onPictureContentsItemClick(View view, Contents contents, int adapterPosition) {
        if (listener != null) {
            listener.onContentsItemClick(view, contents, adapterPosition);
        }

    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


}
