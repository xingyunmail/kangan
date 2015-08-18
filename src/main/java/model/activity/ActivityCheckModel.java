package model.activity;

import model.finance.ProductModel;

import java.util.List;

/**
 * Created by yu on 15-5-28.
 */
public class ActivityCheckModel {

    private int discountId;
    private List<ProductModel> productModels;
    private int multiple;
    private int giftNum;

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public List<ProductModel> getProductModels() {
        return productModels;
    }

    public void setProductModels(List<ProductModel> productModels) {
        this.productModels = productModels;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }
}
