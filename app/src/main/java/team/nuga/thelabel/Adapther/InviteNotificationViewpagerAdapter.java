package team.nuga.thelabel.Adapther;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InviteNotificationViewpagerAdapter extends Fragment {


    public InviteNotificationViewpagerAdapter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invite_notification_viewpager_adapter, container, false);
    }

}
