package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class AccountTypeTwoViewHolder extends RecyclerView.ViewHolder {
    TextView titletwoView;
    public AccountTypeTwoViewHolder(View itemView) {
        super(itemView);
        titletwoView = (TextView)itemView.findViewById(R.id.title_type_two);
    }
}
