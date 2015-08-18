package model.deliver;


import controller.User;
import model.area.AreaModel;
import model.product.ProdModel;
import model.user.UserModel;

import java.util.List;

/**
 * Created by lining on 15-5-13.
 */
public class ReceiveMilkOrderModel {

    /*送奶员编号*/
    private String userId;

    /*送奶员名字*/
    private String userName;

    /*产品编号*/
    private String prodId;

    /*合计*/
    private String number;

    /*回瓶数*/
    private String prodPackage;

    /*地区--查询条件*/
    private String orderArea;

    /*日期--查询条件*/
    private String orderDate;

    /*产品名称*/
    private String prodName;

    /*表头--商品名字*/
    private List<String> prodNames;

    /*表列 --送奶员*/
    private List<String> userNames;






    /*分页参数*/
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

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProdPackage() {
        return prodPackage;
    }

    public void setProdPackage(String prodPackage) {
        this.prodPackage = prodPackage;
    }

    public String getOrderArea() {
        return orderArea;
    }

    public void setOrderArea(String orderArea) {
        this.orderArea = orderArea;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }


}
