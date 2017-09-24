package com.wisesoft.traveltv.model;

import android.content.Context;

import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.db.DataBaseDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by mxh on 2017/9/15.
 * Describe：
 */

public class DataEngine {

    public static Context mContext;
    private static DataBaseDao mDao;

    public static void init(Context applicationCtx) {
        mContext = applicationCtx;
        mDao = new DataBaseDao(mContext);
    }

    private static Random random = new Random();

    private static String[] movieImgs = new String[]{
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4116929131,507038119&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2778645995,2482914601&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=471731589,4180970475&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3838971077,4151306981&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=524852470,2918555158&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1954849729,4021317348&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1603461606,3565424705&fm=11&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3946902641,1203417546&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3940629958,3916474893&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=177212210,3066778892&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=838139577,1861058471&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3800968064,203324487&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=955228025,2469569596&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3483986471,1443536788&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1889706933,2434657849&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3346934314,259707941&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1069765571,2669917239&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3179642771,1751426616&fm=26&gp=0.jpg"
    };

    public static List<ItemInfoBean> getVideos(int count) {
        List<ItemInfoBean> itemInfoBeen = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            itemInfoBeen.add(new ItemInfoBean(movieImgs[random.nextInt(movieImgs.length)]));
        }
        return itemInfoBeen;
    }

    public static List<ImageBean> getImages() {
        List<ImageBean> beanList = new ArrayList<>();
        ImageBean image = null;
        try {
            String[] fileNames = mContext.getAssets().list("imgs");
            for (String str : fileNames) {
                String[] strs = str.split("_");
                image = new ImageBean(strs[1].split("\\.")[0], "file:///android_asset/imgs/" + str, strs[0]);
                beanList.add(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return beanList;
    }

    public static List<ItemInfoBean> getItemInfos() {
        ImageBean image;
        List<ItemInfoBean> itemList = new ArrayList<>();
        Random random = new Random();
        List<String> titles = Arrays.asList(mContext.getResources().getStringArray(R.array.default_play_titles));
        List<String> introduces = Arrays.asList(mContext.getResources().getStringArray(R.array.default_play_introduces));
        List<String> address = Arrays.asList(mContext.getResources().getStringArray(R.array.default_play_address));

        for (int i = 0; i < titles.size(); i++) {
            image = mDao.queryImageByOrder(i + 1, Constans.TYPE_PLAY);

            itemList.add(new ItemInfoBean((image != null) ? image.getImgUrl() : "", titles.get(i),
                    random.nextInt(5) + 1, System.currentTimeMillis(), introduces.get(i),
                    Constans.TYPE_PLAY, random.nextInt(6000), address.get(i), "18772943998"));
        }

        List<String> eatTitles = Arrays.asList(mContext.getResources().getStringArray(R.array.default_eat_titles));
        List<String> eatIntroduces = Arrays.asList(mContext.getResources().getStringArray(R.array.default_eat_introduces));
        List<String> eatAddress = Arrays.asList(mContext.getResources().getStringArray(R.array.default_eat_address));

        for (int i = 0; i < eatTitles.size(); i++) {
            image = mDao.queryImageByOrder(i + 1, Constans.TYPE_EAT);

            itemList.add(new ItemInfoBean((image != null) ? image.getImgUrl() : "", eatTitles.get(i),
                    random.nextInt(5) + 1, System.currentTimeMillis(), eatIntroduces.get(i),
                    Constans.TYPE_EAT, random.nextInt(6000), eatAddress.get(i), "18772943998"));
        }

        List<String> stayTitles = Arrays.asList(mContext.getResources().getStringArray(R.array.default_stay_titles));
        List<String> stayIntroduces = Arrays.asList(mContext.getResources().getStringArray(R.array.default_stay_introduces));
        List<String> stayAddress = Arrays.asList(mContext.getResources().getStringArray(R.array.default_stay_address));

        for (int i = 0; i < stayTitles.size(); i++) {
            image = mDao.queryImageByOrder(i + 1, Constans.TYPE_STAY);

            itemList.add(new ItemInfoBean((image != null) ? image.getImgUrl() : "", stayTitles.get(i),
                    random.nextInt(5) + 1, System.currentTimeMillis(), stayIntroduces.get(i),
                    Constans.TYPE_STAY, random.nextInt(6000), stayAddress.get(i), "18772943998"));
        }
        return itemList;
    }

    public static List<ItemInfoBean> getLandingBanner() {
        List<ItemInfoBean> beanList;
        beanList = mDao.getHotItemInfos(3);
        return beanList;
    }

    public static List<ItemInfoBean> getRecommendInfo(String type, int count) {
        List<ItemInfoBean> beanList;
        beanList = mDao.getItemInfos(type, count);
        return beanList;
    }

    public static List<ItemInfoBean> getTrafficInfo() {
        List<ItemInfoBean> beanList = new ArrayList<>();
        List<String> trafficTitles = Arrays.asList(mContext.getResources().getStringArray(R.array.default_traffic_titles));
        List<String> trafficIntroduces = Arrays.asList(mContext.getResources().getStringArray(R.array.default_traffic_introduces));
        List<String> trafficAddress = Arrays.asList(mContext.getResources().getStringArray(R.array.default_traffic_address));

        for (int i = 0; i < trafficTitles.size(); i++) {
            beanList.add(new ItemInfoBean("file:///android_asset/traffic_" + (i + 1)+".png", trafficTitles.get(i),
                    random.nextInt(5) + 1, System.currentTimeMillis(), trafficIntroduces.get(i),
                    Constans.TYPE_TRAFFIC, random.nextInt(6000), trafficAddress.get(i), "18772943998"));
        }
        return beanList;
    }

    public static List<FilterBean> getFilterData() {
        List<FilterBean> beanList = new ArrayList<>();
        List<String> filterAreaStr = Arrays.asList(mContext.getResources()
                .getStringArray(R.array.default_area));
        List<String> filterStatStr = Arrays.asList(mContext.getResources()
                .getStringArray(R.array.default_star));
        List<String> filterPriceStr = Arrays.asList(mContext.getResources()
                .getStringArray(R.array.default_price));
        /*初始化区域筛选类*/
        FilterBean areaFilter = new FilterBean("001","区域");
        List<FilterBean> areaChilds = new ArrayList<>();
        areaChilds.add(new FilterBean("-1","全部"));
        for(int i = 0;i<filterAreaStr.size();i++){
            areaChilds.add(new FilterBean(""+i,filterAreaStr.get(i)));
        }
        areaFilter.setChildBean(areaChilds);

        /*初始化星级筛选类*/
        FilterBean starFilter = new FilterBean("002","星级");
        List<FilterBean> starChilds = new ArrayList<>();
        starChilds.add(new FilterBean("-1","全部"));
        for(int i = 0;i<filterStatStr.size();i++){
            starChilds.add(new FilterBean(""+i,filterStatStr.get(i)));
        }
        starFilter.setChildBean(starChilds);

        FilterBean priceFilter = new FilterBean("002","价格");
        List<FilterBean> priceChilds = new ArrayList<>();
        priceChilds.add(new FilterBean("-1","全部"));
        for(int i = 0;i<filterPriceStr.size();i++){
            priceChilds.add(new FilterBean(""+i,filterPriceStr.get(i)));
        }
        priceFilter.setChildBean(priceChilds);

        beanList.add(areaFilter);
        beanList.add(starFilter);
        beanList.add(priceFilter);
        return beanList;
    }
}
