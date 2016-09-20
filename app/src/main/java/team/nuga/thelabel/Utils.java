package team.nuga.thelabel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016-08-23.
 */
public class Utils {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static String convertTimeToString(Date date) {
        return sdf.format(date);
    }

    public static Date convertStringToTime(String text) throws ParseException {
        return sdf.parse(text);
    }

    public static String convertCalculateMusicTiem(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";

// Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
// Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

// Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

// return timer string
        return finalTimerString;
    }
}
