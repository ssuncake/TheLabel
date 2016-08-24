package team.nuga.thelabel.Adapther;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import team.nuga.thelabel.Fragment.InviteNotificationFragment;
import team.nuga.thelabel.Fragment.LikeNotificationFragment;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class NotificationViewpagerAdapter extends FragmentStatePagerAdapter {
    int numtabs;

    public NotificationViewpagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.numtabs = numtabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                LikeNotificationFragment tab1 = new LikeNotificationFragment();
                return tab1;
            case 1:
                InviteNotificationFragment tab2 = new InviteNotificationFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numtabs;
    }
}
