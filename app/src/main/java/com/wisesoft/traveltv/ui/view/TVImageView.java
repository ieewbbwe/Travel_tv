package com.wisesoft.traveltv.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by picher on 2017/9/26.
 * Describe：
 */

public class TVImageView extends ImageView {
    public TVImageView(Context context) {
        this(context,null);
    }

    public TVImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TVImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            runScaleAnim(this, 1.1f, 1.1f);
        } else {
            runScaleAnim(this, 1 / 1.1f, 1 / 1.1f);
        }
    }

    private void runScaleAnim(View v, float scaleX, float scaleY) {
        v.animate().scaleX(scaleX).scaleY(scaleY).setDuration(300);
    }
}
