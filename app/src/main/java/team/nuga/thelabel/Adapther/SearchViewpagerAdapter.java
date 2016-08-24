package team.nuga.thelabel.Adapther;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import team.nuga.thelabel.Fragment.LabelSearchFragment;
import team.nuga.thelabel.Fragment.UserSearchFragment;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class SearchViewpagerAdapter extends FragmentStatePagerAdapter {
    int numtabs;
    public SearchViewpagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.numtabs =numtabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                UserSearchFragment tab1 = new UserSearchFragment();
                return tab1;
            case 1:
                LabelSearchFragment tab2 = new LabelSearchFragment();
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
