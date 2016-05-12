package com.vervewireless.mastersampleapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by zvonkok on 24. 02. 2016.
 */
public class SettingsLayout extends LinearLayout {

    private boolean isEnabled = true;

    public SettingsLayout(Context context) {
        super(context);
    }

    public SettingsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isEnabled ? super.onInterceptTouchEvent(ev) : true;
    }
}
