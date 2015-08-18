package model.base.product;

/**
 * Created by ZH on 2015/8/14.
 */
public class Product {
    private int id;
    private String prodId;
    private String prodName;
    private String prodweight;
    private String prodPakage;
    private String prodStatus;
    private String insertDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdweight() {
        return prodweight;
    }

    public void setProdweight(String prodweight) {
        this.prodweight = prodweight;
    }

    public String getProdPakage() {
        return prodPakage;
    }

    public void setProdPakage(String prodPakage) {
        this.prodPakage = prodPakage;
    }

    public String getProdStatus() {
        return prodStatus;
    }

    public void setProdStatus(String prodStatus) {
        this.prodStatus = prodStatus;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }
}
