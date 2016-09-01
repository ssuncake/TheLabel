package team.nuga.thelabel.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;

/**
 * Created by Tacademy on 2016-08-31.
 */
public class AccountTypeMusicViewHolder extends ParentContentsViewHolder {
    TextView titleMusicView;

    public interface OnMusicContentsItemClick{
        void onMusicContentItemClick(View view, Contents contents, int adapterPosition);
    }

    OnMusicContentsItemClick musicContentslist;
    public void setOnMusicContentsItemClickListener(OnMusicContentsItemClick musiclistener){
        this.musicContentslist = musiclistener;
    }

    public AccountTypeMusicViewHolder(View itemView) {
        super(itemView);
        titleMusicView = (TextView)itemView.findViewById(R.id.title_type_music);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicContentslist!=null){
                    musicContentslist.onMusicContentItemClick(view, contents, getAdapterPosition());
                }
                Log.v("Music","test success");
            }
        });
    }
   Contents contents;
    public void setMusicContents(Contents contents){
        this.contents = contents;
        titleMusicView.setText(contents.getContentsText());
    }
//    public void setContents (Contents contents){
//        Log.w("뷰타입" , ""+contents.getContentsType());
//
//    }
}
