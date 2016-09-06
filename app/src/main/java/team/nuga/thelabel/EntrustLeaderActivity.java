package team.nuga.thelabel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import team.nuga.thelabel.adapter.EntrustLeaderAdapter;
import team.nuga.thelabel.data.User;

public class EntrustLeaderActivity extends AppCompatActivity {
    RecyclerView listView;
   EntrustLeaderAdapter entrustLeaderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrust_leader);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_entrust_leader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (RecyclerView)findViewById(R.id.recyclerview_entrust_leader);
        entrustLeaderAdapter = new EntrustLeaderAdapter();
        entrustLeaderAdapter.setOnAdapterItemClickListener(new EntrustLeaderAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, User user, int position) {

            }
        });
        listView.setAdapter(entrustLeaderAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        listView.setLayoutManager(manager);
//        initData();
    }
//    private void initData(){
//        Random r = new Random();
//        for(int i=0; i<5; i++){
//            User u = new User(i);
//            u.setUserName("name"+i);
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
}
