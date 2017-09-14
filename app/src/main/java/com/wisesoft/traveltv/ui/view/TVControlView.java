package com.wisesoft.traveltv.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisesoft.traveltv.R;

/**
 * Created by mxh on 2017/9/14.
 * Describe：
 */

public class TVControlView extends LinearLayout {

    private TextView mTextView;
    private ImageView mIconView;

    public TVControlView(Context context) {
        this(context, null);
    }

    public TVControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TVControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextView = new TextView(context);
        mIconView = new ImageView(context);

        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER_HORIZONTAL;
        params2.gravity = Gravity.CENTER_HORIZONTAL;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ControlView, defStyleAttr, 0);
        if (null != typedArray) {
            mTextView.setText(typedArray.getText(R.styleable.ControlView_cText));
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelOffset(R.styleable.ControlView_cTextSize, 20));
            ColorStateList colorStateList = typedArray.getColorStateList(R.styleable.ControlView_cTextColor);
            if (null != colorStateList) {
                mTextView.setTextColor(colorStateList);
            }
            int padding = typedArray.getDimensionPixelOffset(R.styleable.ControlView_cTextPadding, 0);
            mTextView.setPadding(padding, padding, padding, padding);

            mIconView.setImageResource(typedArray.getResourceId(R.styleable.ControlView_cIcon, 0));

            addView(mIconView, params2);
            addView(mTextView, params1);
            typedArray.recycle();
        }
    }
}
