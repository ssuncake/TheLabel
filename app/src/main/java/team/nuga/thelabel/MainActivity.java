package team.nuga.thelabel;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.Fragment.LabelMainFragment;
import team.nuga.thelabel.Fragment.LabelMakeFragment;
import team.nuga.thelabel.Fragment.MainFragment;
import team.nuga.thelabel.Fragment.UserMainFragment;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener{


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
            ab.setDisplayHomeAsUpEnabled(true);  ab.setDisplayShowCustomEnabled(true);
            ab.setDisplayShowTitleEnabled(true); ab.setTitle("     The label ");

        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        drawer.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager(); // 프래그먼트 매니저를 얻어옴.
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.drawer_upload) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MainFragment()).commit();
        } else if (id == R.id.drawer_profile) {
            item.setIcon(R.drawable.profile_girl);
            item.setTitleCondensed("ddd");
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new UserMainFragment()).commit();


        } else if (id == R.id.drawer_message) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MainFragment()).commit();
        } else if (id == R.id.drawer_likeContents) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MainFragment()).commit();
        } else if (id == R.id.drawer_setting) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MainFragment()).commit();
        }
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout) ;
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void selectLabel(String labelName){ // 레이블선택화면에서 레이블을 선택하면 선택한 레이블에 따라 프래그먼트를 교체해줌

        Bundle bundle = new Bundle();
        bundle.putString("LabelName",labelName);
        // 프래그먼트로 레이블이름을 전달하기 위해 번들값을 생성
        LabelMainFragment selectedLabelFragment = new LabelMainFragment();
        selectedLabelFragment.setArguments(bundle);
        // 프래그먼트에 번들값 셋
        mFragmentManager.beginTransaction().replace(R.id.drawer_container,selectedLabelFragment).commit();
        // drawer_container프래그먼트 전환
    }

    public void makeLabel(){
        mFragmentManager.beginTransaction().replace(R.id.drawer_container,new LabelMakeFragment()).commit();
    }



}