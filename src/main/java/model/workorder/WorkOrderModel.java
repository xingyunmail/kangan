package model.workorder;

import java.util.List;

/**
 * Created by zzq on 15-5-7.
 */
public class WorkOrderModel {
    private int id;
    private String workNum;
    private int type;
    private String customerName;
    private String customerPhone;
    private String milkUser;
    private String address;
    private String areaName;
    private int compID;
    private String products;
    private String remark;
    private String editRemark;
    private int status;
    private String itemDiscrible;
    private String complains;
    private String createTime;

    private String userName;

    private List<WorkOrderModel> workOrderModelList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkNum() {
        return workNum;
    }

    public void setWorkNum(String workNum) {
        this.workNum = workNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMilkUser() {
        return milkUser;
    }

    public void setMilkUser(String milkUser) {
        this.milkUser = milkUser;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getCompID() {
        return compID;
    }

    public void setCompID(int compID) {
        this.compID = compID;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEditRemark() {
        return editRemark;
    }

    public void setEditRemark(String editRemark) {
        this.editRemark = editRemark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<WorkOrderModel> getWorkOrderModelList() {
        return workOrderModelList;
    }

    public void setWorkOrderModelList(List<WorkOrderModel> workOrderModelList) {
        this.workOrderModelList = workOrderModelList;
    }

    public String getItemDiscrible() {
        return itemDiscrible;
    }

    public void setItemDiscrible(String itemDiscrible) {
        this.itemDiscrible = itemDiscrible;
    }

    public String getComplains() {
        return complains;
    }

    public void setComplains(String complains) {
        this.complains = complains;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
