package com.phone.base.common.view;

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/9/2
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by libin on 2016/11/18.
 */

public class ClearEditView extends EditText {

    private OnDrawableLeftListener mLeftListener;
    private OnDrawableRightListener mRightListener;
    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;

    public ClearEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ClearEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClearEditView(Context context) {
        super(context);
    }

    public void setOnDrawableLeftListener(OnDrawableLeftListener listener) {
        this.mLeftListener = listener;
    }

    public void setOnDrawableRightListener(OnDrawableRightListener listener) {
        this.mRightListener = listener;
    }

    public interface OnDrawableLeftListener {
        void onDrawableLeftClick(View view);
    }

    public interface OnDrawableRightListener {
        void onDrawableRightClick(View view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (mRightListener != null) {
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width()/4 -5)) {
                        mRightListener.onDrawableRightClick(this);
                        return true;
                    }
                }

                if (mLeftListener != null) {
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                    if (drawableLeft != null && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())) {
                        mLeftListener.onDrawableLeftClick(this);
                        return true;
                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }
}

