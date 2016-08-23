package team.nuga.thelabel.Adapther;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import team.nuga.thelabel.Fragment.LabelMakeFragment;
import team.nuga.thelabel.Fragment.NewsFeedFragment;
import team.nuga.thelabel.Fragment.UserMainFragment;

/**
 * Created by Tacademy on 2016-08-23.
 */
public class ViewpagerAdapter extends FragmentStatePagerAdapter {
    int numtabs;
    public ViewpagerAdapter(FragmentManager fm, int numtabs) {
        super(fm);
        this.numtabs = numtabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                NewsFeedFragment tab1 = new NewsFeedFragment();
                return tab1;
            case 1:
                LabelMakeFragment tab2 = new LabelMakeFragment();
                return tab2;
            case 2:
                UserMainFragment tab3  = new UserMainFragment();
                return tab3;
            default:
                return null;
            }
    }

    @Override
    public int getCount() {
        return numtabs;
    }
}
