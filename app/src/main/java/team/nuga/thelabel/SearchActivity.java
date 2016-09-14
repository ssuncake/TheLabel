package team.nuga.thelabel;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.adapter.SearchViewpagerAdapter;
import team.nuga.thelabel.fragment.UserSearchFragment;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.search_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.search_viewpager)
    ViewPager viewPager;

    @OnClick(R.id.imageButton_back)
    public void BackButton() {
        finish();
    }

    EditText editTextSearch;
    ImageButton searchImageButton;
    SearchViewpagerAdapter searchviewpagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        tabLayout.addTab(tabLayout.newTab().setText("사람찾기"));
        tabLayout.addTab(tabLayout.newTab().setText("레이블찾기"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        searchviewpagerAdapter = new SearchViewpagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
