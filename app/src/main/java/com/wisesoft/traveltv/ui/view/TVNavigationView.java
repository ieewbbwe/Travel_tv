package com.wisesoft.traveltv.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by mxh on 2017/9/15.
 * Describe：
 */

public class TVNavigationView extends LinearLayout {
    public TVNavigationView(Context context) {
        this(context, null);
    }

    public TVNavigationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TVNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addNavigationTab(TVIconView iconView) {
        
    }
}
