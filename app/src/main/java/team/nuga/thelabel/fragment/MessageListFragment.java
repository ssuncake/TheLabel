package team.nuga.thelabel.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.MessageActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.MessageMemberAdapter;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.gcm.MyGcmListenerService;
import team.nuga.thelabel.manager.DBManager;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.GetUserImageByIdRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageListFragment extends Fragment {

    User user;
    User tempotherUser;
    LocalBroadcastManager mLBM;


    @BindView(R.id.editText_messagedebug)
    EditText editText;


    @BindView(R.id.recyclerView_MessageList)
    RecyclerView recyclerView;

    MessageMemberAdapter adapter;


    @OnClick(R.id.button_messagedebug)
    public void addUser(){
       final int otherId  = Integer.parseInt(editText.getText().toString());

        GetUserImageByIdRequest request = new GetUserImageByIdRequest(getActivity(),otherId);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                tempotherUser = result.getUser();
                tempotherUser.setUserID(otherId);
                Log.e("MessageListFragment","아이디로 유저네임 이미지 얻어오기디버그용 "+tempotherUser.getUserName()+" // "+tempotherUser.getImageUrl());


                // 더미메세지
//                DBManager.getInstance().setMainUser(user);
                DBManager.getInstance().addMessage(user,tempotherUser,0," 더미메세지입니다.");
                updateUser();
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                Log.e("MessageListFragment","protocol no.7 error : "+errorMessage);
            }
        });





    }

    public MessageListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User)getArguments().getSerializable(MainActivity.MAINUSER);
        Log.e("메세지 리스트 프래그먼트 ","프래그먼트 전달 유저 : " +user.getLongUserID());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_message_list, container, false);
        ButterKnife.bind(this,view);
        mLBM = LocalBroadcastManager.getInstance(getActivity());
        adapter = new MessageMemberAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.setOnAdapterItemClickListener(new MessageMemberAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(View view,User suser, int position) {
                Toast.makeText(getActivity(), position+"클릭", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),MessageActivity.class);
                intent.putExtra(MessageActivity.USER, suser);
                intent.putExtra(MainActivity.MAINUSER, user);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
//        DBManager.getInstance().setMainUser(user);
        Cursor c = DBManager.getInstance().getChatUser();
        adapter.changeCursor(c);
        mLBM.registerReceiver(mReceiver, new IntentFilter(MyGcmListenerService.ACTION_CHAT));
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.changeCursor(null);
    }

    private void updateUser() {
//        DBManager.getInstance().setMainUser(user);
        Cursor c = DBManager.getInstance().getChatUser();
        adapter.changeCursor(c);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            User u = (User) intent.getSerializableExtra(MyGcmListenerService.EXTRA_CHAT_USER);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUser();
                    }
                });
                intent.putExtra(MyGcmListenerService.EXTRA_RESULT, true);

        }
    };


}
