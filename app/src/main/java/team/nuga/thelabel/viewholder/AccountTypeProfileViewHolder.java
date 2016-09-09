package team.nuga.thelabel.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    public  interface OnSettingImageClick{  // SettingImage 리스너
        void onSettingImageClick(View view, int adapterPosition);
    }

    OnSettingImageClick imageSettinglist;

    public void setOnSettingImageClick(OnSettingImageClick imageSettinglist){
        this.imageSettinglist = imageSettinglist;
    }

    public AccountTypeProfileViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        ImageView settingimageView = (ImageView)itemView.findViewById(R.id.imageView_setting);
        settingimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageSettinglist!=null){
                    imageSettinglist.onSettingImageClick(view, getAdapterPosition());
                }
            }
        });
    }

    @Override
    public void applyData(User user, Contents contents) {
        if(user !=null) {
            profileUsername.setText(user.getUserName());
            Glide.with(userProfilePicture.getContext())
                    .load(user.getImageUrl())
                    .transform(new RoundImageTransform(userProfilePicture.getContext()))
                    .into(userProfilePicture);
        }
    }
}
