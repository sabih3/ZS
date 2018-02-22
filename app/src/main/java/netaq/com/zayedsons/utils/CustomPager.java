package netaq.com.zayedsons.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by sabih on 18-Feb-18.
 */

public class CustomPager extends ViewPager {

    private boolean disable;

    public CustomPager(@NonNull Context context) {
        super(context);
    }

    public CustomPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return disable ? false : super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return disable ? false : super.onInterceptTouchEvent(ev);
    }

    public void disableScroll(boolean disable){
        this.disable = disable;
    }
}
