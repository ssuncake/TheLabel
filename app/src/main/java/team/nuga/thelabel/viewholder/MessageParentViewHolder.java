package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Tacademy on 2016-09-05.
 */
public abstract class MessageParentViewHolder extends RecyclerView.ViewHolder {
    public MessageParentViewHolder(View itemView) {
        super(itemView);
    }

    abstract public void setMessage(String message,String date);
    abstract public void setImage(String url);
}
