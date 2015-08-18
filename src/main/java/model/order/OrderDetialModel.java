package model.order;

/**
 * Created by scar on 15/5/6.
 */
public class OrderDetialModel {

    private long detailId;
    private String orderId;
    private String prodId;
    private String priceId;
    private double prodPrice;
    private int quantity;
    private int orderStatus;
    private int deliverRules;
    private int deliverDays;
    private String deliverId;

    private String areaId;
    private String orderDate;
    private String beginDate;
    private String endDate;
    private String stopDate;
    private int status;
    //剩余配送量
    private int leftNum;
    private String prodType;
    private String prodName;
    private String prodTypeName;
    private String isGiven;

    //
    private long exchangeId;
    private String detailIds;


    @Override
    public String toString() {
        return "OrderDetialModel{" +
                "detailId=" + detailId +
                ", orderId='" + orderId + '\'' +
                ", prodId='" + prodId + '\'' +
                ", priceId='" + priceId + '\'' +
                ", prodPrice=" + prodPrice +
                ", quantity=" + quantity +
                ", orderStatus=" + orderStatus +
                ", deliverRules=" + deliverRules +
                ", deliverDays=" + deliverDays +
                ", deliverId='" + deliverId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", stopDate='" + stopDate + '\'' +
                ", status=" + status +
                ", leftNum=" + leftNum +
                ", prodType='" + prodType + '\'' +
                ", prodName='" + prodName + '\'' +
                ", prodTypeName='" + prodTypeName + '\'' +
                ", isGiven='" + isGiven + '\'' +
                '}';
    }

    public long getDetailId() {
        return detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getDeliverRules() {
        return deliverRules;
    }

    public void setDeliverRules(int deliverRules) {
        this.deliverRules = deliverRules;
    }

    public int getDeliverDays() {
        return deliverDays;
    }

    public void setDeliverDays(int deliverDays) {
        this.deliverDays = deliverDays;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLeftNum() {
        return leftNum;
    }

    public void setLeftNum(int leftNum) {
        this.leftNum = leftNum;
    }


    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdTypeName() {
        return prodTypeName;
    }

    public void setProdTypeName(String prodTypeName) {
        this.prodTypeName = prodTypeName;
    }

    public String getIsGiven() {
        return isGiven;
    }

    public void setIsGiven(String isGiven) {
        this.isGiven = isGiven;
    }

    public long getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(long exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getDetailIds() {
        return detailIds;
    }

    public void setDetailIds(String detailIds) {
        this.detailIds = detailIds;
    }
}
