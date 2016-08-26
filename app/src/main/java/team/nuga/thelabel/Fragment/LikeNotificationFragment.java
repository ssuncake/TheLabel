package team.nuga.thelabel.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import team.nuga.thelabel.NotificationActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.adapther.NotificationLikeAdapter;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.LikeNotification;
import team.nuga.thelabel.data.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikeNotificationFragment extends Fragment {
    RecyclerView listView;
    NotificationLikeAdapter adapter;




    public LikeNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_like_notification, container, false);
        listView =(RecyclerView)view.findViewById(R.id.recyclerView_LikeNotification);
        adapter = new NotificationLikeAdapter();
        adapter.setOnAdapterItemClickListener(new NotificationLikeAdapter.onAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, LikeNotification notification, int position) {
                NotificationActivity parent = (NotificationActivity)getActivity();
                Toast.makeText(getActivity(), notification.getSender().getUserName()+"   !@!@     "+notification.getContents().getContentsTitle(), Toast.LENGTH_SHORT).show();
               parent.receiveNotification(notification);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(manager);
        listView.setAdapter(adapter);
        initData();

        return view;
    }

    public void initData(){

        // 더미 입니다.
        LikeNotification n = new LikeNotification();
        User u = new User();
        u.setUserName("선두원");
        Contents c = new Contents();
        c.setContentsTitle("오늘의 밥");
        n.setSender(u);
        n.setContents(c);
        adapter.addNotification(n);

        n = new LikeNotification();
        u = new User();
        u.setUserName("구혜빈");
        c = new Contents();
        c.setContentsTitle("래시가드뭐사지");
        n.setSender(u);
        n.setContents(c);
        adapter.addNotification(n);

        n = new LikeNotification();
        u = new User();
        u.setUserName("안드로이드");
        c = new Contents();
        c.setContentsTitle("갤럭시 노트 7");
        n.setSender(u);
        n.setContents(c);
        adapter.addNotification(n);
    }

}
