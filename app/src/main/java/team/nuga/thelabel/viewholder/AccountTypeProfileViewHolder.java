package team.nuga.thelabel.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.RoundImageTransform;
import team.nuga.thelabel.data.User;

/**
 * Created by Tacademy on 2016-09-05.
 */
public class AccountTypeProfileViewHolder extends ParentContentsViewHolder {
    @BindView(R.id.textView_profile_username)
    TextView profileUsername;
    @BindView(R.id.imageView_user_profile)
    ImageView userProfilePicture;

    public AccountTypeProfileViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        ImageView settingImage = (ImageView)itemView.findViewById(R.id.imageView_setting);
        settingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), MainActivity.class);
                ((Activity)itemView.getContext()).startActivity(intent);
            }
        });
    }

    @Override
    public void applyData(User user, Contents contents) {
        if(user !=null) {
            profileUsername.setText(user.getUserName());
            Log.e("프로필 사진",user.getImageUrl());
            Glide.with(userProfilePicture.getContext())
                    .load("http://blogpfthumb.phinf.naver.net/20131004_253/dlsdnd345_1380849789799ScRyM_JPEG/673_405960459519395_780704392_n.jpg")
                    .transform(new RoundImageTransform(userProfilePicture.getContext()))
                    .into(userProfilePicture);
        }
    }
}
