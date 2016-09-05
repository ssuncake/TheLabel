package team.nuga.thelabel.viewholder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.User;

/**
 * Created by Tacademy on 2016-09-05.
 */
public class AccountTypeProfileViewHolder extends ParentContentsViewHolder {
    @BindView(R.id.textView_profile_username)
    TextView profileUsername;

    public AccountTypeProfileViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    @Override
    public void applyData(User user, Contents contents) {
        profileUsername.setText(user.getUserName());
    }

}
