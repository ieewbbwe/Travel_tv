package com.wisesoft.traveltv.model.temp;

import android.content.Context;

import com.android_mobile.net.response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wisesoft.traveltv.R;
import com.wisesoft.traveltv.constants.Constans;
import com.wisesoft.traveltv.db.DataBaseDao;
import com.wisesoft.traveltv.model.KeyBoardItemBean;
import com.wisesoft.traveltv.utils.Utils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static com.wisesoft.traveltv.utils.Utils.readTextfileFromAssets;

/**
 * Created by mxh on 2017/9/15.
 * Describe：
 */

public class DataEngine {

    public static Context mContext;
    private static DataBaseDao mDao;
    private static List<KeyBoardItemBean> keyBoardItemBeans;

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
                    Constans.TYPE_EAT, random.nextInt(6000), eatAddress.get(i), "18772943998", image.getOrder()));
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
        List<ItemInfoBean> beanList = new ArrayList<>();
        //beanList = mDao.getHotItemInfos(3);
        beanList.add(new ItemInfoBean(R.mipmap.ic_home_bg_01, Constans.TYPE_PLAY));
        beanList.add(new ItemInfoBean(R.mipmap.ic_home_bg_02, Constans.TYPE_EAT));
        beanList.add(new ItemInfoBean(R.mipmap.ic_home_bg_03, Constans.TYPE_STAY));
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
            beanList.add(new ItemInfoBean("file:///android_asset/traffic_" + (i + 1) + ".png", trafficTitles.get(i),
                    random.nextInt(5) + 1, System.currentTimeMillis(), trafficIntroduces.get(i),
                    Constans.TYPE_TRAFFIC, random.nextInt(6000), trafficAddress.get(i), "18772943998"));
        }
        return beanList;
    }

    public static List<InitDataBean> getFilterData(String type) {
        switch (type) {
            case Constans.TYPE_PLAY:
                return getPlayFilterData();
            case Constans.TYPE_STAY:
                return getStayFilterData();
            case Constans.TYPE_EAT:
            default:
                return null;
        }
    }

    public static List<InitDataBean> getStayFilterData() {
        List<InitDataBean> beanList = new ArrayList<>();
        List<String> filterAreaStr = Arrays.asList(mContext.getResources()
                .getStringArray(R.array.default_area));
        List<String> filterStatStr = Arrays.asList(mContext.getResources()
                .getStringArray(R.array.default_star));
        List<String> filterPriceStr = Arrays.asList(mContext.getResources()
                .getStringArray(R.array.default_price));
        /*初始化区域筛选类*/
        InitDataBean areaFilter = new InitDataBean("001", "区域");
        List<InitDataBean> areaChilds = new ArrayList<>();
        areaChilds.add(new InitDataBean("-1", "全部"));
        for (int i = 0; i < filterAreaStr.size(); i++) {
            areaChilds.add(new InitDataBean("" + i, filterAreaStr.get(i)));
        }
        areaFilter.setChildBean(areaChilds);

        /*初始化星级筛选类*/
        InitDataBean starFilter = new InitDataBean("002", "星级");
        List<InitDataBean> starChilds = new ArrayList<>();
        starChilds.add(new InitDataBean("-1", "全部"));
        for (int i = 0; i < filterStatStr.size(); i++) {
            starChilds.add(new InitDataBean("" + i, filterStatStr.get(i)));
        }
        starFilter.setChildBean(starChilds);

        InitDataBean priceFilter = new InitDataBean("002", "价格");
        List<InitDataBean> priceChilds = new ArrayList<>();
        priceChilds.add(new InitDataBean("-1", "全部"));
        for (int i = 0; i < filterPriceStr.size(); i++) {
            priceChilds.add(new InitDataBean("" + i, filterPriceStr.get(i)));
        }
        priceFilter.setChildBean(priceChilds);

        beanList.add(areaFilter);
        beanList.add(starFilter);
        beanList.add(priceFilter);
        return beanList;
    }

    public static List<ImageBean> getHotelImages() {
        List<ImageBean> beanList = new ArrayList<>();
        ImageBean image = null;
        try {
            String[] fileNames = mContext.getAssets().list("hotel");
            for (String str : fileNames) {
                String[] strs = str.split("_");
                image = new ImageBean(strs[2].split("\\.")[0],
                        "file:///android_asset/hotel/" + str, strs[0], strs[1]);
                beanList.add(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return beanList;
    }

    //放入所有 吃相关 酒店的食品图片
    public static List<ItemInfoBean> getHotelFoodInfos() {
        List<ItemInfoBean> itemList = new ArrayList<>();
        Random random = new Random();
        List<String> titles = Arrays.asList(mContext.getResources()
                .getStringArray(R.array.default_hotel_food_title));
        int count = 0;
        for (int i = 0; i < titles.size(); i++) {
            List<ImageBean> imageBeen = mDao.queryImageByHotelOrder("" + (i + 1));
            for (ImageBean item : imageBeen) {
                itemList.add(new ItemInfoBean((item != null) ? item.getImgUrl() : "", titles.get(count),
                        random.nextInt(5) + 1, System.currentTimeMillis(), "",
                        Constans.TYPE_HOTEL_FOOD, random.nextInt(6000), "湖北省宜昌市点军区土城乡宜昌车溪民俗风景区",
                        "18772943998", (item != null) ? item.getHotelId() : ""));
                count++;
            }
        }
        return itemList;
    }

    //根据酒店id查找相关图片条目
    public static List<ItemInfoBean> getHotelFoodInfo(String hotel_id) {
        List<ItemInfoBean> itemList = new ArrayList<>();
        List<ItemInfoBean> been = mDao.queryItemInfoByHotelId(hotel_id);
        //由於圖片不夠，因此循環幾遍顯示
        for (int i = 0; i < 3; i++) {
            itemList.addAll(been);
        }
        return itemList;
    }

    public static List<InitDataBean> getPlayFilterData() {
        List<InitDataBean> beanList = new ArrayList<>();
         /*初始化景观筛选类*/
        InitDataBean landScapeFilter = new InitDataBean("004", "类型");
        List<InitDataBean> landScapeChilds = new ArrayList<>();
        landScapeChilds.add(new InitDataBean("-1", "全部"));
        landScapeChilds.add(new InitDataBean("0", "自然景观"));
        landScapeChilds.add(new InitDataBean("1", "人文景观"));
        landScapeFilter.setChildBean(landScapeChilds);
        beanList.add(landScapeFilter);
        return beanList;
    }

    public static List<ItemInfoBean> getSomeTestItemInfo(int size) {
        Random random = new Random();
        String[] imgs = {"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515254339536&di=e64494a115f921f8028989e4be2260ee&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fd000baa1cd11728bce0a8de8c1fcc3cec2fd2cd0.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515254368531&di=774aa62901018326025b73b88087e13c&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fa50f4bfbfbedab6438b9c80afe36afc378311e34.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515254368529&di=8c8b1d6d681ea1ee8de4730df34ca88b&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fb8389b504fc2d5624b744a27ee1190ef77c66cf8.jpg"};

        List<ItemInfoBean> itemInfoBeen = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            itemInfoBeen.add(new ItemInfoBean(imgs[random.nextInt(3)], "测试数据" + i, random.nextInt(5), System.currentTimeMillis(), "介绍信息！！！！！"
                    , Constans.TYPE_EAT, random.nextInt(1000), "听说地址要长！！！！！！", "18772943998"));
        }
        return itemInfoBeen;
    }

    public static List<ItemInfoBean> getItemInfoFromLocal(Context context,String fileName){
        String json  = Utils.readTextfileFromAssets(context,fileName);
        Gson gson = new Gson();
        Type jsonType = new TypeToken<List<ItemInfoBean>>() {
        }.getType();
        return gson.fromJson(json, jsonType);
    }

    public static List<KeyBoardItemBean> getKeyBrodData() {
        if(keyBoardItemBeans == null){
            keyBoardItemBeans = new ArrayList<>();
            KeyBoardItemBean itemBean;
            //创建A~Z
            for (int i = 65; i < 91; i++) {
                itemBean = new KeyBoardItemBean((char) i);
                keyBoardItemBeans.add(itemBean);
            }
            //创建0~9
            for (int i = 48; i < 58; i++) {
                itemBean = new KeyBoardItemBean((char) i);
                keyBoardItemBeans.add(itemBean);
            }
        }
        return keyBoardItemBeans;
    }
}
