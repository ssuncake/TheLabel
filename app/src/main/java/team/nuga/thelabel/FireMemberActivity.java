package team.nuga.thelabel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.Random;

import team.nuga.thelabel.adapter.FireMemberListAdapter;
import team.nuga.thelabel.data.User;

public class FireMemberActivity extends AppCompatActivity {
    RecyclerView listView;
    FireMemberListAdapter firememberlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_fire_member);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (RecyclerView)findViewById(R.id.recyclerview_fire_member);
        firememberlistAdapter = new FireMemberListAdapter();
        firememberlistAdapter.setOnAdapterItemClickListener(new FireMemberListAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, User user, int position) {

            }
        });
        listView.setAdapter(firememberlistAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        listView.setLayoutManager(manager);
        initData();
    }
    private void initData(){
        Random r = new Random();
        for(int i=0; i<5; i++){
            User u = new User();
            u.setUserName("name"+i);
            firememberlistAdapter.add(u);
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

