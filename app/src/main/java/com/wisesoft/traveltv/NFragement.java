package com.wisesoft.traveltv;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;

import com.android_mobile.core.base.BaseFragment;
import com.tv.boost.widget.focus.FocusBorder;

/**
 * Created by mxh on 2017/8/8.
 * Describe：
 */

public abstract class NFragement extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected FocusBorder getFocusBorder() {
        if (activity != null && activity instanceof NActivity) {
            if (((NActivity) activity).mFocusBorder == null) {
                ((NActivity) activity).initBorder();
            }
            return ((NActivity) activity).mFocusBorder;
        }
        return new FocusBorder.Builder().asColor()
                .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 18f) //阴影宽度(方式二)
                .shadowColor(getResources().getColor(R.color.colorPrimaryDark)) //阴影颜色
                .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 2f) //边框宽度
                .borderColor(getResources().getColor(R.color.white)) //边框颜色
                .build(activity);
    }


    /**
     * 拦截Activity的key事件
     * * @param keyCode
     *
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onKeyUp(int keyCodem, KeyEvent event){
        return false;
    }
}
