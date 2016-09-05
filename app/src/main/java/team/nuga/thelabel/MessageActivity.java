package team.nuga.thelabel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.adapter.MessageMainAdapter;
import team.nuga.thelabel.data.Message;

public class MessageActivity extends AppCompatActivity {
    @BindView(R.id.toolBar_MessageMain)
    Toolbar toolbar;
    @BindView(R.id.editText_Message_InputText)
    EditText inputText;
    @BindView(R.id.recyclerView_Message_Main)
    RecyclerView message;

    @OnClick(R.id.button_Message_Send)
    public void clickSend(){

    }

    MessageMainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new MessageMainAdapter();
        message.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        message.setLayoutManager(manager);

        DummyMessage();

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

    public void DummyMessage(){
        Message m = new Message();
        m.setMessageType(Message.ME);
        m.setMessage("안녕하세요");
        m.setDate("99999");
        adapter.add(m);

        m = new Message();
        m.setMessageType(Message.ME);
        m.setMessage("안녕하세요");
        m.setDate("99999");
        adapter.add(m);
        m = new Message();
        m.setMessageType(Message.OTHER);
        m.setMessage("안녕");
        m.setDate("88888");
        adapter.add(m);

        m = new Message();
        m.setMessageType(Message.ME);
        m.setMessage("zzzzzz");
        m.setDate("22");
        adapter.add(m);

        m = new Message();
        m.setMessageType(Message.OTHER);
        m.setMessage("ㅎㅎㅎㅎㅎ");
        m.setDate("222233");
        adapter.add(m);

        m = new Message();
        m.setMessageType(Message.OTHER);
        m.setMessage("ㄴㄴㄴㄴ");
        m.setDate("2525");
        adapter.add(m);

        m = new Message();
        m.setMessageType(Message.ME);
        m.setMessage("ㄴㅇㄹㄴㄹ");
        m.setDate("99999");
        adapter.add(m);

        m = new Message();
        m.setMessageType(Message.DATE);
        adapter.add(m);

        m = new Message();
        m.setMessageType(Message.OTHER);
        m.setMessage("ㄴㄴㄴㄴ");
        m.setDate("2525");
        adapter.add(m);

    }
}
