package team.nuga.thelabel.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.MessageActivity;
import team.nuga.thelabel.R;
import team.nuga.thelabel.data.ChatContract;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.DBManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageListFragment extends Fragment {

    User user;


    @BindView(R.id.editText_messagedebug)
    EditText editText;
    @BindView(R.id.listView_MessageList)
    ListView listView;

    SimpleCursorAdapter adapter;

    @OnItemClick(R.id.listView_MessageList)
    public void onItemClick(int position, long id) {
        Cursor cursor = (Cursor)listView.getItemAtPosition(position);
        User user = new User();
        user.setUserID((int)(cursor.getLong(cursor.getColumnIndex(ChatContract.ChatUser.OTHER_ID))));

        user.setUserName(cursor.getString(cursor.getColumnIndex(ChatContract.ChatUser.OTHER_NAME)));
        Intent intent = new Intent(getContext(),MessageActivity.class);
        intent.putExtra(MessageActivity.USER, user);
        intent.putExtra(MainActivity.MAINUSER, this.user);
        startActivity(intent);
    }

    @OnClick(R.id.button_messagedebug)
    public void addUser(){
        String un = editText.getText().toString()+" 테스트인원";

        User user = new User();
        user.setUserName(un);
        user.setUserEmail("a@a.a");
        user.setUserID(Integer.parseInt(editText.getText().toString()));
        Log.e("더미유저추가","유저 이름 "+user.getUserName());
        DBManager.getInstance(this.user).addMessage(this.user,user,1,user.getUserName()+1+" 으아아아");
        DBManager.getInstance(this.user).addMessage(this.user,user,0,"오마나세상에");


    }

    public MessageListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User)getArguments().getSerializable(MainActivity.MAINUSER);
        Log.e("메세지 리스트 프래그먼트 ","프래그먼트 전달 유저 : " +user.getLongUserID());
        String[] from = {ChatContract.ChatUser.OTHER_NAME};
        int[] to = {R.id.textView_MessageList_UserName};
        adapter = new SimpleCursorAdapter(getContext(), R.layout.view_messagelist, null, from, to, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_message_list, container, false);
        ButterKnife.bind(this,view);
        listView.setAdapter(adapter);

        // 더미세팅 부분이빈다.


        User user = new User();
        user.setUserName("힣핳훟");
        user.setUserEmail("a@a.a");
        user.setUserID(900);

//        DBManager.getInstance(this.user).addUser(user);
//        DBManager.getInstance(this.user).addMessage(this.user,user,1,user.getUserName()+1+" 으아아아");
//        DBManager.getInstance(this.user).addMessage(this.user,user,0,"오마나세상에");
        return view;
    }


    private void initData(){

//
//        user = new User();
//        user.setUserName("두원");
//        user.setUserEmail("b@b.b");
//        user.setUserID(602);
//        DBManager.getInstance().addUser(user);
//
//        user = new User();
//        user.setUserName("혜빈");
//        user.setUserEmail("c@c.c");
//        user.setUserID(702);
//        DBManager.getInstance().addUser(user);



    }


    @Override
    public void onStart() {
        super.onStart();
        Cursor c = DBManager.getInstance(user).getChatUser();
        adapter.changeCursor(c);
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.changeCursor(null);
    }


}
