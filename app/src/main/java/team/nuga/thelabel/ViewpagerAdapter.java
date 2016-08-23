package team.nuga.thelabel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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

        }
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
