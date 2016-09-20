package team.nuga.thelabel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.adapter.MessageListCursorAdapter;
import team.nuga.thelabel.data.SearchUser;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.gcm.MyGcmListenerService;
import team.nuga.thelabel.manager.DBManager;

public class MessageActivity extends AppCompatActivity {
    public static final String USER="dbuser";


    @BindView(R.id.toolBar_MessageMain)
    Toolbar toolbar;
    @BindView(R.id.editText_Message_InputText)
    EditText inputText;
    @BindView(R.id.recyclerView_Message_Main)
    RecyclerView message;

    @BindView(R.id.textView_title)
    TextView textView;

    @OnClick(R.id.button_Message_Send)
    public void clickSend(){
        String s = inputText.getText().toString();
        if(sw){
            DBManager.getInstance().addMessage(myUser,user,0,s);
            sw = !sw;
        }else{
            DBManager.getInstance().addMessage(myUser,user,1,s);
            sw = !sw;
        }
        updateMessage();
    }

    User myUser;
    User user;
    SearchUser searchUser;
    MessageListCursorAdapter adapter;

    boolean sw=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        myUser = (User)getIntent().getSerializableExtra(MainActivity.MAINUSER);

        user = (User)getIntent().getSerializableExtra(USER);
        searchUser = (SearchUser) getIntent().getSerializableExtra("user");

        adapter = new MessageListCursorAdapter();
        Log.w("Message Activity","이미지 경로 "+user.getImageUrl());

        adapter.setUserImagePath(user.getImageUrl());


        message.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        message.setLayoutManager(manager);

        message.scrollToPosition(adapter.getItemCount()-1);


//        textView.setText(searchUser.getSearchUserName());//유저 네임 확인용

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateMessage() {
        Cursor c = DBManager.getInstance().getChatMessage(user);
        adapter.changeCursor(c);
    }


    @Override
    protected void onStart() {
        super.onStart();
        updateMessage();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.changeCursor(null);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            User u = (User) intent.getSerializableExtra(MyGcmListenerService.EXTRA_CHAT_USER);
            if (u.getUserID() == user.getUserID()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateMessage();
                    }
                });
                intent.putExtra(MyGcmListenerService.EXTRA_RESULT, true);
            }
        }
    };
}
