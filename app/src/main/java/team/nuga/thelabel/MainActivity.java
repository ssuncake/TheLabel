package team.nuga.thelabel;

import android.app.Activity;
import android.content.Context;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.adapter.ContentsAdatper;
import team.nuga.thelabel.data.LikeNotification;
import team.nuga.thelabel.data.NetworkResult;
import team.nuga.thelabel.data.RoundImageTransform;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.fragment.MainFragment;
import team.nuga.thelabel.fragment.MessageListFragment;
import team.nuga.thelabel.fragment.ProfileSettingFragment;
import team.nuga.thelabel.fragment.SettingFragment;
import team.nuga.thelabel.fragment.UploadFragment;
import team.nuga.thelabel.manager.DBManager;
import team.nuga.thelabel.manager.NetworkManager;
import team.nuga.thelabel.manager.NetworkRequest;
import team.nuga.thelabel.manager.PropertyManager;
import team.nuga.thelabel.request.LoginRequest;
import team.nuga.thelabel.request.ProfileGetRequest;

import static team.nuga.thelabel.R.id.drawer_layout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String MAINUSER = "MainUser";                   // 다른 프래그먼트 및 액티비디로 이동시킬 사용자 유저정보의 번들태그
    public static final String PROFILEUSER = "ProfileUser";             // 다른 프래그먼트 및 액티비디로 이동시킬 사용자 유저정보의 번들태그
    public static final String MAINUSERINLABELS = "MainUserInLabels";
    public static final String SELECTLABEL = "SelectLabel";             // 선택된 레이블로 이동 또는 세팅할때 이용 레이블객체를 직접 이동
    public static final String LABELID = "labelId";                     // 레이블의 아이디만 전달할때 이용

    public static final String NEWLEADER = "NewLeader";
    public static final String TABINDEX = "tabindex";
    public static final String CONTAINERFRAGMENTTAG = "LabelContainer";


    public static final int REQUEST_LIKENOTIFICATION = 200;
    public static final int REQUEST_UPLOAD = 300;
    public static final int REQUEST_MUSICUPLOAD = 310;
    public static final int REQUEST_MAKELABEL = 400;
    public static final int REQUEST_SETTINGLABEL = 410;


    @BindView(R.id.mainactivity_toolbar)
    Toolbar toolbar;
    @BindView(drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.drawer)
    NavigationView drawer;

    private TextView headerUserName;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    private FragmentManager mFragmentManager;

    private User mainUser;
    private Bundle bundle;

    private AppFunction appFunction;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (mainUser == null) {             //스플래시에서 유저정보를 못가져왔을경우 프로퍼티 데이터로 다시 로그인
            String email = PropertyManager.getInstance().getEmail();
            if (!TextUtils.isEmpty(email)) {  //앱데이터의 email이 있을경우에 로그인.
                String password = PropertyManager.getInstance().getPassword();
                String regid = PropertyManager.getInstance().getRegistrationId();
                if (Debug.debugmode) Log.e("로그인관련", "레지아이디 " + regid);
                LoginRequest request = new LoginRequest(this, email, password, regid);
                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                        User getLoginUser = result.getUser();
                        DBManager.getInstance().setMainUser(getLoginUser);

                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
            } else { // 앱에 저장된 email정보가 없을경우. = 로그인 기록 없음
                Toast.makeText(this, "로그인이 필요한 서비스 입니다.", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(this, LoginActivity.class); //재 로그인,회원가입 하도록.
                startActivity(loginIntent);
            }
        }
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        appFunction = new AppFunction(this);


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


        User user = (User) getIntent().getSerializableExtra("LoginUser");
        if (user == null) {
            mainUser = new User();
            mainUser.setUserName("이정호");
        } else {
            mainUser = user;
            Log.w("MainActivity", "userId= " + user.getUserID() + "userName = " + user.getUserName() + "user impath =" + user.getUserName());
        }
        Toast.makeText(MainActivity.this, "로그인 유저 : " + user.getUserName(), Toast.LENGTH_SHORT).show();
        //// 가짜  User 데이터를 만들어서 메인프레그먼트로 넘김니다.

        bundle = new Bundle();
        goMainFragment(MainFragment.NEWSFEEDTAB);

        setheaderView();//헤더뷰 설정


    }

    public void setheaderView() {// 헤더뷰 관련 설정
        String[] citylist = getResources().getStringArray(R.array.cityList);
        String[] genrelist = getResources().getStringArray(R.array.genrelist);
        String[] positionlist = getResources().getStringArray(R.array.positionlist);
        View headerView = drawer.inflateHeaderView(R.layout.drawer_header);
        TextView position = (TextView) headerView.findViewById(R.id.textView_MainHeader_position);
        position.setText(positionlist[mainUser.getPostition() - 1]);
        headerUserName = (TextView) headerView.findViewById(R.id.textView_MainHeader_Name);
        TextView headerUserEmail = (TextView) headerView.findViewById(R.id.textView_MainHeaderEmail);
        headerUserEmail.setText(mainUser.getUserEmail());
        headerUserName.setText(mainUser.getUserName());
        TextView headerUserGenre = (TextView) headerView.findViewById(R.id.textView_MainHeader_Genre);
        ImageView headerUserProfile = (ImageView) headerView.findViewById(R.id.imageView_MainHeader_Profile);
        headerUserGenre.setText("#" + genrelist[mainUser.getGenre() - 1] + ", #" + citylist[mainUser.getCity()]);
        Glide.with(this)
                .load(mainUser.getImageUrl())
                .transform(new RoundImageTransform(this))
                .into(headerUserProfile);
    }

    public static int currentViewPage = 0;
    public static final int DRAWER = 5;
    public static final int LABEL_MAIN = 1;
    int appFinCount = 0;

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            switch (currentViewPage) {
                case LABEL_MAIN:
                    goMainFragment(1);
                    appFinCount++;
                    if (appFinCount > 1) appFunction.appFinished();// 레이블 셀렉에서 나오면 앱종료기능
                    break;
                case DRAWER:
                    goMainFragment(0);
                    // 드로어로 들어간 메뉴에서 나오도록..
                    appFinCount++;
                    if (appFinCount > 1) appFunction.appFinished();
                    break;
                default:
                    appFinCount = 0;
                    appFunction.appFinished();
                    break;
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
                Intent intent = new Intent(this, NotificationActivity.class);
                startActivityForResult(intent, REQUEST_LIKENOTIFICATION);
                break;
            case R.id.toolbar_search:    //툴바 상단 검색 메뉴 클릭시 해당 액티비티 띄움
                intent = new Intent(this, SearchActivity.class);
                intent.putExtra(MAINUSER, mainUser);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ContentsAdatper contentsAdatper;
    User profile_getUser;

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {  // 네미게이션 드로어 메뉴 선택시 해당 프래그먼트로 이동
        int id = item.getItemId();

        if (id == R.id.drawer_upload) {
            currentViewPage = DRAWER;
            actionBar.setTitle("업로드");
            Bundle b = new Bundle();
            b.putSerializable(MainActivity.MAINUSER, mainUser);
            UploadFragment uploadFragment = new UploadFragment();
            uploadFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, uploadFragment).commit();//업로드 메뉴 선택시 업로드 프래그먼트로 이동
        } else if (id == R.id.drawer_profile) {         ///프로필 설정 이동
            currentViewPage = DRAWER;
            actionBar.setTitle("프로필 설정");
            drawer.setCheckedItem(R.id.drawer_profile);

            ProfileGetRequest request = new ProfileGetRequest(this);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                    profile_getUser = result.getUser();
                    Bundle b = new Bundle();
                    b.putSerializable(MainActivity.PROFILEUSER, profile_getUser);
                    ProfileSettingFragment profileSettingFragment = new ProfileSettingFragment();
                    profileSettingFragment.setArguments(b); //네비게이션 드로어에 유저정보 전달
                    getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, profileSettingFragment).commit();
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                    if (Debug.debugmode)
                        Toast.makeText(MainActivity.this, "유저 가져오기 실패", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.message_network_fail), Toast.LENGTH_SHORT).show();
                }
            });

        } else if (id == R.id.drawer_message) {
            currentViewPage = DRAWER;
            actionBar.setTitle("메세지");
            drawer.setCheckedItem(R.id.drawer_message);
            Bundle b = new Bundle();
            b.putSerializable(MainActivity.MAINUSER, mainUser);
            MessageListFragment messageListFragment = new MessageListFragment();
            messageListFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, messageListFragment).commit();
        } else if (id == R.id.drawer_likeContents) {
            if (Debug.debugmode) Toast.makeText(this, "추후 구현 예정입니다.", Toast.LENGTH_SHORT).show();
//            actionBar.setTitle("내가 좋아요한 게시물");
//            drawer.setCheckedItem(R.id.drawer_likeContents);
//            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MyLikeContentsFragment()).commit();
        } else if (id == R.id.drawer_setting) {
            currentViewPage = DRAWER;
            actionBar.setTitle("설정");
            drawer.setCheckedItem(R.id.drawer_setting);
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new SettingFragment()).commit();
        } else if (id == R.id.drawer_main) {
            goMainFragment(MainFragment.NEWSFEEDTAB);
        }


        drawerLayout.closeDrawer(GravityCompat.START); // 드로어 닫음

        return true;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LIKENOTIFICATION) {
            if (resultCode == Activity.RESULT_OK) {
                LikeNotification notification = (LikeNotification) data.getSerializableExtra(NotificationActivity.RESULT_NOTIFICATION);
                goMainFragment(MainFragment.USERTAB);
            }
        } else if (requestCode == REQUEST_UPLOAD) {
            if (resultCode == Activity.RESULT_OK) {
                int tabindex = data.getIntExtra(MainActivity.TABINDEX, 0);
                goMainFragment(tabindex);
            }
        } else if (requestCode == REQUEST_MAKELABEL) {
            if (resultCode == Activity.RESULT_OK) {
                int tabindex = data.getIntExtra(MainActivity.TABINDEX, 0);

            }
        }
    }
