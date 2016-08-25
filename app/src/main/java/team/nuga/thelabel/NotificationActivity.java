package team.nuga.thelabel;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.nuga.thelabel.Adapther.NotificationViewpagerAdapter;

public class NotificationActivity extends AppCompatActivity {
    @BindView(R.id.notification_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.notification_viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.main_viewpager, new InviteNotificationFragment());
//        fragmentTransaction.commit();

        tabLayout.addTab(tabLayout.newTab().setText("내소식"));
        tabLayout.addTab(tabLayout.newTab().setText("초대"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        NotificationViewpagerAdapter notificationviewpagerAdapter = new NotificationViewpagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(notificationviewpagerAdapter);
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
