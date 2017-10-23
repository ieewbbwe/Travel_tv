package com.wisesoft.traveltv.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

/**
 * Created by picher on 2017/10/23.
 * Describe：
 */

public class TVScrollView extends ScrollView {

    private OnScrollChangedListener mOnScrollChangedListener;

    public TVScrollView(Context context) {
        this(context, null);
    }

    public TVScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TVScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChange(l, t, oldl, oldt);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }

    public interface OnScrollChangedListener {
        void onScrollChange(int l, int t, int ol, int ot);
    }
}
