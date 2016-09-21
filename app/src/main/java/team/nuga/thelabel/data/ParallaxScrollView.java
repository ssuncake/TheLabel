package team.nuga.thelabel.data;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import team.nuga.thelabel.R;

/**
 * Created by Tacademy on 2016-09-21.
 */
public class ParallaxScrollView extends ScrollView {
    private static final float DEFAULT_SCROLL_FACTOR = 0.6f;
    private float mScrollFactor = DEFAULT_SCROLL_FACTOR;

    private static final boolean PRE_HONEYCOMB = Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB;
    private int mBackgroundResId;
    private View mBackgroundView;

    public ParallaxScrollView(Context context) {
        super(context);
        initView(context, null, 0);
    }


    public ParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }


    public ParallaxScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {
        if (isInEditMode()) {
            return;
        }


        if (attrs != null) {
            TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.ParallaxScrollView, defStyle, 0);
            mBackgroundResId = values.getResourceId(R.styleable.ParallaxScrollView_backgroundView, 0);
            mScrollFactor = values.getFloat(R.styleable.ParallaxScrollView_scrollFactor, DEFAULT_SCROLL_FACTOR);
            values.recycle();
        }


        // Disable fading edge
        setVerticalFadingEdgeEnabled(false);
    }

    public void setBackgroundView(int resId) {
        mBackgroundView = findViewById(resId);
    }

    public void setBackgroundView(View view) {
        mBackgroundView = view;
    }

    public void setScrollFactor(float scrollFactor) {
        mScrollFactor = scrollFactor;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // On layout changes (eg. orientation change) scroll offset might have changed.
            // Setting a new Y translation here removes any background view hiccups.
            translateBackgroundView(getScrollY());
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mBackgroundResId > 0 && mBackgroundView == null) {
            mBackgroundView = findViewById(mBackgroundResId);
            mBackgroundResId = 0;
        }
    }


    @Override
    protected void onDetachedFromWindow() {

        if (PRE_HONEYCOMB && mBackgroundView != null) {
            mBackgroundView.clearAnimation();
        }
        mBackgroundView = null;
        super.onDetachedFromWindow();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        translateBackgroundView(t);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void translateBackgroundView(int y) {
        if (mBackgroundView != null) {
            int translationY = (int) (y * mScrollFactor);
            if (PRE_HONEYCOMB) {
                ViewCompat.wrap(mBackgroundView).setTranslationY(translationY);
            } else {
                mBackgroundView.setTranslationY(translationY);
            }
        }
    }

}
