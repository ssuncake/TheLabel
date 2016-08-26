package team.nuga.thelabel.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.NotificationInviteAdapter;
import team.nuga.thelabel.data.InviteNotification;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class InviteNotificationFragment extends Fragment {
    RecyclerView listView;
    NotificationInviteAdapter adapter;


    public InviteNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_invite_notification, container, false);
        listView =(RecyclerView)view.findViewById(R.id.recyclerView_InviteNotification);
        adapter = new NotificationInviteAdapter();


        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(manager);
        listView.setAdapter(adapter);
        initData();
        return view;
    }

    public void initData(){

        // 더미 입니다.
        InviteNotification n = new InviteNotification();
        n.setInviteModeInviteLabel();
        Label l = new Label();
        l.setLabelName("팀NUGA");
        n.setSendLabel(l);
        adapter.addNotification(n);

        n = new InviteNotification();
        n.setInviteModeJoinRequest();
        User u = new User();
        u.setUserName("도재환");
        n.setJoinUser(u);

        adapter.addNotification(n);


    }

}
