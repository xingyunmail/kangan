package model.order;

import java.util.List;

/**
 * Created by sunhao on 15/4/30.
 */
public class OrderModel {

    private String orderId;

    private String custId;

    private String custName;

    private String discountId;

    private String orderType;

    private String custPhone;

    private String custAddr;

    private String deliverId;

    private String orderStatus;

    private String originalOrder;

    private List<OrderDetialModel> detialList;

    private String orderDate;

    private String orderDateEnd;

    private String areaId;


    private double orderPrice;

    private String products;

    private String userName;

    private String milkBoxId;

    private String orderTypeName;

    private String addUserId;

    private String addUserName;

    private int startNum;

    private int limit;

    @Override
    public String toString() {
        return "OrderModel{" +
                "orderId='" + orderId + '\'' +
                ", custId='" + custId + '\'' +
                ", custName='" + custName + '\'' +
                ", discountId='" + discountId + '\'' +
                ", orderType='" + orderType + '\'' +
                ", custPhone='" + custPhone + '\'' +
                ", custAddr='" + custAddr + '\'' +
                ", deliverId='" + deliverId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", originalOrder='" + originalOrder + '\'' +
                ", detialList=" + detialList +
                ", orderDate='" + orderDate + '\'' +
                ", areaId='" + areaId + '\'' +
                ", orderPrice=" + orderPrice +
                ", products='" + products + '\'' +
                ", userName='" + userName + '\'' +
                ", milkBoxId='" + milkBoxId + '\'' +
                ", orderTypeName='" + orderTypeName + '\'' +
                ", addUserId='" + addUserId + '\'' +
                ", addUserName='" + addUserName + '\'' +
                '}';
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

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustAddr() {
        return custAddr;
    }

    public void setCustAddr(String custAddr) {
        this.custAddr = custAddr;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOriginalOrder() {
        return originalOrder;
    }

    public void setOriginalOrder(String originalOrder) {
        this.originalOrder = originalOrder;
    }

    public List<OrderDetialModel> getDetialList() {
        return detialList;
    }

    public void setDetialList(List<OrderDetialModel> detialList) {
        this.detialList = detialList;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMilkBoxId() {
        return milkBoxId;
    }

    public void setMilkBoxId(String milkBoxId) {
        this.milkBoxId = milkBoxId;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(String addUserId) {
        this.addUserId = addUserId;
    }

    public String getAddUserName() {
        return addUserName;
    }

    public void setAddUserName(String addUserName) {
        this.addUserName = addUserName;
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

    public String getOrderDateEnd() {
        return orderDateEnd;
    }

    public void setOrderDateEnd(String orderDateEnd) {
        this.orderDateEnd = orderDateEnd;
    }
}
