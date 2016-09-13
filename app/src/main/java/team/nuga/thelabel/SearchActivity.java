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

    public static final int PERSONSERACHTAB = 0;
    public static final int LABELSERACHTAB = 1;


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


//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.search_content, new LikeNotificationFragment());
//        fragmentTransaction.commit();

        tabLayout.addTab(tabLayout.newTab().setText("사람찾기"));
        tabLayout.addTab(tabLayout.newTab().setText("레이블찾기"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        searchviewpagerAdapter = new SearchViewpagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),searchText);
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

        editTextSearch = (EditText) findViewById(R.id.editText_search);
        searchImageButton = (ImageButton) findViewById(R.id.imageButton_search);

    }

    String searchText;

    @OnClick(R.id.imageButton_search)
    public void onClick(View view) {
        searchText = editTextSearch.getText().toString();
        Log.e("로그 검색", searchText);
        int fid = viewPager.getCurrentItem();
//        Log.e("유저 찾음", "유저서치 프래그먼트");
        Fragment suf = searchviewpagerAdapter.getItem(PERSONSERACHTAB);
        Fragment slf = searchviewpagerAdapter.getItem(LABELSERACHTAB);
        if(suf instanceof UserSearchFragment) {
            Log.e("유저 찾음", "유저서치 프래그먼트");
            UserSearchFragment uf = (UserSearchFragment)suf;
            uf.a(searchText);
        }
    }

}
