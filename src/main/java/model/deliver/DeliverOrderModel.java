package model.deliver;

/**
 * Created by lining on 15-5-18.
 */
public class DeliverOrderModel {


    private int id;

    /*开始时间*/
    private String startDate;

    /*结束时间*/
    private String endDate;

    /*查询区域*/
    private String orderArea;

    private String areaName;

    /*订单编号*/
    private String orderId;

    /*送奶员ID*/
    private String userId;

    /*送奶员姓名*/
    private String userName;

    /*产品编号*/
    private String prodId;

    /*订单类型*/
    private String orderType;

    /*客户编号*/
    private String custId;

    /*客户姓名*/
    private String custName;

    /*联系电话*/
    private String phone;

    /*地址*/
    private String address;

    /*商品名称*/
    private String prodName;

    /*变动类型*/
    private String changeType;

    /*变动*/
    private String change;

    /*余量*/
    private int rest;


    /*配送数量*/
    private int number;

    /*产品类型：商品奶、赠奶*/
    private int prodType;

    private int deliverRules;

    /*分页参数*/
    private int startNum;
    private int limit;



    /*订单状态：启、停、转、换、退*/
    private int orderStatus;

    /*订单状态：启、停、转、换、退*/
    private int status;


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

    public String getOrderArea() {
        return orderArea;
    }

    public void setOrderArea(String orderArea) {
        this.orderArea = orderArea;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }


    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getProdType() {
        return prodType;
    }

    public void setProdType(int prodType) {
        this.prodType = prodType;
    }

    public int getDeliverRules() {
        return deliverRules;
    }

    public void setDeliverRules(int deliverRules) {
        this.deliverRules = deliverRules;
    }
}
