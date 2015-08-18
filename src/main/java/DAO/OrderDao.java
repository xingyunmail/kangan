package DAO;

import DAO.order.OrderDaoImpl;
import model.order.*;
import model.product.ProdModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunhao on 15/4/30.
 */
@Repository
public interface OrderDao {

    @SelectProvider(type = OrderDaoImpl.class, method = "getOrderInfo")
    public List<OrderModel> getOrderInfo(OrderModel orderModel);

    /**
     * 一期优化查询
     * created by sunhao 2015-07-20
     * @param orderModel
     * @return
     */
    @SelectProvider(type = OrderDaoImpl.class,method = "getOrders")
    public List<QueryOrderModel> getOrders(OrderModel orderModel);

    @SelectProvider(type = OrderDaoImpl.class,method = "getOrdersCount")
    public int getOrdersCount(OrderModel orderModel);


    @SelectProvider(type = OrderDaoImpl.class, method = "getOrderProds")
    public List<OrderDetialModel> getOrderProds(OrderDetialModel orderProdModel);

    //    @InsertProvider(type = OrderDaoImpl.class, method = "addDetials")
//    @Select("call addOrder(#{orderId},#{prodId},#{priceId},#{quantity},#{deliverRules},#{deliverDays},#{beginDate},#{areaId},#{deliverId})")
//    @InsertProvider(type = OrderDaoImpl.class, method = "addOrderDetial")
    @Insert("INSERT INTO order_detail ( orderId, prodId, priceId,prodType, quantity, deliverRules, deliverDays, beginDate, endDate, leftDays, STATUS,isGiven ) VALUES ( #{orderId},#{prodId},#{priceId},#{prodType},#{quantity},#{deliverRules},#{deliverDays},#{beginDate},getEndDate(#{deliverRules},#{deliverDays},#{beginDate}),#{deliverDays},#{status},#{isGiven})")
    public int addOrderDetial(OrderDetialModel orderDetialModel);

    @Insert("INSERT INTO order_detail_history ( orderId, prodId, priceId,prodType, quantity, deliverRules, deliverDays, beginDate, endDate, leftDays, STATUS,isGiven ) VALUES ( #{orderId},#{prodId},#{priceId},#{prodType},#{quantity},#{deliverRules},#{deliverDays},#{beginDate},getEndDate(#{deliverRules},#{deliverDays},#{beginDate}),#{deliverDays},1,#{isGiven})")
    public int addOrderDetialHitory(OrderDetialModel orderDetialModel);

    @Insert("insert into order_info (orderId,orderType,custId,orderPrice,custName,custPhone,custAddr,orderDate,orderStatus,originalOrder,deliverId,addUserId,areaId,discountId) values (#{orderId},#{orderType},#{custId},#{orderPrice},#{custName},#{custPhone},#{custAddr},#{orderDate},1,0,#{deliverId},#{addUserId},#{areaId},#{discountId})")
    int addOrder(OrderModel orderModel);

    @UpdateProvider(type = OrderDaoImpl.class, method = "updateOrderProd")
    public int updateOrderProd(OrderDetialModel orderProdModel);

    @Update("update order_info c set c.orderPrice =(select sum(a.deliverDays*a.quantity*b.price) count from order_detail a, price_info b where a.priceId=b.priceId and a.orderId =c.orderid)where c.orderId=#{orderId}")
    void updateOrderPrice(OrderModel orderModel);

    @Select("select ifnull(getleftNumber(#{detailId},CURRENT_DATE()),0) from dual;")
    public int getLeftNumbers(OrderDetialModel orderProdModel);


    @Select("call returnMilk(#{orderId},#{detailId},#{prodId},#{prodNum},#{returnPrice},#{reason},#{operator})")
    public int returnMilk(ReturnMilkModel returnMilkModel);

    @Select("call stopMilk(#{detailId},#{stopDate},#{beginDate})")
    public int stopMilk(OrderDetialModel orderDetialModel);


    @Select("call activeMilk(#{detailId},#{beginDate},#{deliverRules})")
    public int activeMilk(OrderDetialModel orderDetialModel);

