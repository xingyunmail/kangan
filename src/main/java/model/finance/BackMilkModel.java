package model.finance;

import java.util.List;

/**
 * Created by zhaijinxu on 15-5-22.
 */
public class BackMilkModel {

    private String startDate ;//订单生成时间搜索条件

    private String endDate ;

    private String creatOrderDate ;//订单生成时间

    private String startDateBack ;//退款时间搜索条件

    private String endDateBack ;

    private String backDate ;//退款时间

    private String areaId ;//区域ID

    private String deliverId ;//送奶员工ID

    private String userName ;//送奶员名字

    private String reasons ;//退款原因

    private String orderId;// 订单编号

    private String custNo;//客户编号

    private String phoneNo;//联系电话

    private String address;//地址

    private Double returnTotal ;//总价

    private List<ProductModel> productModel ;

    private int startNum;

    private int limit;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Double getReturnTotal() {
        return returnTotal;
    }

    public void setReturnTotal(Double returnTotal) {
        this.returnTotal = returnTotal;
    }

    public String getCreatOrderDate() {
        return creatOrderDate;
    }

    public void setCreatOrderDate(String creatOrderDate) {
        this.creatOrderDate = creatOrderDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDateBack() {
        return startDateBack;
    }

    public void setStartDateBack(String startDateBack) {
        this.startDateBack = startDateBack;
    }

    public String getEndDateBack() {
        return endDateBack;
    }

    public void setEndDateBack(String endDateBack) {
        this.endDateBack = endDateBack;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProductModel> getProductModel() {
        return productModel;
    }

    public void setProductModel(List<ProductModel> productModel) {
        this.productModel = productModel;
    }

    @Override
    public String toString() {
        return "BackMilkModel{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", creatOrderDate='" + creatOrderDate + '\'' +
                ", startDateBack='" + startDateBack + '\'' +
                ", endDateBack='" + endDateBack + '\'' +
                ", backDate='" + backDate + '\'' +
                ", areaId='" + areaId + '\'' +
                ", deliverId='" + deliverId + '\'' +
                ", reasons='" + reasons + '\'' +
                ", orderId='" + orderId + '\'' +
                ", custNo='" + custNo + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", address='" + address + '\'' +
                ", productModel=" + productModel +
                '}';
    }
}
