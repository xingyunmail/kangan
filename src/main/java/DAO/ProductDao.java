package DAO;


import DAO.product.ProductDaoImpl;
import model.base.product.Product;
import model.product.ProdModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunhao on 15/5/4.
 */
@Repository
public interface ProductDao {

    //查询产品，只能查询正常产品价格。
    @Select("SELECT DISTINCT product_info.prodid prodId, product_info.prodname prodName, price_info.prodtype prodType, price_info.price price, price_info.priceid priceId, price_info.pricetype FROM product_info join  price_info on product_info.prodid = price_info.prodid   WHERE price_info.beginDate<=CURRENT_DATE() and ifnull(price_info.endDate,CURRENT_DATE())>=CURRENT_DATE() and price_info.valid = 1 and price_info.prodtype = 1 and product_info.prodid = #{prodId} and price_info.customer in (#{custId},'0') and price_info.priceType in ((select pricetype from order_type where order_type.id = #{orderType} and valid = 1),99) order by price_info.pricetype DESC LIMIT 1")
    public ProdModel getProdInfo(ProdModel prodModel);

    @SelectProvider(type = ProductDaoImpl.class,method = "selectMessage")
    public List<ProdModel> getProductMessage(ProdModel prodModel);


    @SelectProvider(type = ProductDaoImpl.class, method = "getProductByCity")
    public List<ProdModel> getProductByCity(ProdModel prodModel);
    @SelectProvider(type = ProductDaoImpl.class, method = "getProductByCityCount")
    public int getProductByCityCount(ProdModel prodModel);

//    @SelectProvider(type = ProductDaoImpl.class, method = "getProductMessageCount")
//    public int getProductMessageCount(ProdModel prodModel);


    @SelectProvider(type = ProductDaoImpl.class,method = "getProdPriceInfo")
    public List<ProdModel> getProdPriceInfo(ProdModel prodModel);

    @Select("select DISTINCT product_info.prodId,prodName from product_info join price_info on product_info.prodid = price_info.prodid where price_info.prodtype = 1")
    public List<ProdModel> getProdNumName(ProdModel prodModel);

    @Select("select * from product_info where prodId=#{prodId}")
    public ProdModel getProdInfoById(ProdModel prodModel);

    @SelectProvider(type = ProductDaoImpl.class, method = "getProductById")
    public ProdModel getProductById(ProdModel prodModel);

    @Insert("insert into product_info (prodId,prodName,prodweight,prodPakage,prodStatus,insertDate) " +
            "values (#{prodId},#{prodName},#{prodweight},#{prodPakage},#{prodStatus},now())")
    @SelectKey(statement = "select LAST_INSERT_ID() as id", keyProperty = "id", resultType = Integer.class, before = false)
    public int addProudct(Product product);

    @Select("select prodId from product_info where prodId like '1%' ORDER BY prodId DESC LIMIT 1")
    public int getLastProudctId();

   @Delete("delete from product_info where prodId = #{prodId}")
    public int deleteProudct(Product product);

    @Update("update product_info set prodName=#{prodName},prodweight=#{prodweight},prodPakage=#{prodPakage},prodStatus=#{prodStatus},insertDate=now() where prodId = #{prodId} ")
    public int updateProudct(Product product);
}
