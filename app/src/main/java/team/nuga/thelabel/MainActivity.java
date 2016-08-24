package team.nuga.thelabel;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
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

    ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (null != ab) {
            ab.setDisplayHomeAsUpEnabled(true);
        }




        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        drawer.setNavigationItemSelectedListener(this);
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
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new UserMainFragment()).commit();


        } else if (id == R.id.drawer_message) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MainFragment()).commit();
        } else if (id == R.id.drawer_likeContents) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MainFragment()).commit();
        } else if (id == R.id.drawer_setting) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MainFragment()).commit();
        } else if (id == R.id.drawer_logOut) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_container, new MainFragment()).commit();
        }
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout) ;
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}