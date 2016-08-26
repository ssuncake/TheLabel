package team.nuga.thelabel.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import team.nuga.thelabel.adapter.MessageAdapter;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.MessageActivity;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageListFragment extends Fragment {

    RecyclerView listView;
    MessageAdapter messageAdapter;

    public MessageListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_message_list, container, false);
        listView =(RecyclerView)view.findViewById(R.id.recyclerview_message);
        messageAdapter = new MessageAdapter();
        messageAdapter.setOnAdapterItemClickListener(new MessageAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, User user, int position) {
                Intent intent = new Intent(getActivity(),MessageActivity.class);
                intent.putExtra("message","친구"+position+"과의 채팅화면");
                startActivity(intent);

//                switch(position){
//                    case 0:
//                        Intent intent = new Intent(getActivity(), MessageActivity.class);
//                        startActivity(intent);
//                        break;
//                    case 1:
//                        Intent intent1 = new Intent(getActivity(), MessageActivity.class);
//                        startActivity(intent1);
//                        break;
//                    case 2:
//                        Intent intent2 = new Intent(getActivity(), MessageActivity.class);
//                        startActivity(intent2);
//                        break;
//                }
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(manager);
        listView.setAdapter(messageAdapter);
        initData();
        return view;
    }
    int [] userphoto ={R.drawable.profile_girl, R.drawable.profile_girl,R.drawable.profile_girl};

    private void initData(){
        Random r = new Random();
        for (int i = 0; i<3; i++){
            User u = new User();
            u.setUserName("친구 "+i);
            u.setUserProfileImage(getResources().getDrawable(R.drawable.profile_girl));
            messageAdapter.add(u);
        }

    }


}
