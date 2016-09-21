package team.nuga.thelabel.data;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Tacademy on 2016-09-21.
 */
public class ViewCompat extends Animation {
    public static ViewCompat wrap(View view) {
        Animation wrapper = view.getAnimation();
        if (wrapper instanceof ViewCompat) {
            return (ViewCompat) wrapper;
        } else {
            return new ViewCompat(view);
        }
    }


    private final View mView;


    private float mTranslationY;


    private ViewCompat(View view) {
        setDuration(0);
        setFillAfter(true);
        view.setAnimation(this);
        mView = view;
    }


    public void setTranslationY(float translationY) {
        mTranslationY = translationY;
    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (mView != null) {
            t.getMatrix().postTranslate(0, mTranslationY);
        }
    }

}
