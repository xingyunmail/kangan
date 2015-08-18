package DAO;

import DAO.price.PriceDaoImpl;
import model.price.PriceModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ZH on 2015/5/18.
 */
@Repository
public interface PriceDao {

    @SelectProvider(type = PriceDaoImpl.class, method = "getList")
    public List<PriceModel> getList(PriceModel priceModel);

    @SelectProvider(type = PriceDaoImpl.class, method = "getListCount")
    public int getListCount(PriceModel priceModel);

  //  @Insert( statement = "select LAST_INSERT_ID() as discountId", keyProperty = "discountId", resultType = Integer.class, before = false)
    @SelectProvider(type = PriceDaoImpl.class, method = "batchInsert")
    public void batchInsertUpdatePrice(PriceModel priceModel);

    @Update("update price_info set endDate=#{endDate},updateTime=now() where customer=#{customer}" +
            " and beginDate=#{beginDate}")
    public int updateCoustomerPrice(PriceModel priceModel);

    @SelectProvider(type = PriceDaoImpl.class, method = "selectMesg")
    public List<PriceModel> selectMesg(PriceModel priceModel);

    @Insert("insert into price_info (price,beginDate,endDate,priceType,prodId,valid,customer,prodType,updateTime,isdelete) " +
            "values (#{price},#{beginDate},#{endDate},#{priceType},#{prodId},1,0,#{prodType},now(),0)")
    public int insertPrice(PriceModel priceModel);

    @Update("update price_info set isdelete = 1 where prodId=#{prodId} and priceType=#{priceType}")
    public int deletePrice(PriceModel priceModel);

    @Delete("update price_info set price=#{price},beginDate=#{beginDate},endDate=#{endDate},valid=#{valid} where prodId=#{prodId} and priceType=#{priceType}")
    public int updatePrice(PriceModel priceModel);
}
