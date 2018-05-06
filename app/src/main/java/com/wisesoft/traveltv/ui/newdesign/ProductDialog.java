package com.wisesoft.traveltv.ui.newdesign;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_mobile.core.BasicDialog;
import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

/**
 * Created by picher on 2018/5/4.
 * Describe：
 */

public class ProductDialog extends BasicDialog {

    private ImageView mDialogIv;
    private TextView mDialogTitleTv;
    private TextView mDialogPriceTv;
    private TextView mDialogIntroduceTv;

    public ProductDialog(Context context) {
        super(context);
    }

    public ProductDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public int onCreate() {
        return R.layout.detial_product_dialog;
    }

    @Override
    public void onViewCreated(View v) {
        mDialogIv = (ImageView) v.findViewById(R.id.m_dialog_iv);
        mDialogTitleTv = (TextView) v.findViewById(R.id.m_dialog_title_tv);
        mDialogPriceTv = (TextView) v.findViewById(R.id.m_dialog_price_tv);
        mDialogIntroduceTv = (TextView) v.findViewById(R.id.m_dialog_introduce_tv);

        setCanceledOnTouchOutside(true);// 设置点击dialog外面的界面 关闭dialog
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        dialogWindow.setWindowAnimations(com.android_mobile.core.R.style.dialog_anim);
        DisplayMetrics dm = mContentView.getResources().getDisplayMetrics();
        lp.width = (int) (0.4 * dm.widthPixels);
        lp.height = (int) (0.8 * dm.heightPixels);
        dialogWindow.setAttributes(lp);
    }

    public void setData(ItemInfoBean infoBean) {
        if(mDialogIv != null){
            ImageLoadFactory.getInstance().getImageLoadHandler().displayImage(infoBean.getImgUrl(),mDialogIv);
            mDialogTitleTv.setText(infoBean.getName());
            mDialogPriceTv.setText(infoBean.getPrice()+"￥");
            mDialogIntroduceTv.setText("商品介绍：" + infoBean.getIntroduce());
        }
    }
}
