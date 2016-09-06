package team.nuga.thelabel.viewholder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.fragment.ProfileSettingFragment;

/**
 * Created by Tacademy on 2016-09-05.
 */
public class AccountTypeProfileViewHolder extends ParentContentsViewHolder {
    @BindView(R.id.textView_profile_username)
    TextView profileUsername;

    public AccountTypeProfileViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        ImageView settingImage = (ImageView)itemView.findViewById(R.id.imageView_setting);
        settingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), ProfileSettingFragment.class);
                (itemView.getContext()).startActivity(intent);
            }
        });
    }

    @Override
    public void applyData(User user, Contents contents) {
        if(user !=null) {
            profileUsername.setText(user.getUserName());
        }
    }
}
