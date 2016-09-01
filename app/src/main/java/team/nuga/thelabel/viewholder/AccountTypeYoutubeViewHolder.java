package team.nuga.thelabel.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;

/**
 * Created by Tacademy on 2016-08-31.
 */
public class AccountTypeYoutubeViewHolder extends ParentContentsViewHolder {
    TextView titleYoutubeView;

     public interface OnYoutubeContentsItemClick{
        void onYoutubeContentsItemClick(View view, Contents contents, int adapterPosition);
    }

    OnYoutubeContentsItemClick youtubeContentslist;
    public void setOnYoutubeContentsItemClickListener(OnYoutubeContentsItemClick youtubelistener){
        this.youtubeContentslist = youtubelistener;
    }

    public AccountTypeYoutubeViewHolder(View itemView) {
        super(itemView);
        titleYoutubeView = (TextView)itemView.findViewById(R.id.title_type_youtube);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(youtubeContentslist!=null){
                    youtubeContentslist.onYoutubeContentsItemClick(view , contents, getAdapterPosition());
                }
                Log.v("Youtube","test success");
            }
        });
    }
    Contents contents;
    public void setYoutubeContents(Contents contents){
        this.contents = contents;
        titleYoutubeView.setText(contents.getContentsText());
    }
}
