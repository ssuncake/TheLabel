package team.nuga.thelabel.youtube;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.adapter.NotificationInviteAdapter;

public class JoinReqeustManageActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView_LabelJoin)
    RecyclerView mRecyclerView;
    NotificationInviteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_reqeust_manage);
        ButterKnife.bind(this);

        mAdapter = new NotificationInviteAdapter();
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

    }
}
