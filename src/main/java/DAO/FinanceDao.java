package DAO;

import DAO.finance.FinanceDaoImpl;
import model.finance.ExchangeCostModel;
import model.finance.FinanceModel;
import model.product.ProdModel;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by scar on 15/5/20.
 */
@Repository
public interface FinanceDao {

    @SelectProvider(type = FinanceDaoImpl.class, method = "getNewList")
//    @Select("SELECT b.userName, c.areaName, count(a.id) count, a.orderPrice, a.deliverId FROM order_info a, user_info b, area_info c, order_type d WHERE a.orderDate <= '2015-05-30' AND a.orderDate >= '2015-05-15' AND a.deliverId = b.userId AND b.areaId = c.areaId AND a.orderType = d.id AND d.receiveType = 2 GROUP BY a.deliverId")
    List<FinanceModel> getNewList(FinanceModel financeModel);

    @SelectProvider(type = FinanceDaoImpl.class, method = "getNewListCounts")
//    @Select("SELECT b.userName, c.areaName, count(a.id) count, a.orderPrice, a.deliverId FROM order_info a, user_info b, area_info c, order_type d WHERE a.orderDate <= '2015-05-30' AND a.orderDate >= '2015-05-15' AND a.deliverId = b.userId AND b.areaId = c.areaId AND a.orderType = d.id AND d.receiveType = 2 GROUP BY a.deliverId")
    int getNewListCounts(FinanceModel financeModel);

    //    @Select("select *,GROUP_CONCAT(e.product) products,GROUP_CONCAT(e.price) prices,sum(e.price) totalPrice  from (SELECT a.orderid, a.custId, a.custAddr, a.custPhone, CONCAT_WS( '*', CONVERT(c.prodName,char), CONVERT(b.quantity,char), CONVERT(b.deliverDays,char) ) product, d.price FROM order_info a, order_detail b, product_info c, price_info d WHERE a.deliverId = #{userId} AND a.orderDate <= #{endDate} AND a.orderDate >= #{startDate} AND a.orderid = b.orderId AND b.prodId = c.prodId AND b.priceId = d.priceId)  e group by e.orderid")
    @SelectProvider(type = FinanceDaoImpl.class, method = "getNewInfo")
    List<FinanceModel> getNewInfo(FinanceModel financeModel);

    /*
     * 换奶费用单列表，根据送奶员分组查出
     */
    @SelectProvider(type=FinanceDaoImpl.class,method="getChangeList")
    List<ExchangeCostModel> getChangeList(ExchangeCostModel exchangeCostModel);

    /*
     * 换奶费用单详细列表，根据客户分组显示某个客户的总差额
     */
    @SelectProvider(type=FinanceDaoImpl.class,method="getOrderChangeInfoByCust")
    List<ExchangeCostModel> getOrderChangeInfoByCust(ExchangeCostModel exchangeCostModel);


    /*
     * 换奶费用单详细列表，查看某个客户下的所有换奶情况
     * @param exchangeCostModel 客户ID
     */
    @SelectProvider(type=FinanceDaoImpl.class,method="getChangeInfo")
    List<ExchangeCostModel> getChangeInfo(ExchangeCostModel exchangeCostModel);

    /*
     * 换奶费用单详细列表，根据exchange_info_id 和exchange_prod_exchangeId 关联查询换奶品种及个数（原先的品种）
     */
    @Select("SELECT a.prodId, cast( concat(b.prodName, '*', c.quantity) AS CHAR charset utf8 ) prodName, a.prodNum quantity FROM exchange_prod a, product_info b, order_detail c WHERE a.exchangeId = #{exchangeId} AND oldOrNew = 1 AND a.prodId = b.prodId AND a.detailId = c.id")
    List<ProdModel> getChangeOldProdInfo(ExchangeCostModel exchangeCostModel);

    /*
     * 换奶费用单详细列表，根据exchange_info_id 和exchange_prod_exchangeId 关联查询换奶品种及个数（换后的品种）
     */
    @Select("SELECT a.prodId, cast( concat(b.prodName, '*', c.quantity) AS CHAR charset utf8 ) prodName, a.prodNum quantity FROM exchange_prod a, product_info b, order_detail c WHERE a.exchangeId = #{exchangeId} AND oldOrNew = 2 AND a.prodId = b.prodId AND a.detailId = c.id")
    List<ProdModel> getChangeNewProdInfo(ExchangeCostModel exchangeCostModel);



}
