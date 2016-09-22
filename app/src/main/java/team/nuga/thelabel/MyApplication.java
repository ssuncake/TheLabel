package team.nuga.thelabel;

import android.app.Application;
import android.content.Context;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class MyApplication extends Application {
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunGothic.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunGothicBold.otf"));
    }

    public static Context getContext(){return context;}


}
