package team.nuga.thelabel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.adapter.MessageListCursorAdapter;
import team.nuga.thelabel.data.ChatContract;
import team.nuga.thelabel.data.NetworkResultGCM;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.gcm.MyGcmListenerService;
import team.nuga.thelabel.manager.DBManager;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.MessageRequest;

public class MessageActivity extends AppCompatActivity {
    public static final String USER="dbuser";


    @BindView(R.id.toolBar_MessageMain)
    Toolbar toolbar;
    @BindView(R.id.editText_Message_InputText)
    EditText inputText;
    @BindView(R.id.recyclerView_Message_Main)
    RecyclerView message;
    @BindView(R.id.textView_Message_title)
    TextView title;



    @OnClick(R.id.button_Message_Send)
    public void clickSend(){
        String sendText = inputText.getText().toString();
        DBManager.getInstance().addMessage(myUser,user, ChatContract.ChatMessage.TYPE_SEND,sendText);
        updateMessage();
        MessageRequest request = new MessageRequest(this,myUser.getUserID(),user.getUserID(),sendText);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResultGCM>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResultGCM> request, NetworkResultGCM result) throws ParseException {
                Log.e("메세지보내기",result.getSuccess()+"");
                inputText.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(inputText.getWindowToken(), 0);
            }

            @Override
            public void onFail(NetworkRequest<NetworkResultGCM> request, int errorCode, String errorMessage, Throwable e) {
                Log.e("메세지보내기",errorMessage+"");
            }
        });
    }

    User myUser;
    User user;
    MessageListCursorAdapter adapter;
    LocalBroadcastManager mLBM;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        mLBM = LocalBroadcastManager.getInstance(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        myUser = (User)getIntent().getSerializableExtra(MainActivity.MAINUSER);

        user = (User)getIntent().getSerializableExtra(USER);
        title.setText(user.getUserName());

        adapter = new MessageListCursorAdapter();
        Log.w("Message Activity","이미지 경로 "+user.getImageUrl());

        adapter.setUserImagePath(user.getImageUrl());


        message.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        message.setLayoutManager(manager);

        message.scrollToPosition(adapter.getItemCount()-1);




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
        message.scrollToPosition(adapter.getItemCount()-1);
    }


    @Override
    protected void onStart() {
        super.onStart();
        updateMessage();
        mLBM.registerReceiver(mReceiver, new IntentFilter(MyGcmListenerService.ACTION_CHAT));
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
