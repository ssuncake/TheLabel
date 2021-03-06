package team.nuga.thelabel.data;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import team.nuga.thelabel.R;

/**
 * Created by kuhyebin on 2016-09-18.
 */
public class ClearEditText extends AppCompatEditText implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {
    private Drawable clearDrawable;
    private OnFocusChangeListener onFocusChangeListener;
    private OnTouchListener onTouchListener;

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public void init() {
        Drawable tempDrawable = ContextCompat.getDrawable(getContext(), R.drawable.cleartext);
        clearDrawable = DrawableCompat.wrap(tempDrawable);
//        DrawableCompat.setTintList(clearDrawable.getHintTextColors());
        clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(), clearDrawable.getIntrinsicHeight());

        setClearIconVisible(false);

        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            setClearIconVisible(getText().length() > 0);
        }else {
            setClearIconVisible(false);
        }
        if(onFocusChangeListener!=null){
            onFocusChangeListener.onFocusChange(view,b);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int)motionEvent.getX();
        if(clearDrawable.isVisible()&& x > getWidth() - getPaddingRight() - clearDrawable.getIntrinsicWidth()){
            if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                setError(null);
                setText(null);
            }
            return true;
        }
        if(onTouchListener !=null){
            return onTouchListener.onTouch(view, motionEvent);
        }else {
            return false;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count){
        if(isFocused()){
            setClearIconVisible(s.length()>0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void setClearIconVisible(boolean visible){
        clearDrawable.setVisible(visible, false);
        setCompoundDrawables(null, null, visible? clearDrawable : null, null);

    }
}
