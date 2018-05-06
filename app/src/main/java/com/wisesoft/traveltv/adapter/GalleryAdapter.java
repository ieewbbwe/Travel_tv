package com.wisesoft.traveltv.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android_mobile.core.manager.image.ImageLoadFactory;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.model.temp.ItemInfoBean;

import java.util.List;

/**
 * Created by picher on 2017/9/9.
 * Describe：首页画廊适配器
 */

public class GalleryAdapter extends BaseAdapter {
    private Context ctx;
    private List<ItemInfoBean> list;
    private OnItemClickListener mItemClickListener;

    public GalleryAdapter(List<ItemInfoBean> list, Context context) {
        this.ctx = context;
        this.list = list;
    }

    public List<ItemInfoBean> getDataList(){
        return list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.layout_gallery, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        ItemInfoBean item = list.get(position);
        if(item != null){
            if(TextUtils.isEmpty(item.getImgUrl())){
                ImageLoadFactory.getInstance().getImageLoadHandler()
                        .displayImage(item.getImgRes(), vh.image);
            }else{
                ImageLoadFactory.getInstance().getImageLoadHandler()
                        .displayImage(item.getImgUrl(), vh.image);
            }

            if (mItemClickListener != null) {
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onClick(v, position);
                    }
                });
            }
        }

        return convertView;
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    static class ViewHolder {
        public ImageView image;

        public ViewHolder(View convertView) {
            image = (ImageView) convertView.findViewById(R.id.m_gallery_item_iv);
        }
    }
}
