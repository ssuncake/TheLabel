package team.nuga.thelabel;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Bliss on 2016-09-05.
 */

public class AppFunction {

    static final int BACK_PRESSED_TIME = 2000; //백버튼 두번누르기 까지의 간격
    private long backKeyPressedTime = 0; //백버튼 누른시간
    private Toast toast;
    private Activity activity;

    public AppFunction(Activity context) {
        this.activity = context;
    }

    public void appFinished() {
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
    private void showGuide() {
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",
                Toast.LENGTH_SHORT);
        toast.show();
    }



}
