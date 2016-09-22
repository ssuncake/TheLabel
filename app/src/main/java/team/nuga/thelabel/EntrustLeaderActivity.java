package team.nuga.thelabel;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.tsengvn.typekit.TypekitContextWrapper;

import team.nuga.thelabel.adapter.EntrustLeaderAdapter;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.NetworkResultLabelMemberManage;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.LabelGetFireMemberRequest;

public class EntrustLeaderActivity extends AppCompatActivity {

    public static final String LOGTAG = "EntrustActivity";
    RecyclerView listView;
    EntrustLeaderAdapter entrustLeaderAdapter;
    public static Label label;
    User[] users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrust_leader);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_entrust_leader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        label = (Label) getIntent().getSerializableExtra(MainActivity.SELECTLABEL);
        if (label != null) {
            LabelGetFireMemberRequest request = new LabelGetFireMemberRequest(label.getLabelID());
            Log.e(LOGTAG, "전달받은 레이블 id " + label.getLabelID());
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResultLabelMemberManage>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResultLabelMemberManage> request, NetworkResultLabelMemberManage result) {
                    users = result.getUsers();
                    Log.e(LOGTAG, "users" + users.length);
                    initData(users);
                }

                @Override
                public void onFail(NetworkRequest<NetworkResultLabelMemberManage> request, int errorCode, String errorMessage, Throwable e) {
                    Log.e(LOGTAG, "LabelGetFireMemberRequest fail " + errorMessage);
                }
            });
        }


        listView = (RecyclerView) findViewById(R.id.recyclerview_entrust_leader);
        entrustLeaderAdapter = new EntrustLeaderAdapter();
        entrustLeaderAdapter.setOnAdapterItemClickListener(new EntrustLeaderAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, User user, int position) {

            }
        });
        listView.setAdapter(entrustLeaderAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);
    }

    private void initData(User[] users) {
        for(User u : users){
            Log.e(LOGTAG,"add user = "+u.getUserName());
            entrustLeaderAdapter.add(u);
        }
    }

//    private void initData() {
//        Random r = new Random();
//        for (int i = 0; i < 5; i++) {
//            User u = new User();
//            u.setUserName("name" + i);
//            entrustLeaderAdapter.add(u);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
