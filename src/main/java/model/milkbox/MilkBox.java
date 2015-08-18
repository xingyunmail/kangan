package model.milkbox;

/**
 * Created by sunhao on 15/5/8.
 */
public class MilkBox {

    private String boxId;

    private String newStatus;

    private String customeId;

    private String boxStatus;


    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getBoxStatus() {
        return boxStatus;
    }

    public void setBoxStatus(String boxStatus) {
        this.boxStatus = boxStatus;
    }

    public String getCustomeId() {
        return customeId;
    }

    public void setCustomeId(String customeId) {
        this.customeId = customeId;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}
