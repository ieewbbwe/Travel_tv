package com.wisesoft.traveltv.ui.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.adapter.ImageAdapter;
import com.wisesoft.traveltv.utils.BitmapScaleDownUtil;

public class Gallery3DActivity extends AppCompatActivity {
    /* 数据段begin */
    private final String TAG = "Gallery3DActivity";
    private Context mContext;

    // 图片缩放倍率（相对屏幕尺寸的缩小倍率）
    public static final int SCALE_FACTOR = 8;

    // 图片间距（控制各图片之间的距离）
    private final int GALLERY_SPACING = -10;

    // 控件
    private GalleryFlow mGalleryFlow;
    /* 数据段end */

    /* 函数段begin */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();

        setContentView(R.layout.activity_gallery3_d);
        initGallery();
    }

    private void initGallery()
    {
        // 图片ID
        int[] images = {
                R.mipmap.ic_content,
                R.mipmap.ic_content,
                R.mipmap.ic_content,
                 };

        ImageAdapter adapter = new ImageAdapter(mContext, images);
        // 计算图片的宽高
        int[] dimension = BitmapScaleDownUtil.getScreenDimension(getWindowManager().getDefaultDisplay());
        int imageWidth = dimension[0] / SCALE_FACTOR;
        int imageHeight = dimension[1] / SCALE_FACTOR;
        // 初始化图片
        adapter.createImages(imageWidth, imageHeight);

        // 设置Adapter，显示位置位于控件中间，这样使得左右均可"无限"滑动
        mGalleryFlow = (GalleryFlow) findViewById(R.id.gallery_flow);
        mGalleryFlow.setSpacing(GALLERY_SPACING);
        mGalleryFlow.setAdapter(adapter);
        mGalleryFlow.setSelection(Integer.MAX_VALUE / 2);
    }
    /* 函数段end */
}
