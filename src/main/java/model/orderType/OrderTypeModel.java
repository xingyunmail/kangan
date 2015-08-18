package model.orderType;

import java.util.List;

/**
 * Created by zzq on 15-5-15.
 */
public class OrderTypeModel {

    private int id;
    private String name;
    private int priceType;
    private int receiveType;
    private int valid;

    private String priceTypeName;

    private List<OrderTypeModel> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public List<OrderTypeModel> getList() {
        return list;
    }

    public void setList(List<OrderTypeModel> list) {
        this.list = list;
    }

    public String getPriceTypeName() {
        return priceTypeName;
    }

    public void setPriceTypeName(String priceTypeName) {
        this.priceTypeName = priceTypeName;
    }
}
