package DAO;

import DAO.Activity.ActivityDaoImpl;
import model.activity.ActivityCheckModel;
import model.activity.ActivityModel;
import model.activity.ActivityVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ZH on 2015/5/6.
 */
@Repository
public interface ActivityDao {

//    @Select("select discountId,discountName,discountType,discountInfo,isAdd,beginDate,endDate,insertDate,discountStatus from discount_info " +
//            "where  discountName=#{discountName} and beginDate=#{beginDate} and endDate=#{endDate} and discountType=#{discountType} and discountStatus=#{discountStatus} ")
//    public List<ActivityModel> getList(ActivityModel activityModel);

    @SelectProvider(type = ActivityDaoImpl.class, method = "list")
    public List<ActivityModel> getList(ActivityModel activityModel);
    @SelectProvider(type = ActivityDaoImpl.class, method = "listcount")
    public int getListCount(ActivityModel activityModel);
    //    @Update("update discount_info set endDate = #{endDate} where discountId = #{discountId}")
    @UpdateProvider(type = ActivityDaoImpl.class, method = "updateModel")
    public Integer updateModel(ActivityModel activityModel);

    @Select("select dp.discountId,dp.prodId,dp.quantity,dp.isGift,dp.giftId,pi.prodName from  discount_prods dp left join product_info pi on  pi.prodId=dp.prodId where pi.prodId=#{prodId}")
    public ActivityModel findActivityModelByProdId(ActivityModel activityModel);

    @Insert("insert into discount_info (discountName,discountType,discountInfo,isAdd,giftNum,beginDate,endDate,insertDate,discountStatus) " +
            "values (#{discountName},#{discountType},#{discountInfo},#{isAdd},#{giftNum},#{beginDate},#{endDate},now(),1)")
    @SelectKey(statement = "select LAST_INSERT_ID() as discountId", keyProperty = "discountId", resultType = Integer.class, before = false)
     public int addActivityModel(ActivityModel activityModel);

    @Select("select di.discountId discountId,di.giftNum giftNum,di.discountType discountType,di.discountName discountName,di.discountInfo discountInfo,di.isAdd iaAdd,di.beginDate beginDate,di.endDate endDate,\n" +
            " dp.quantity quantity,dp.isGift isGift,pi.prodId prodId,pi.prodName prodName,pri.priceId priceId ,pri.prodType ,pri.priceType priceType,pri.valid valid,pri.customer customer,pri.price price " +
            " from discount_info di " +
            " LEFT JOIN discount_prods dp on  di.discountId=dp.discountId " +
            " LEFT JOIN product_info pi on dp.prodId = pi.prodId  " +
            " LEFT JOIN price_info pri on pri.prodId = pi.prodId  " +
            " where ifnull(pri.priceType,1) = 1" +
            " AND ifnull(pri.valid,1) = 1 and  di.discountId=#{discountId} and pri.customer=0 GROUP BY prodName,isGift ")
    public List<ActivityVO> editActivityMessage(ActivityModel activityModel);

    @Update("update discount_info set discountName=#{discountName},discountType=#{discountType},discountInfo=#{discountInfo},isAdd=#{isAdd},giftNum=#{giftNum},insertDate=now()," +
            "beginDate=#{beginDate},endDate=#{endDate},discountStatus=1 where discountId=#{editdiscountId}")
    public int updateModelInfo(ActivityModel activityModel);

    @Select("select * from discount_info where discountId = #{discountId} and discountStatus = 1")
    public ActivityModel getActivityInfo(ActivityCheckModel checkModel);
}
