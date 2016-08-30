package team.nuga.thelabel;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.nuga.thelabel.data.User;

public class IntroActivity extends AppCompatActivity {

    FragmentManager introFragmentManger;

    @BindView(R.id.imageView_Intro_LogoImage)
    ImageView logo;int clickImage = 0;
    @OnClick(R.id.imageView_Intro_LogoImage)
    public void click(){
        clickImage ++;
        if(9>clickImage && clickImage>5){
            Toast.makeText(IntroActivity.this, 10-clickImage+"번 더!...", Toast.LENGTH_SHORT).show();
        }else if(clickImage>9){Intent intent = new Intent(this, ScrollingActivity.class);
            Toast.makeText(this, "♡♡♡", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            clickImage = 0;
        }
    }
    @BindView(R.id.linearLayout_intro_main)
    FrameLayout mainLayout;

    @BindView(R.id.frameLayout_intro_container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        logo.setImageDrawable(getResources().getDrawable(R.drawable.intrologo));




        final Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // 이부분에서 자동로그인이 성공하면 바로 이동가능하게 코드
                //
                //
                //

                moveUpLogo();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showLoginFragment();}
                },1000);//로그인 1초 후 이동.
            }
        },2000);




    }

    public void signUpFragmentCall(){
        introFragmentManger.beginTransaction().replace(R.id.frameLayout_intro_container,new SignUpFragment()).commit();
    }

    private void moveUpLogo(){
        float startY = logo.getY();
        float endY = startY-getResources().getDimension(R.dimen.intro_movelogo);
        final ValueAnimator logoAnimator = ValueAnimator.ofFloat(startY,endY);
        logoAnimator.setEvaluator(new FloatEvaluator());
        logoAnimator.setDuration(1000);
        logoAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float y = (Float)valueAnimator.getAnimatedValue();
                logo.setY(y);
            }
        });
        logoAnimator.start();
    }

    private void showLoginFragment(){

        container.setVisibility(View.VISIBLE);
        introFragmentManger = getSupportFragmentManager();
        introFragmentManger.beginTransaction().add(R.id.frameLayout_intro_container,new SignInFragment()).commit();
    }

    public void moveMainActivity(User user){

        Intent intent = new Intent(IntroActivity.this,MainActivity.class);
        intent.putExtra("LoginUser",user);
        startActivity(intent);
        finish();
    }

}
