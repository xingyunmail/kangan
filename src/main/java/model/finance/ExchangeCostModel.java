package model.finance;

import model.product.ProdModel;

import java.util.Date;
import java.util.List;

/**
 * Created by scar on 15/5/20.
 */
public class ExchangeCostModel {

    private int exchangeId;

    /*共用属性*/
    private String orderId;

    /*变动开始时间*/
    private String startDate;

    /*变动结束时间*/
    private String endDate;

    /*换奶时间*/
    private String changeDate;

    /*共用属性*/
    private String diffPrice;

    /*送奶员Id*/
    private String deliverId;

    /*送奶员名字*/
    private String DeliverName;

    /*区域*/
    private String orderArea;

    /*订单类型*/
    private String orderType;

    //差额是否小于0的标识 0：未选择差额小于0,1：选中了差额小于0
    private int isLessZero;

    /*变动订单数*/
    private int exchangeOrderCount;

    /*****************************************/

    //换奶详细页面model

    /*客户Id*/
    private String custId;

    /*客户姓名*/
    private String custName;

    /*客户地址*/
    private String custAddr;

    /*原先的奶品*/
    private String preProd;

    /*原先奶品剩余量*/
    private String preRest;

    /*换后的奶品*/
    private String newProd;

    /*换后的奶品数量*/
    private int  newCount;

    /*总差额*/
    private String diffCount;

    /*原先的奶品*/
    private List<ProdModel> oldProds;

    /*换后的奶品*/
    private List<ProdModel> newProds;

    private List<ExchangeCostModel> exchangeCostModels;


    public int getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getDiffPrice() {
        return diffPrice;
    }

    public void setDiffPrice(String diffPrice) {
        this.diffPrice = diffPrice;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getDeliverName() {
        return DeliverName;
    }

    public void setDeliverName(String deliverName) {
        DeliverName = deliverName;
    }

    public int getExchangeOrderCount() {
        return exchangeOrderCount;
    }

    public void setExchangeOrderCount(int exchangeOrderCount) {
        this.exchangeOrderCount = exchangeOrderCount;
    }

    public String getOrderArea() {
        return orderArea;
    }

    public void setOrderArea(String orderArea) {
        this.orderArea = orderArea;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public String getCustAddr() {
        return custAddr;
    }

    public void setCustAddr(String custAddr) {
        this.custAddr = custAddr;
    }

    public String getPreProd() {
        return preProd;
    }

    public void setPreProd(String preProd) {
        this.preProd = preProd;
    }

    public String getPreRest() {
        return preRest;
    }

    public void setPreRest(String preRest) {
        this.preRest = preRest;
    }

    public String getNewProd() {
        return newProd;
    }

    public void setNewProd(String newProd) {
        this.newProd = newProd;
    }

    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

    public String getDiffCount() {
        return diffCount;
    }

    public void setDiffCount(String diffCount) {
        this.diffCount = diffCount;
    }

    public List<ProdModel> getOldProds() {
        return oldProds;
    }

    public void setOldProds(List<ProdModel> oldProds) {
        this.oldProds = oldProds;
    }

    public List<ProdModel> getNewProds() {
        return newProds;
    }

    public void setNewProds(List<ProdModel> newProds) {
        this.newProds = newProds;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public List<ExchangeCostModel> getExchangeCostModels() {
        return exchangeCostModels;
    }

    public void setExchangeCostModels(List<ExchangeCostModel> exchangeCostModels) {
        this.exchangeCostModels = exchangeCostModels;
    }

    public int getIsLessZero() {
        return isLessZero;
    }

    public void setIsLessZero(int isLessZero) {
        this.isLessZero = isLessZero;
    }
}
