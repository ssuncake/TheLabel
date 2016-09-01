package team.nuga.thelabel.viewholder;

import android.view.View;
import android.widget.TextView;

import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-08-31.
 */
public class AccountTypePictureViewHolder extends ParentContentsViewHolder {
    TextView titlePictureView;
    public AccountTypePictureViewHolder(View itemView) {
        super(itemView);
        titlePictureView = (TextView)itemView.findViewById(R.id.title_type_picture);
    }
//        public void setContents (Contents contents){
//        Log.w("뷰타입" , ""+contents.getContentsType());
//    }
}
