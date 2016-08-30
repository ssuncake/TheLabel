package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class AccountTypeFourViewHolder extends RecyclerView.ViewHolder {
    TextView titlefourView;
    public AccountTypeFourViewHolder(View itemView) {
        super(itemView);
        titlefourView = (TextView)itemView.findViewById(R.id.title_type_four);
    }
}
