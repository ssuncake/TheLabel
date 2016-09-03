package team.nuga.thelabel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.adapter.MemberListAdapter;
import team.nuga.thelabel.data.Member;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.request.MemberListRequest;

public class MemberListActivity extends AppCompatActivity {

    MemberListAdapter adapter;
    Member[] members;

    @BindView(R.id.recycler_MemberList)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_memberlist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        int id = getIntent().getIntExtra(MainActivity.LABELID,-1);

        if(id != -1){
            MemberListRequest request = new MemberListRequest(this,id);

            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<Member[]>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<Member[]>> request, NetworkResult<Member[]> result) {
                    members = result.getData();
                    setRecyclerView(members);
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<Member[]>> request, int errorCode, String errorMessage, Throwable e) {
                    Log.e("레이블 멤버리스트","받아오기 실패 "+errorMessage);
                }
            });
        }




    }

    public void setRecyclerView(Member[] members){
        adapter = new MemberListAdapter();
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        for(Member m : members){
            adapter.add(m);
        }
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
