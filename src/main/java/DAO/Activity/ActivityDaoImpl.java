package DAO.Activity;

import model.activity.ActivityModel;

/**
 * Created by ZH on 2015/5/6.
 */
public class ActivityDaoImpl {

    public String list(ActivityModel activityModel) {
        String sql = "select discountId,discountName,discountType,discountInfo,isAdd,beginDate,endDate,insertDate,discountStatus from discount_info";
        String sqlstr = "";
        if(activityModel.getDiscountStatus()!=null && activityModel.getDiscountStatus()!=""){
            if(activityModel.getDiscountStatus().equals("all")){
                sqlstr += " where discountStatus = 1 and beginDate<='" +activityModel.getBeginDate() +"' and endDate>='"+ activityModel.getBeginDate() +"'" ;
            }
        }else{
            if (activityModel.getDiscountType() != null) {
                sqlstr += "  where discountType=" + activityModel.getDiscountType();
            }
            if (activityModel.getDiscountStatus() != null) {
                sqlstr += "  and discountStatus=" + activityModel.getDiscountStatus();
            }
            if (activityModel.getDiscountName() != null && activityModel.getDiscountName()!="") {
                sqlstr += "  and discountName like '%" + activityModel.getDiscountName()+"%'";
            }
            if (activityModel.getBeginDate() != null && activityModel.getBeginDate()!="") {
                sqlstr += "  and beginDate <='" + activityModel.getBeginDate()+"'";
            }
            if (activityModel.getEndDate() != null && activityModel.getEndDate()!="") {
                sqlstr += "  and endDate >='" + activityModel.getEndDate()+"'";
            }
        }
        if (activityModel.getLimit() > 0) {
            sql += " limit " + (activityModel.getStartNum() - 1) * activityModel.getLimit() + "," + activityModel.getLimit();
        }
        return sql + sqlstr;

    }
    public String listcount(ActivityModel activityModel) {
        String sql = "select count(*) from discount_info";
        String sqlstr = "";
        if(activityModel.getDiscountStatus()!=null && activityModel.getDiscountStatus()!=""){
            if(activityModel.getDiscountStatus().equals("all")){
                sqlstr += " where discountStatus = 1 and beginDate<='" +activityModel.getBeginDate() +"' and endDate>='"+ activityModel.getBeginDate() +"'" ;
            }
        }else{
            if (activityModel.getDiscountType() != null) {
                sqlstr += "  where discountType=" + activityModel.getDiscountType();
            }
            if (activityModel.getDiscountStatus() != null) {
                sqlstr += "  and discountStatus=" + activityModel.getDiscountStatus();
            }
            if (activityModel.getDiscountName() != null && activityModel.getDiscountName()!="") {
                sqlstr += "  and discountName like '%" + activityModel.getDiscountName()+"%'";
            }
            if (activityModel.getBeginDate() != null && activityModel.getBeginDate()!="") {
                sqlstr += "  and beginDate <='" + activityModel.getBeginDate()+"'";
            }
            if (activityModel.getEndDate() != null && activityModel.getEndDate()!="") {
                sqlstr += "  and endDate >='" + activityModel.getEndDate()+"'";
            }
        }

        return sql + sqlstr;

    }


    public String updateModel(ActivityModel activityModel){
        String sql = "update discount_info set ";
        String str = "";
        //1 进行中 0 已停止
        if(activityModel.getDiscountStatus().equals("open")){
            str = "discountStatus = 1";
        }else  if(activityModel.getDiscountStatus().equals("close")){
            str = "discountStatus = 0";
        }else  if(activityModel.getDiscountStatus().equals("update")){//延期
            str = " endDate = '"+activityModel.getEndDate()+"' , discountStatus = 1";
        }
        str += " where discountId="+activityModel.getDiscountId();
        return sql + str;
    }
    public String getProdIsGift(ActivityModel activityModel){
        String sql = "select dp.id id,dp.discountId discountId,dp.prodId prodId,dp.quantity quantity,dp.isGift isGift,dp.giftId giftId," +
                " dp.priceId priceId,pi.prodName prodName from discount_prods dp " +
                " LEFT JOIN product_info pi on pi.prodId = dp.prodId";
        if(activityModel.getDiscountId()!=null){
            sql += " where dp.discountId="+activityModel.getDiscountId();
        }
        //way 1 赠品 0 正常商品
//        if(way.equals("0")){
//            sql += " and dp.isGift = 0";
//        }else if(way.equals("1")){
//            sql += " and dp.isGift = 1";
//        }
        return sql;
    }
}
