package team.nuga.thelabel;

import android.app.Activity;
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
import team.nuga.thelabel.fragment.MainFragment;
import team.nuga.thelabel.fragment.MessageListFragment;
import team.nuga.thelabel.fragment.MyLikeContentsFragment;
import team.nuga.thelabel.fragment.ProfileSettingFragment;
import team.nuga.thelabel.fragment.SettingFragment;
import team.nuga.thelabel.fragment.UploadFragment;
import team.nuga.thelabel.data.LikeNotification;
import team.nuga.thelabel.data.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String MAINUSER = "MainUser"; // 다른 프래그먼트 및 액티비디로 이동시킬 사용자 유저정보의 번들태그
    public static final String TABINDEX = "tabindex";

    public static final int LABELTAB = 1;
    public static final int USERTAB = 2;

    private static final int REQUEST_LIKENOTIFICATION = 200;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.drawer)
    NavigationView drawer;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    private FragmentManager mFragmentManager;

    private User mainUser;
    private Bundle dummyBundle;


    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);



        actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("     The label ");

        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        drawer.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager(); // 프래그먼트 매니저를 얻어옴.


        //// 가짜  User 데이터를 만들어서 메인프레그먼트로 넘김니다.
        mainUser = new User();
        mainUser.setUserName("이정호");
        dummyBundle = new Bundle();
        goMainFragment(0);

//        drawer_nickname = (TextView)findViewById(R.id.TextView_drawer_nickname) ;
//        drawer_nickname.setText(dummyUser.getUserName());
    }

    boolean backButtonClicked = false;
//s
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
                startActivityForResult(intent,REQUEST_LIKENOTIFICATION);
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
            actionBar.setTitle("업로드");
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new UploadFragment()).commit();//업로드 메뉴 선택시 업로드 프래그먼트로 이동
        } else if (id == R.id.drawer_profile) {
            actionBar.setTitle("Toolbar_Profile");
            Bundle b = new Bundle();
            b.putSerializable(MainActivity.MAINUSER,mainUser);
            ProfileSettingFragment profileSettingFragment = new ProfileSettingFragment();
            profileSettingFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, profileSettingFragment).commit();
        } else if (id == R.id.drawer_message) {
            actionBar.setTitle("Toolbar_Message");
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MessageListFragment()).commit();
        } else if (id == R.id.drawer_likeContents) {
            actionBar.setTitle("Toolbar_LikePage");
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MyLikeContentsFragment()).commit();
        } else if (id == R.id.drawer_setting) {
            actionBar.setTitle("Toolbar_Setting");
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new SettingFragment()).commit();
        } else if (id == R.id.drawer_main) {
            goMainFragment(0);

        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START); // 드로어 닫음
        return true;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_LIKENOTIFICATION){
            if(resultCode == Activity.RESULT_OK){
                LikeNotification notification =(LikeNotification) data.getSerializableExtra(NotificationActivity.RESULT_NOTIFICATION);
                goMainFragment(2);
            }
        }
    }

    public void goMainFragment(int tabIndex) // 메인 프래그먼트로 이동하며 인자로 받은 값 0~2탭으로 바로이동시켜줍니다.
    {
        actionBar.setTitle("Toolbar_AppTitle");
        dummyBundle.putSerializable(MAINUSER,mainUser);
        if(tabIndex != 0){
            dummyBundle.putInt(TABINDEX,tabIndex);
        }
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(dummyBundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, mainFragment).commit();
    }



}