package model.activity;

import java.util.List;

/**
 * Created by ZH on 2015/5/6.
 */

public class ActivityModel {
    private Integer discountId;
    private String  discountName;
    private String  discountInfo;
    private String  isAdd;
    private String  beginDate;
    private String  endDate;
    private String  insertDate;
    private String  discountType;
    private String  discountStatus;

    private String prodName;
    private String prodId;

    private String quantity;
    private String batchprodId;     //批量产品ID
    private String batchGiftProdId;//批量赠品ID
    private String editdiscountId;  //需要保存的活动ID
    private String priceId;          //价格ID
    private int giftNum;            //赠品数量
    private String priceIdGift;     //批量赠品价格ID

    private List<ActivityProdsModel> prodList;//活动中的 商品列表
    private List<ActivityProdsModel> giftList;//活动中的 赠品列表

    private int startNum;
    private int limit;

    public String getPriceIdGift() {
        return priceIdGift;
    }

    public void setPriceIdGift(String priceIdGift) {
        this.priceIdGift = priceIdGift;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }

    public List<ActivityProdsModel> getProdList() {
        return prodList;
    }

    public void setProdList(List<ActivityProdsModel> prodList) {
        this.prodList = prodList;
    }

    public List<ActivityProdsModel> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<ActivityProdsModel> giftList) {
        this.giftList = giftList;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public String getBatchprodId() {
        return batchprodId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public String getEditdiscountId() {
        return editdiscountId;
    }

    public void setEditdiscountId(String editdiscountId) {
        this.editdiscountId = editdiscountId;
    }

    public void setBatchprodId(String batchprodId) {
        this.batchprodId = batchprodId;
    }

    public String getBatchGiftProdId() {
        return batchGiftProdId;
    }

    public void setBatchGiftProdId(String batchGiftProdId) {
        this.batchGiftProdId = batchGiftProdId;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDiscountInfo() {
        return discountInfo;
    }

    public void setDiscountInfo(String discountInfo) {
        this.discountInfo = discountInfo;
    }

    public String getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(String isAdd) {
        this.isAdd = isAdd;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getDiscountStatus() {
        return discountStatus;
    }

    public void setDiscountStatus(String discountStatus) {
        this.discountStatus = discountStatus;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
}
