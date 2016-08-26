package team.nuga.thelabel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import team.nuga.thelabel.fragment.LabelSearchFragment;
import team.nuga.thelabel.fragment.UserSearchFragment;


/**
 * Created by Tacademy on 2016-08-24.
 */
public class SearchViewpagerAdapter extends FragmentStatePagerAdapter {
    int numtabs;

    public SearchViewpagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.numtabs =tabCount;
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
