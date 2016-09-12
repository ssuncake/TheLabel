package team.nuga.thelabel.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.MainActivity;
import team.nuga.thelabel.adapter.MainViewpagerAdapter;
import team.nuga.thelabel.data.User;
import team.nuga.thelabel.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class MainFragment extends Fragment {

    public static final int NEWSFEEDTAB = 0;
    public static final int LABELTAB = 1;
    public static final int USERTAB = 2;



    @BindView(R.id.main_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;

    public User user;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view); //fragment나 viewholder를 사용할 때 ButterKnife.bind(this)방식은 activity일 때



        user =(User)getArguments().getSerializable(MainActivity.MAINUSER); // 메인 액티비티로부터 받아온 더미유저를 사용



        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.main_fragment_home)); //setindicator을 사용하게 되면 내가 원하는 view를 넣을 수 있고, setText나 setIon을 사용하면 글자나 아이콘을 사용할 수 있다.
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.main_fragment_label));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.main_fragment_account));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MainViewpagerAdapter mainViewpagerAdapter = new MainViewpagerAdapter(getChildFragmentManager(),tabLayout.getTabCount(), user); //어뎁터에 가짜 유저데이터를 넘겨줌.

        viewPager.setAdapter(mainViewpagerAdapter);                                                                       //getActivity().getSupportFragmentManager()을 사용해도 된다.
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));//tab과 viewpager가 같이 변환 될 수 있도록 해주는 메소드
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    UserMainFragment f = (UserMainFragment)mainViewpagerAdapter.getItem(2);
                    f.mediaStop();
//                    LabelMainFragment f1 = (LabelMainFragment)mainViewpagerAdapter.getItem(1);
//                    f1.mediaStop();
                }else if(position==1){
                    NewsFeedFragment f2 = (NewsFeedFragment)mainViewpagerAdapter.getItem(0);
                    f2.mediaStop();
                    UserMainFragment f = (UserMainFragment)mainViewpagerAdapter.getItem(2);
                    f.mediaStop();
                }else if(position==2){
                    NewsFeedFragment f2 = (NewsFeedFragment)mainViewpagerAdapter.getItem(0);
                    f2.mediaStop();
//                    LabelMainFragment f1 = (LabelMainFragment)mainViewpagerAdapter.getItem(1);
//                    f1.mediaStop();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




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

        int tabindex = getArguments().getInt(MainActivity.TABINDEX);
        if(tabindex!=0){
            viewPager.setCurrentItem(tabindex);
        }
        return view;
    }



}
