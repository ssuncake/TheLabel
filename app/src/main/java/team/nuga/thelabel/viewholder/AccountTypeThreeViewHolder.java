package team.nuga.thelabel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class AccountTypeThreeViewHolder extends RecyclerView.ViewHolder {
    TextView titlethreeView;
    public AccountTypeThreeViewHolder(View itemView) {
        super(itemView);
        titlethreeView = (TextView)itemView.findViewById(R.id.title_type_three);
    }
}
