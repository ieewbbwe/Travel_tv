package com.wisesoft.traveltv;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.android_mobile.core.base.BaseActivity;
import com.tv.boost.widget.focus.FocusBorder;
import com.tv.boost.widget.tablayout.ValueAnimatorCompat;
import com.wisesoft.traveltv.model.FilterBean;
import com.wisesoft.traveltv.ui.view.weight.pop.OnItemClickListener;
import com.wisesoft.traveltv.ui.view.weight.pop.TVBottomFilterPop;

/**
 * Created by mxh on 2017/8/8.
 * Describe：
 */

public abstract class NActivity extends BaseActivity {

    protected FocusBorder mFocusBorder;
    private TVBottomFilterPop mFilterPop;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public void initBorder() {
        mFocusBorder = new FocusBorder.Builder().asColor()
                .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 18f) //阴影宽度(方式二)
                .shadowColor(getResources().getColor(R.color.colorPrimaryDark)) //阴影颜色
                .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 2f) //边框宽度
                .borderColor(getResources().getColor(R.color.white)) //边框颜色
                .build(this);
    }

    public boolean isFocused(View v) {
        return null != v || v.isFocused();
    }

    /**
     * 弹出筛选菜单
     */
    public void showFilterPop(String type,OnItemClickListener clickListener) {
        if (mFilterPop == null) {
            mFilterPop = new TVBottomFilterPop(mContext, type);
            mFilterPop.setOnItemClickListener(clickListener);
            mFilterPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
        backgroundAlpha(0.5f);
        mFilterPop.showAtLocation(getRootView(),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void backgroundAlpha(float alpha) {
        final int RED = 0x00000000;
        final int BLUE = 0x68000000;
        if (alpha == 1f) {
            ValueAnimator colorAnim = ObjectAnimator.ofInt(mShadow, "backgroundColor", BLUE,RED);
            colorAnim.setDuration(300);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.setRepeatMode(ValueAnimator.REVERSE);
            colorAnim.start();
            colorAnim.addListener(new SimpleAnimListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mShadow.setVisibility(View.GONE);
                }
            });
        } else {
            mShadow.setVisibility(View.VISIBLE);
            ValueAnimator colorAnim = ObjectAnimator.ofInt(mShadow, "backgroundColor",RED, BLUE);
            colorAnim.setDuration(300);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.setRepeatMode(ValueAnimator.REVERSE);
            colorAnim.start();
        }

    }

    class SimpleAnimListener implements Animator.AnimatorListener {


        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

}
