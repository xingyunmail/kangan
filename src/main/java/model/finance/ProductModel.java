package model.finance;

/**
 * Created by zhaijinxu on 15-5-26.
 * 退奶单商品详情
 */
public class ProductModel {

    private String prodId ;

    private String prodName ;

    private int number ;

    private Double price ;

    private String orderId ;

    private int prodType;

    private int priceId;

    private int quantity;

    @Override
    public String toString() {
        return "ProductModel{" +
                "prodId='" + prodId + '\'' +
                ", prodName='" + prodName + '\'' +
                ", number=" + number +
                ", price=" + price +
                ", orderId='" + orderId + '\'' +
                ", prodType=" + prodType +
                ", priceId=" + priceId +
                ", quantity=" + quantity +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getProdType() {
        return prodType;
    }

    public void setProdType(int prodType) {
        this.prodType = prodType;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
