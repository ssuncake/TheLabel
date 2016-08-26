package team.nuga.thelabel;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import team.nuga.thelabel.fragment.MainFragment;

public class FragmentMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, new MainFragment()); //첫번째 인자는 프래그먼트가 들어갸야될 엑티비티의 레이아웃, 두번째 인자는 frgment를 추가하면된다.
                                                                        //replace()메소드를 통해 fragment를 불러온다.
        fragmentTransaction.commit(); //꼭 이걸 해줘야 반영이 된다.
    }
}
