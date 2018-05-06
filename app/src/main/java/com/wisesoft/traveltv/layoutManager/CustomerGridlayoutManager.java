package com.wisesoft.traveltv.layoutManager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.android_mobile.core.utiles.Lg;
import com.owen.tvrecyclerview.widget.SpannableGridLayoutManager;

/**
 * Created by picher on 2018/4/24.
 * Describe：
 */

public class CustomerGridlayoutManager extends SpannableGridLayoutManager {
    public CustomerGridlayoutManager(Context context) {
        super(context);
    }

    public CustomerGridlayoutManager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerGridlayoutManager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public int getChildHeight(int rowSpan) {
        int viewHeight = (getHeight() - getPaddingBottom() - getPaddingTop()) / getNumRows();
        return (viewHeight) * rowSpan;
    }

    @Override
    public int getChildWidth(int colSpan) {
        return super.getChildWidth(colSpan);
    }
}
