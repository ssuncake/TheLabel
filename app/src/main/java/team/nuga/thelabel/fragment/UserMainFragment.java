package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.ContentsAdatper;
import team.nuga.thelabel.data.Contents;
import team.nuga.thelabel.data.MusicContents;
import team.nuga.thelabel.data.PictureContents;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.data.YoutubeContents;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserMainFragment extends Fragment {
    ContentsAdatper accountAdatper;
    User user;
    MusicContents musicContents;
    PictureContents pictureContents;
    YoutubeContents youtubeContents;
    @BindView(R.id.textView_UserMain_UsrName)
    TextView userName;

    public UserMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_main, container, false);
        ButterKnife.bind(this,view);
        user = (User)getArguments().getSerializable(MainActivity.MAINUSER);
        userName.setText(user.getUserName()+" 의 계정입니다.");
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_user_main);
        accountAdatper = new ContentsAdatper();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(accountAdatper);
        initData();
        return view;
    }
    public void initData(){
        Contents contents;
       Random r = new Random();
        for(int i=0;i<10;i++)
       {
           if(r.nextInt()%3==0){
               contents = new MusicContents();
               accountAdatper.add(contents);
           }else if(r.nextInt()%3==1){
               contents = new PictureContents();
               accountAdatper.add(contents);
           }else{
               contents = new YoutubeContents();
               accountAdatper.add(contents);
           }
       }
    }
}
