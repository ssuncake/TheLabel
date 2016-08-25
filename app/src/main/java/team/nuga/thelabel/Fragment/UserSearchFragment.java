package team.nuga.thelabel.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import team.nuga.thelabel.Adapther.SearchUserResultAdapter;
import team.nuga.thelabel.Data.User;
import team.nuga.thelabel.OtherUserActivity;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserSearchFragment extends Fragment {
    RecyclerView listView;
    SearchUserResultAdapter adapter;

    public UserSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_search, container, false);
        listView =(RecyclerView)view.findViewById(R.id.recyclerview_search);
        adapter = new SearchUserResultAdapter();
        adapter.setOnAdapterItemClickListener(new SearchUserResultAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, User user, int position) {
                Intent intent = new Intent(getActivity(),OtherUserActivity.class);
                intent.putExtra("name",position+"님의 계정입니다.");
                startActivity(intent);
//                switch(position){
//                    case 0:
//                        Intent intent = new Intent(getActivity(), OtherUserActivity.class);
//                        startActivity(intent);
//                    case 1:
//                        Intent intent1 = new Intent(getActivity(), OtherUserActivity.class);
//                        startActivity(intent1);
//                    case 2:
//                        Intent intent2 = new Intent(getActivity(), OtherUserActivity.class);
//                        startActivity(intent2);
//                }
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(manager);
        listView.setAdapter(adapter);
        initData();
        return view;
    }


    private void initData(){
        Random r = new Random();
        for (int i = 0; i<3; i++){
            User u = new User();
            u.setUserName("name "+i);
            u.setUserProfileImage(getResources().getDrawable(R.drawable.profile_girl));
            adapter.add(u);
        }

    }
}
