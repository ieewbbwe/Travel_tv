package com.wisesoft.traveltv.ui.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

/**
 * Created by picher on 2018/4/22.
 * Describe：
 */

public class TVHeaderItemView extends CardView {
    private ItemInfoBean itemInfoBean;
    private ImageView mItemIv;
    private TextView mTitleTv;

    public TVHeaderItemView(Context context) {
        this(context,null);
    }

    public TVHeaderItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TVHeaderItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.tv_header_item,this,true);
        mItemIv = (ImageView) findViewById(R.id.m_item_iv);
        mTitleTv = (TextView) findViewById(R.id.m_title_tv);
    }

    public void setData(ItemInfoBean data){
        this.itemInfoBean = data;
        if(itemInfoBean != null){
            ImageLoadFactory.getInstance().getImageLoadHandler().displayImage(itemInfoBean.getImgUrl(),mItemIv);
            mTitleTv.setText(itemInfoBean.getName());
        }
    }

}