//
//    public void refresh(){
//        Fragment currentFragment = this.getSupportFragmentManager().findFragmentByTag("mainContainer");
//        if (currentFragment instanceof MainFragment) {
//            currentFragment.getChildFragmentManager().findFragmentByTag()
//            FragmentTransaction fragTransaction =   this.getSupportFragmentManager().beginTransaction();
//            fragTransaction.detach(currentFragment);
//            fragTransaction.attach(currentFragment);
//            fragTransaction.commit();}
//    }


    public void goMainFragment(int tabIndex) // 메인 프래그먼트로 이동하며 인자로 받은 값 0~2탭으로 바로이동시켜줍니다.
    {
        drawer.setCheckedItem(R.id.drawer_main);
        actionBar.setTitle("The Label");
        bundle.putSerializable(MAINUSER, mainUser);
        if (tabIndex != 0) {
            bundle.putInt(TABINDEX, tabIndex);
        }
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.drawer_container, mainFragment, "mainContainer")
                .commit();

    }


    public void drawerUserSetting(String name) {
        headerUserName.setText(name);
    }

    public void startUploadActivity(int mode) {
        Intent intent = new Intent(this, UploadActivity.class);
        intent.putExtra(MainActivity.MAINUSER, mainUser);
        intent.putExtra(UploadFragment.UPLOADMODE, mode);
        startActivityForResult(intent, MainActivity.REQUEST_UPLOAD);
    }

    public void goProfileSetting() {
        drawer.setCheckedItem(R.id.drawer_profile);
        Bundle b = new Bundle();
        b.putSerializable(MainActivity.MAINUSER, mainUser);
        ProfileSettingFragment profileSettingFragment = new ProfileSettingFragment();
        profileSettingFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, profileSettingFragment).commit();
    }

    // 단말환경 check
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}