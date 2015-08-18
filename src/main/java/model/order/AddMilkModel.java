package model.order;

import java.util.Date;
import java.util.List;

/**
 * Created by sunhao on 15/5/22.
 */
public class AddMilkModel {

    private long addMilkId ;

    private String prodId ;

    private int milkNum;

    private String userId;

    private Date addMonth;

    private int leftMilkNum;

    private Date insertdate;

    private List<ApplyModel> applyModelList;

    public long getAddMilkId() {
        return addMilkId;
    }

    public void setAddMilkId(long addMilkId) {
        this.addMilkId = addMilkId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getMilkNum() {
        return milkNum;
    }

    public void setMilkNum(int milkNum) {
        this.milkNum = milkNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getAddMonth() {
        return addMonth;
    }

    public void setAddMonth(Date addMonth) {
        this.addMonth = addMonth;
    }

    public int getLeftMilkNum() {
        return leftMilkNum;
    }

    public void setLeftMilkNum(int leftMilkNum) {
        this.leftMilkNum = leftMilkNum;
    }

    public Date getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(Date insertdate) {
        this.insertdate = insertdate;
    }

    public List<ApplyModel> getApplyModelList() {
        return applyModelList;
    }

    public void setApplyModelList(List<ApplyModel> applyModelList) {
        this.applyModelList = applyModelList;
    }
}
