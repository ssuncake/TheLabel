package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.PictureContents;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.data.YoutubeContents;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.ContentsRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserMainFragment extends Fragment {
    ContentsAdatper accountAdatper;
    User user;
//    MusicContents musicContents;
//    PictureContents pictureContents;
//    YoutubeContents youtubeContents;
    @BindView(R.id.textView_UserMain_UsrName)
    TextView userName;

    public UserMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_main, container, false);
        ButterKnife.bind(this,view);

        ContentsRequest contentsRequest = new ContentsRequest(getContext(),1,5);
        NetworkManager.getInstance().getNetworkData(contentsRequest, new NetworkManager.OnResultListener<NetworkResult<Contents[]>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<Contents[]>> request, NetworkResult<Contents[]> result) {
                Contents[] contentses = result.getData();
                for(Contents c : contentses){
                    Log.e("게시글 ID",""+ c.getContentsID());
                    Log.e("파일경로", ""+c.getContentsPath());
                    Log.e("파일타입", ""+c.getContentsType());
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<Contents[]>> request, int errorCode, String errorMessage, Throwable e) {
                Log.e("유저메인 실패",errorMessage);
            }
        });
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
        Contents contents ;
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
