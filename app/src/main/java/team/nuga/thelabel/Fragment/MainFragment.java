package team.nuga.thelabel.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.R;
import team.nuga.thelabel.Adapther.ViewpagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */

public class MainFragment extends Fragment {
    @BindView(R.id.main_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view); //fragment나 viewholder를 사용할 때 ButterKnife.bind(this)방식은 activity일 때

        tabLayout.addTab(tabLayout.newTab().setText("newsfeed")); //setindicator을 사용하게 되면 내가 원하는 view를 넣을 수 있고, setText나 setIon을 사용하면 글자나 아이콘을 사용할 수 있다.
        tabLayout.addTab(tabLayout.newTab().setText("label"));
        tabLayout.addTab(tabLayout.newTab().setText("account"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getChildFragmentManager(),tabLayout.getTabCount()); //getChildFragmentManager을 사용해도 되고
        viewPager.setAdapter(viewpagerAdapter);                                                                       //getActivity().getSupportFragmentManager()을 사용해도 된다.
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout)); //tab과 viewpager가 같이 변환 될 수 있도록 해주는 메소드
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
        return view;
    }


}
