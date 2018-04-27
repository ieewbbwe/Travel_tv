package com.wisesoft.traveltv.ui.view;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by picher on 2018/4/24.
 * Describe：
 */

public class CustomerAppbarLayout extends AppBarLayout {

    public static final int EXPANDED = -1;
    public static final int MIDDLE = 0;
    public static final int COLLAPSED = 1;

    private @AppbarLayoutState int mCurrentState = EXPANDED;

    @IntDef({EXPANDED,COLLAPSED,MIDDLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AppbarLayoutState{
    }

    public CustomerAppbarLayout(Context context) {
        this(context,null);
    }

    public CustomerAppbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == 0){
                    if(mCurrentState != EXPANDED){
                        mCurrentState = EXPANDED;
                    }
                }else if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
                    if(mCurrentState != COLLAPSED){
                        mCurrentState = COLLAPSED;
                    }
                }else{
                    if(mCurrentState != MIDDLE){
                        if(mCurrentState == COLLAPSED){
                            //由折叠变成中间状态
                        }else if(mCurrentState == EXPANDED){
                            //由展开变成中间状态
                        }
                        mCurrentState = MIDDLE;
                    }
                }
            }
        });
    }

    public int getmCurrentState() {
        return mCurrentState;
    }

    public boolean isExpened(){
        return mCurrentState == EXPANDED;
    }
}
