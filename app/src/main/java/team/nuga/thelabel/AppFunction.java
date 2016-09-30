package team.nuga.thelabel;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Bliss on 2016-09-05.
 */

public class AppFunction {      //앱 기능 통합 함수

    static final int BACK_PRESSED_TIME = 2000; //백버튼 두번누르기 까지의 간격
    static final int seconds = 1000;
    private long backKeyPressedTime = 0; //백버튼 누른시간 저장하기위한 변수
    private Toast toast;
    private Activity activity;

    public AppFunction(Activity context) {
        this.activity = context;
    }


    /**
     * Twice input for finish application
     * @param finish_second Double-click distance(Seconds)
     */
    public void appFinished(int finish_second) {                                     //백버튼 두번 눌러야 종료
        if (System.currentTimeMillis() > backKeyPressedTime + finish_second*seconds) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + finish_second*seconds) {
            activity.finish();
            toast.cancel();
        }
    }
    public void appFinished() {                                     //백버튼 두번 눌러야 종료
        if (System.currentTimeMillis() > backKeyPressedTime + BACK_PRESSED_TIME) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + BACK_PRESSED_TIME) {
            activity.finish();
            toast.cancel();
        }
    }

    private void showGuideBack() {                          //백버튼 가이드
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",
                Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showGuide() {                          //백버튼 가이드
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",
                Toast.LENGTH_SHORT);
        toast.show();
    }

}
