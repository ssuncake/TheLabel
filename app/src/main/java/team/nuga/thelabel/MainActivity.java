package team.nuga.thelabel;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.Fragment.MainFragment;
import team.nuga.thelabel.Fragment.MessageListFragment;
import team.nuga.thelabel.Fragment.MyLikeContentsFragment;
import team.nuga.thelabel.Fragment.ProfileSettingFragment;
import team.nuga.thelabel.Fragment.SettingFragment;
import team.nuga.thelabel.Fragment.UploadFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.drawer)
    NavigationView drawer;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    private FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (null != ab) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowCustomEnabled(true);
            ab.setDisplayShowTitleEnabled(true);
            ab.setTitle("     The label ");

        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        drawer.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager(); // 프래그먼트 매니저를 얻어옴.

        getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MainFragment()).commit();


    }

    boolean backButtonClicked = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (backButtonClicked == true) {
                Toast.makeText(MainActivity.this, "앱이 종료되었습니다.", Toast.LENGTH_SHORT).show();
                super.onBackPressed();
            }
            if (backButtonClicked == false) {
                Toast.makeText(MainActivity.this, "한번 더 누르면 앱이 종료됩니다!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                backButtonClicked = true;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;  //툴바 메뉴 - 알림,검색 생성
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig); //드로어 토글 체인지
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) { //드로어 버튼 클릭 시 드로어 실행
            return true;
        }
        switch (item.getItemId()) {
            case R.id.toolbar_notification:  //툴바 상단 노티피케이션 메뉴 클릭시 해당 액티비티 띄움
                Toast.makeText(MainActivity.this, "Move Notification Activity... ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.toolbar_search:    //툴바 상단 검색 메뉴 클릭시 해당 액티비티 띄움
                Toast.makeText(MainActivity.this, "Move Search Activity... ", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {  // 네미게이션 드로어 메뉴 선택시 해당 프래그먼트로 이동
        int id = item.getItemId();

        if (id == R.id.drawer_upload) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new UploadFragment()).commit();//업로드 메뉴 선택시 업로드 프래그먼트로 이동
        } else if (id == R.id.drawer_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new ProfileSettingFragment()).commit();
        } else if (id == R.id.drawer_message) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MessageListFragment()).commit();
        } else if (id == R.id.drawer_likeContents) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MyLikeContentsFragment()).commit();
        } else if (id == R.id.drawer_setting) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new SettingFragment()).commit();
        } else if (id == R.id.drawer_main) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MainFragment()).commit();
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START); // 드로어 닫음
        return true;
    }


}