package team.nuga.thelabel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.viewholder.AccountTypeMusicViewHolder;
import team.nuga.thelabel.viewholder.AccountTypePictureViewHolder;
import team.nuga.thelabel.viewholder.AccountTypeYoutubeViewHolder;
import team.nuga.thelabel.viewholder.ParentContentsViewHolder;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class ContentsAdatper extends RecyclerView.Adapter<ParentContentsViewHolder>
        implements AccountTypeMusicViewHolder.OnMusicContentsItemClick, AccountTypePictureViewHolder.OnPictureContentsItemClick, AccountTypeYoutubeViewHolder.OnYoutubeContentsItemClick {
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
    public void add(Contents contents){
        mcontentslist.add(contents);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        int viewType;
        viewType = mcontentslist.get(position).getContentsType();
        return viewType;
    }

    @Override
    public ParentContentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case Contents.MUSIC:
                ViewGroup viewOne = (ViewGroup) layoutInflater.inflate(R.layout.view_account_type_two, parent,false);
                AccountTypeMusicViewHolder accounttypeOne = new AccountTypeMusicViewHolder(viewOne);
                return accounttypeOne;
            case Contents.PICTURE:
                ViewGroup viewTwo = (ViewGroup) layoutInflater.inflate(R.layout.view_account_type_three, parent,false);
                AccountTypePictureViewHolder accounttypeTwo = new AccountTypePictureViewHolder(viewTwo);
                return accounttypeTwo;
            case Contents.YOUTUBE:
                ViewGroup viewThree = (ViewGroup) layoutInflater.inflate(R.layout.view_account_type_four, parent,false);
                AccountTypeYoutubeViewHolder accounttypeThree = new AccountTypeYoutubeViewHolder(viewThree);
                return accounttypeThree;
            default:
                ViewGroup viewFour = (ViewGroup) layoutInflater.inflate(R.layout.view_account_type_four, parent,false);
                AccountTypeYoutubeViewHolder accounttypeFour = new AccountTypeYoutubeViewHolder(viewFour);
                return accounttypeFour;
        }
    }

    @Override
    public void onBindViewHolder(ParentContentsViewHolder holder, int position) {
        holder.setContents(mcontentslist.get(position));

    }
    @Override
    public int getItemCount() {
        return mcontentslist.size();
    }


    public interface OnContentsItemClickListener {
        public void onContentsItemClick(View view, Contents user, int position);
    }

    OnContentsItemClickListener listener;

    public void setOnAdapterItemClickListener(OnContentsItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onMusicContentItemClick(View view, Contents contents, int adapterPosition) {
        if (listener != null) {
            listener.onContentsItemClick(view, contents, adapterPosition);
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
