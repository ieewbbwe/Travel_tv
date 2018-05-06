package com.wisesoft.traveltv.net.request;

/**
 * Created by picher on 2018/1/7.
 * Describe：
 */

public class ItemRequestModel {

    private String area;//区域
    private String star;//星级
    private String sight;//景点类型
    private String food_type;//事物类型
    private String price;//人均价格
    private float p_h;//最高价
    private float p_low;//最低价

    //购物类型
    private String pay_type;
    //娱乐类型
    private String fun_type;

    public ItemRequestModel() {
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getFun_type() {
        return fun_type;
    }

    public void setFun_type(String fun_type) {
        this.fun_type = fun_type;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public void setSight(String sight) {
        this.sight = sight;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setP_h(float p_h) {
        this.p_h = p_h;
    }

    public void setP_low(float p_low) {
        this.p_low = p_low;
    }

    public String getArea() {
        return area;
    }

    public String getStar() {
        return star;
    }

    public String getSight() {
        return sight;
    }

    public String getFood_type() {
        return food_type;
    }

    public String getPrice() {
        return price;
    }

    public float getP_h() {
        return p_h;
    }

    public float getP_low() {
        return p_low;
    }

    private ItemRequestModel(String area, String star, String sight, String food_type, String price, float p_h, float p_low) {
        this.area = area;
        this.star = star;
        this.sight = sight;
        this.food_type = food_type;
        this.price = price;
        this.p_h = p_h;
        this.p_low = p_low;
    }

    public static class Builder {
        private String area;//区域
        private String star;//星级
        private String sight;//景点类型
        private String food_type;//事物类型
        private String price;//人均价格
        private float p_h;//最高价
        private float p_low;//最低价

        public Builder setArea(String area) {
            this.area = area;
            return this;
        }

        public Builder setStar(String star) {
            this.star = star;
            return this;
        }

        public Builder setSight(String sight) {
            this.sight = sight;
            return this;
        }

        public Builder setFood_type(String food_type) {
            this.food_type = food_type;
            return this;
        }

        public Builder setPrice(String price) {
            this.price = price;
            return this;
        }

        public Builder setP_h(float p_h) {
            this.p_h = p_h;
            return this;
        }

        public Builder setP_low(float p_low) {
            this.p_low = p_low;
            return this;
        }

        public ItemRequestModel builde() {
            return new ItemRequestModel(area, star, sight, food_type, price, p_h, p_low);
        }
    }
}
