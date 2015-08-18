package model.finance;

import java.util.Date;

/**
 * Created by scar on 15/5/20.
 */
public class FinanceModel {

    private Date startDate;
    private Date endDate;

    private String areaId;

    private String userId;

    private String areaName;
    private String userName;
    private String orderCounts;
    private String priceCounts;


    private String orderId;
    private String custId;
    private String custAddr;
    private String custPhone;
    private String prodName;
    private String prodId;
    private String product;
    private String price;
    private String prices;
    private String products;
    private String totalPrice;

    private int startNum;
    private int limit;

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

    @Override
    public String toString() {
        return "FinanceModel{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", areaId=" + areaId +
                ", userId='" + userId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", userName='" + userName + '\'' +
                ", orderCounts='" + orderCounts + '\'' +
                ", priceCounts='" + priceCounts + '\'' +
                ", orderId='" + orderId + '\'' +
                ", custId='" + custId + '\'' +
                ", custAddr='" + custAddr + '\'' +
                ", custPhone='" + custPhone + '\'' +
                ", prodName='" + prodName + '\'' +
                ", prodId='" + prodId + '\'' +
                ", product='" + product + '\'' +
                ", price='" + price + '\'' +
                ", prices='" + prices + '\'' +
                ", products='" + products + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                '}';
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderCounts() {
        return orderCounts;
    }

    public void setOrderCounts(String orderCounts) {
        this.orderCounts = orderCounts;
    }

    public String getPriceCounts() {
        return priceCounts;
    }

    public void setPriceCounts(String priceCounts) {
        this.priceCounts = priceCounts;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustAddr() {
        return custAddr;
    }

    public void setCustAddr(String custAddr) {
        this.custAddr = custAddr;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
