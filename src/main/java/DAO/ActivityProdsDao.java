package DAO;

import DAO.Activity.ActivityDaoImpl;
import model.activity.ActivityCheckModel;
import model.activity.ActivityModel;
import model.activity.ActivityProdsModel;
import model.finance.ProductModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ZH on 2015/5/13.
 */
@Repository
public interface ActivityProdsDao {
    @Insert("insert into discount_prods (discountId,prodId,quantity,isGift,giftId,priceId) " +
            "values (#{discountId},#{prodId},#{quantity},#{isGift},#{giftId},#{priceId})")
    public int addActivityProdsModel(ActivityProdsModel activityProdsModel);

    @Update("update  discount_prods set quantity=#{quantity},isGift=#{isGift} where discountId=#{discountId} and prodId=#{prodId} and isGift=#{isGift}")
    public int updateByDiscountId(ActivityProdsModel activityProdsModel);

    @Delete("delete from discount_prods where discountId=#{discountId} and prodId=#{prodId} and priceId=#{priceId};")
    public int deleteProdDiscount(ActivityProdsModel activityProdsModel);


    @Select("select discountId,discountName,discountType,discountInfo,isAdd,giftNum,beginDate,endDate,insertDate,discountStatus " +
            "from discount_info " +
            "where discountStatus=1 and discountId=#{discountId}")
    public ActivityModel selectByDiscountId(ActivityModel activityModel);

    @SelectProvider(type = ActivityDaoImpl.class, method = "getProdIsGift")
    public List<ActivityProdsModel> selectByDiscountIdProdinfo(ActivityModel activityModel);

    @Select("select b.* from discount_prods a,product_info b where a.discountId = #{discountId} and b.prodId = a.prodId and a.isGift = 0")
    public List<ProductModel> getActivityProds(ActivityCheckModel checkModel);

    @Select("SELECT a.priceId as priceId,a.quantity as quantity,c.prodType as prodType,c.price as price,b.prodName as prodName,b.prodId as prodId from discount_prods a,product_info b,price_info c where a.discountId = #{discountId} and b.prodId = a.prodId AND c.priceId = a.priceId and a.isGift = 1 order by c.prodType desc")
    public List<ProductModel> getGiftProduct(ActivityCheckModel checkModel);
}

