package team.nuga.thelabel.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;

/**
 * Created by Tacademy on 2016-08-31.
 */
public class AccountTypePictureViewHolder extends ParentContentsViewHolder {
    TextView titlePictureView;
    public interface OnPictureContentsItemClick{
        void onPictureContentsItemClick(View view, Contents contents, int adapterPosition);
    }

    OnPictureContentsItemClick pictureContentslist;
    public void setOnPictureContentsItemClickListener(OnPictureContentsItemClick picturelistener){
        this.pictureContentslist = picturelistener;
    }
    public AccountTypePictureViewHolder(View itemView) {
        super(itemView);
        titlePictureView = (TextView)itemView.findViewById(R.id.title_type_picture);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pictureContentslist!=null){
                   pictureContentslist.onPictureContentsItemClick(view, contents, getAdapterPosition());
                }
                Log.v("Picture","test success");
            }
        });
    }
    Contents contents;
    public void setPictureContent(Contents contents){
        this.contents = contents;
        titlePictureView.setText(contents.getContentsText());
    }
}
