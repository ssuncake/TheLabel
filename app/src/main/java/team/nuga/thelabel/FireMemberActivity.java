package team.nuga.thelabel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import team.nuga.thelabel.adapter.FireMemberListAdapter;
import team.nuga.thelabel.data.Label;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.LabelGetFireMemberRequest;

public class FireMemberActivity extends AppCompatActivity {
    public static final String LOGTAG = "FireMemberActivity ";
    RecyclerView listView;
    FireMemberListAdapter firememberlistAdapter;
    Label label;
    User[] users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_fire_member);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        label =(Label) getIntent().getSerializableExtra(MainActivity.SELECTLABEL);
        if (label != null) {
            LabelGetFireMemberRequest request = new LabelGetFireMemberRequest(label.getLabelID());
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User[]>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<User[]>> request, NetworkResult<User[]> result) {
                    users = result.getUser();
                    Log.e(LOGTAG, "users" + users.length);
                    initData(users);
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<User[]>> request, int errorCode, String errorMessage, Throwable e) {
                    Log.e(LOGTAG, "LabelGetFireMemberRequest fail " + errorMessage);
                }
            });
        }


        listView = (RecyclerView) findViewById(R.id.recyclerview_fire_member);
        firememberlistAdapter = new FireMemberListAdapter();
        firememberlistAdapter.setOnAdapterItemClickListener(new FireMemberListAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, User user, int position) {

            }
        });
        listView.setAdapter(firememberlistAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

    }

    private void initData(User[] users) {

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
}

