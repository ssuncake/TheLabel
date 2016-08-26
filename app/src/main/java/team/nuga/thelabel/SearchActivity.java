package team.nuga.thelabel;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.adapther.SearchViewpagerAdapter;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.search_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.search_viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.search_content, new LikeNotificationFragment());
//        fragmentTransaction.commit();

        tabLayout.addTab(tabLayout.newTab().setText("사람찾기"));
        tabLayout.addTab(tabLayout.newTab().setText("레이블찾기"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

       SearchViewpagerAdapter searchviewpagerAdapter = new SearchViewpagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(searchviewpagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
