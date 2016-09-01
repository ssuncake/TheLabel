package team.nuga.thelabel.viewholder;

import android.view.View;
import android.widget.TextView;

import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-08-31.
 */
public class AccountTypeMusicViewHolder extends ParentContentsViewHolder {
    TextView titleMusicView;
    public AccountTypeMusicViewHolder(View itemView) {
        super(itemView);
        titleMusicView = (TextView)itemView.findViewById(R.id.title_type_music);
    }
//    public void setContents (Contents contents){
//        Log.w("뷰타입" , ""+contents.getContentsType());
//
//    }
}
