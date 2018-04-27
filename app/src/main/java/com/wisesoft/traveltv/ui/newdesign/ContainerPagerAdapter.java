package com.wisesoft.traveltv.ui.newdesign;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wisesoft.traveltv.ui.change.HomeTab;

import java.util.List;

/**
 * Created by picher on 2018/4/1.
 * Describe：首页的构造器
 */

public class ContainerPagerAdapter extends FragmentPagerAdapter {
    private Context ctx;
    private List<BaseNewDesignFragment> baseFragmnets;
    private List<HomeTab> titleData;

    public ContainerPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.ctx = context;
    }

    public void setData(List<BaseNewDesignFragment> fragmnets) {
        this.baseFragmnets = fragmnets;
    }

    public void setTitleData(List<HomeTab> titleData){
        this.titleData = titleData;
    }

    @Override
    public Fragment getItem(int position) {
        return baseFragmnets.get(position);
    }

    @Override
    public int getCount() {
        return baseFragmnets == null ? 0 : baseFragmnets.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleData == null ? "" : ctx.getResources().getString(titleData.get(position).getNameRes());
    }
}
