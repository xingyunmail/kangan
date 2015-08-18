package model.order;

import java.util.List;

/**
 * Created by sunhao on 15/7/20.
 */
public class QueryOrderModel {

    private String orderId;

    private String orderType;

    private String custId;

    private String custName;

    private String custPhone;

    private String custAddr;

    private double orderPrice;

    private String orderDate;

    private String originalOrder;

    private String deliverId;

    private String deliverName;

    private String detail;

    private List<OrderDetialModel> orderDetail;

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

    public String getOriginalOrder() {
        return originalOrder;
    }

    public void setOriginalOrder(String originalOrder) {
        this.originalOrder = originalOrder;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<OrderDetialModel> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetialModel> orderDetail) {
        this.orderDetail = orderDetail;
    }


    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }
}
