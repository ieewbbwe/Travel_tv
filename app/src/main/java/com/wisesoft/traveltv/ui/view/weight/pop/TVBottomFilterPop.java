package com.wisesoft.traveltv.ui.view.weight.pop;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.android_mobile.core.BasicPopWindow;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.DataEngine;

/**
 * Created by picher on 2017/9/24.
 * Describe：
 */

public class TVBottomFilterPop extends BasicPopWindow {

    private String mType;
    private TVFilterView mFilterV;

    public TVBottomFilterPop(Context context) {
        this(context,"");
    }

    public TVBottomFilterPop(Context context,String type) {
        super(context);
        this.mType = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.layout_filter_pop, parent, false);
    }

    @Override
    public void onViewCreated(View view) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(mDisplayMetrics);

        mFilterV = (TVFilterView) view.findViewById(R.id.m_filter_fv);
        mFilterV.setFilterList(DataEngine.getStayFilterData());
        // 设置弹出窗体显示时的动画，从底部向上弹出
        setAnimationStyle(com.android_mobile.core.R.style.take_photo_anim);
        setHeight((int) (mDisplayMetrics.heightPixels * 0.3));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        if(mFilterV!=null){
            mFilterV.setOnItemClickListener(onItemClickListener);
        }
    }
}
