package team.nuga.thelabel.viewholder;

import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;

/**
 * Created by Tacademy on 2016-08-31.
 */
public class AccountTypeYoutubeViewHolder extends ParentContentsViewHolder implements View.OnClickListener, View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {
    TextView titleYoutubeView;
    private ImageView imageViewMenu;

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    public interface OnYoutubeContentsItemClick{
        void onYoutubeContentsItemClick(View view, Contents contents, int adapterPosition);
    }

    OnYoutubeContentsItemClick youtubeContentslist;
    public void setOnYoutubeContentsItemClickListener(OnYoutubeContentsItemClick youtubelistener){
        this.youtubeContentslist = youtubelistener;
    }

    public AccountTypeYoutubeViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(youtubeContentslist!=null){
                    youtubeContentslist.onYoutubeContentsItemClick(view , contents, getAdapterPosition());
                }
                Log.v("Youtube","test success");
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

    Contents contents;
    public void setYoutubeContents(Contents contents){
        this.contents = contents;
        titleYoutubeView.setText(contents.getContentsText());
    }
}
