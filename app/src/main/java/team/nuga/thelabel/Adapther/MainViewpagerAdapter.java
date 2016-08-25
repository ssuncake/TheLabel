package team.nuga.thelabel.Adapther;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import team.nuga.thelabel.Data.User;
import team.nuga.thelabel.Fragment.LabelContainerFragment;
import team.nuga.thelabel.Fragment.NewsFeedFragment;
import team.nuga.thelabel.Fragment.UserMainFragment;

/**
 * Created by Tacademy on 2016-08-23.
 */
public class MainViewpagerAdapter extends FragmentStatePagerAdapter {
    int numtabs;
    Bundle bundle;
    User user;
    public MainViewpagerAdapter(FragmentManager fm, int numtabs, User user) {
        super(fm);
        this.numtabs = numtabs;
        this.user = user;
        bundle = new Bundle();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                NewsFeedFragment tab1 = new NewsFeedFragment();
//              bundle.putSerializable("dummyUser",user);
//               tab1.setArguments(bundle);
                return tab1;
            case 1:
                LabelContainerFragment tab2 = new LabelContainerFragment();
                bundle.putSerializable("dummyUser",user);
                tab2.setArguments(bundle);
                return tab2;
            case 2:
                UserMainFragment tab3  = new UserMainFragment();
//                bundle.putSerializable("dummyUser",user);
//                tab3.setArguments(bundle);
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
