package com.wisesoft.traveltv.ui.view;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by picher on 2017/10/23.
 * Describe：
 */

public class CheckOverSizeTextView extends TextView {

    private boolean isOverSize;

    public CheckOverSizeTextView(Context context) {
        this(context,null);
    }

    public CheckOverSizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CheckOverSizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                checkOverLines();
            }
        });
    }

    public boolean checkOverLines(){
        int maxLine = getMaxLines();
        try {
            Field field = getClass().getSuperclass().getDeclaredField("mLayout");
            field.setAccessible(true);
            Layout mLayout = (Layout) field.get(this);
            if (mLayout == null)
                return false;
            isOverSize = mLayout.getEllipsisCount(maxLine - 1) > 0;
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return isOverSize;
    }

    public boolean isOverSize() {
        return isOverSize;
    }
}