    /*START function below are use for exchangeMilk*/
    @Insert("insert into exchange_info(orderid,custId,deliverId) select order_info.orderid,order_info.custId,order_info.deliverId from order_info where order_info.orderid= #{orderId}")
    @SelectKey(statement = "select LAST_INSERT_ID() as exchangeId", keyProperty = "exchangeId", resultType = Long.class, before = false)
    public int addExchangeInfo(OrderDetialModel orderDetialModel);

    @Select("call updateExchangeProd(#{detailid},#{exchangeid})")
    public int addExchangeOld(@Param("detailid") String detailid, @Param("exchangeid") long exchangeid);

    @Select("call exchangeMilk(#{exchangeId},#{orderId},#{detailIds},#{prodId},#{quantity},#{leftNum},#{deliverRules},#{beginDate},#{status})")
    public int addExchangeNew(OrderDetialModel orderDetialModel);

    @Update("update exchange_info set exchange_info.diffPrice = ((select sum(exchange_prod.prodNum * price_info.price) from exchange_prod join price_info on exchange_prod.priceId = price_info.priceId where exchange_prod.oldOrNew = 2 and exchange_prod.exchangeId = #{exchangeId}) - (select sum(exchange_prod.prodNum * price_info.price) from exchange_prod join price_info on exchange_prod.priceId = price_info.priceId where exchange_prod.oldOrNew = 1 and exchange_prod.exchangeId = #{exchangeId}) )  where exchange_info.exchangeId = #{exchangeId} ")
    public int updateExchangePrice(@Param("exchangeId") long exchangeId);

    @Update("update order_info set orderPrice = (orderPrice + ifnull((select diffPrice from exchange_info where exchangeId = #{exchangeId}),0)) where orderid = #{orderId}")
    public int updateOrderInfoPrice(OrderDetialModel orderDetialModel);

    @Select("SELECT discount_info.giftNum, product_info.prodId, product_info.prodName,discount_prods.priceId,price_info.prodType FROM discount_info JOIN discount_prods ON discount_info.discountId = discount_prods.discountId JOIN product_info ON discount_prods.prodid = product_info.prodId join price_info on discount_prods.priceId = price_info.priceId  WHERE discount_info.discountId = (SELECT order_info.discountId from order_info where order_info.orderid = #{orderId}) AND discount_prods.isGift = 1;")
    public List<ProdModel> getDisProds(OrderModel orderModel);

    @Select("call exchangeGift(#{exchangeId},#{orderId},#{detailIds},#{prodId},#{quantity},#{leftNum},#{deliverRules},#{beginDate},#{status},#{prodType})")
    public int addExchangeGift(OrderDetialModel orderDetialModel);

    @Select("call getProdPrice(#{orderId},#{prodId},#{quantity},#{leftNum})")
    public float getProdPrice(OrderDetialModel orderDetialModel);
    /*END*/


    @SelectProvider(type = OrderDaoImpl.class, method = "getLeft")
    void getLeft(OrderExpireModel orderExpireModel);


    @Insert("insert into production_info (detiallId,prodId,prodDate,areaId,deliverId,numbers) values(#{detiallId},#{prodId},#{prodDate},#{areaId},#{deliverId},#{numbers})")
    void test(String detiallId, String prodId, String prodDate, String areaId, String deliverId, String numbers);





    /*functions below are using for add milk or taste milk --by sunhao*/

    @SelectProvider(type = OrderDaoImpl.class, method = "getApplyInfo")
    public List<ApplyModel> getApplyInfo(ApplyModel applyModel);

    @SelectProvider(type = OrderDaoImpl.class, method = "getDeliveredNum")
    public int getDeliveredNum(ApplyModel applyModel);

    @InsertProvider(type = OrderDaoImpl.class, method = "addMilkApply")
    public int addMilkApply(AddMilkModel addMilkModel);

    @SelectProvider(type = OrderDaoImpl.class, method = "getApplyNumber")
    public int getApplyNumber(ApplyModel applyModel);


}
