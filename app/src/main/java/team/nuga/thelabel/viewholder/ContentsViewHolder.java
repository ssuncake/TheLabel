package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class ContentsViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public ImageView imageView;


    public ContentsViewHolder(View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.textView_cardView_profileId);
//        imageView = (ImageView)itemView.findViewById(R.id.cardView_profileImage);
    }
}
