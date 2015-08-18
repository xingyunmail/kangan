package model.order;

import java.util.Date;

/**
 * Created by scar on 15/5/18.
 */
public class OrderExpireModel {
    private String startDate;
    private String endDate;

    private String leftMin;
    private String leftMax;
    private String orderId;

    private String areaId;
    private String areaName;
    private String userId;
    private String userName;
    private String custName;
    private String custId;
    private String custPhone;
    private String custAddr;
    private int leftCount;

    private int searchType;

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
        return "OrderExpireModel{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", leftMin='" + leftMin + '\'' +
                ", leftMax='" + leftMax + '\'' +
                ", orderId='" + orderId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", custName='" + custName + '\'' +
                ", custId='" + custId + '\'' +
                ", custPhone='" + custPhone + '\'' +
                ", custAddr='" + custAddr + '\'' +
                ", leftCount=" + leftCount +
                ", searchType=" + searchType +
                '}';
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

    public String getLeftMin() {
        return leftMin;
    }

    public void setLeftMin(String leftMin) {
        this.leftMin = leftMin;
    }

    public String getLeftMax() {
        return leftMax;
    }

    public void setLeftMax(String leftMax) {
        this.leftMax = leftMax;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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

    public int getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(int leftCount) {
        this.leftCount = leftCount;
    }

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }
}
