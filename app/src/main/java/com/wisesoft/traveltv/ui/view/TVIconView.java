package com.wisesoft.traveltv.ui.view;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android_mobile.core.utiles.Lg;
import com.wisesoft.traveltv.R;

/**
 * Created by picher on 2017/9/9.
 * Describe：
 */

public class TVIconView extends LinearLayout {

    private AppCompatTextView mTextView;
    private AppCompatImageView mImageView;
    private int mColorFoucus;
    private int mColorNotFoucus;
    private double mHeight;

    public TVIconView(Context context) {
        this(context, null);
    }

    public TVIconView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TVIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextView = new AppCompatTextView(context);
        mTextView.setSingleLine(true);
        mImageView = new AppCompatImageView(context);
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER_HORIZONTAL;
        params2.gravity = Gravity.CENTER_HORIZONTAL;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconView, defStyleAttr, 0);
        if (a != null) {
            mTextView.setText(a.getText(R.styleable.IconView_gText));
            ColorStateList colorStateList = a.getColorStateList(R.styleable.IconView_gTextColor);
            if (null != colorStateList) {
                mTextView.setTextColor(colorStateList);
            }
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelOffset(R.styleable.IconView_gTextSize, 20));
            int padding = a.getDimensionPixelOffset(R.styleable.IconView_gTextPadding, 0);
            mTextView.setPadding(padding, padding, padding, padding);

            mImageView.setImageResource(a.getResourceId(R.styleable.IconView_gIcon, 0));
            params2.width = a.getDimensionPixelOffset(R.styleable.IconView_gIconWidth, LayoutParams.WRAP_CONTENT);
            params2.height = a.getDimensionPixelOffset(R.styleable.IconView_gIconHeight, LayoutParams.WRAP_CONTENT);
            a.recycle();
        }

        addView(mTextView, params1);
        addView(mImageView, params2);
        setOrientation(VERTICAL);

        mColorFoucus = Color.parseColor("#ededed");
        mColorNotFoucus = Color.parseColor("#7f7f7f");

    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            runScaleAnim(this, 1.2f, 1.2f);
        } else {
            runScaleAnim(this, 1 / 1.2f, 1 / 1.2f);
        }
        tintImage(gainFocus);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //TODO 测量画布高度为原始画布的1.2倍
        if (0 == mHeight && getHeight() > 0) {
            mHeight = getHeight();
        }
    }

    private void tintImage(boolean gainFocus) {
        Drawable imageDrawable = mImageView.getDrawable();
        Drawable.ConstantState state = imageDrawable.getConstantState();
        Drawable wrap = DrawableCompat.wrap(state == null ? imageDrawable : state.newDrawable()).mutate();
        DrawableCompat.setTint(wrap, gainFocus ? mColorFoucus : mColorNotFoucus);
        mImageView.setImageDrawable(wrap);
        mTextView.setTextColor(gainFocus ? mColorFoucus : mColorNotFoucus);
    }

    private void runScaleAnim(View v, float scaleX, float scaleY) {
        v.animate().scaleX(scaleX).scaleY(scaleY).setDuration(300);
    }


}
