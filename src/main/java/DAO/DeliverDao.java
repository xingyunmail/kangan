package DAO;


import DAO.deliver.DeliverDaoImpl;
import DAO.order.OrderDaoImpl;
import controller.Deliver;
import model.deliver.DeliverOrderModel;
import model.deliver.ReceiveMilkOrderModel;
import model.order.OrderModel;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lining on 15-5-13.
 */
@Repository
public interface DeliverDao {

    /*
     * 收奶单sql
     */
//    @Select("CALL findOrderDetail(#{orderDate},#{orderArea},'','',0,#{startNum},#{limit},'','')")
//    public List<ReceiveMilkOrderModel> getMilkOrderList(ReceiveMilkOrderModel receiveMilkOrderModel);

    /*
     * 收奶单sql 不分页
     */
    @Select("CALL findOrderDetail(#{orderDate},#{orderArea},'','',0,0,0,'','')")
    public List<ReceiveMilkOrderModel> getMilkOrderList(ReceiveMilkOrderModel receiveMilkOrderModel);

    /*
     * 收奶单sql-count
     */
    @Select("CALL findOrderDetail(#{orderDate},#{orderArea},'','',0,0,0,'','')")
    public List<ReceiveMilkOrderModel> getMilkOrderListCount(ReceiveMilkOrderModel receiveMilkOrderModel);

    /*
     * 配送单sql
     */
    @Select("CALL findOrderDetail(#{startDate},#{orderArea},#{orderId},#{prodId},#{orderType},#{startNum},#{limit},#{userId},#{userName})")
    public List<DeliverOrderModel> getDeliverOrderInfo(DeliverOrderModel deliverOrderModel);

     //根据时间段查询配送单，未用
//    @SelectProvider(type = DeliverDaoImpl.class,method = "getDeliverOrderCount")
//    public int getDeliverOrderCount(DeliverOrderModel deliverOrderModel);

    /*
     * 配送单sql--count
     */
    @Select("CALL findOrderDetail(#{startDate},#{orderArea},#{orderId},#{prodId},#{orderType},0,0,#{userId},#{userName})")
    public List<DeliverOrderModel> getDeliverOrderCountByProc(DeliverOrderModel deliverOrderModel);

    /*
     * 配送单中的查看变动sql
     */
    @Select("CALL findOrderDetail(#{startDate},#{orderArea},#{orderId},#{prodId},#{orderType},0,0,#{userId},#{userName})")
    public List<DeliverOrderModel> getChangeOrderInfo(DeliverOrderModel deliverOrderModel);

    /*
     * 配送单中的查看变动中的余量查询，
     * @param  #{id} order_detail_id
     * @param  #{startDate} 查询时间
     */
    @Select("select getLeftNumber(#{id},#{startDate}) from dual")
    public int getRestNumber(DeliverOrderModel deliverOrderModel);

//    @Select("SELECT b.prodPakage prodPackage, a.deliverid userId, a.prodId, sum(number) number FROM add_taste_apply a, product_info b WHERE a.applytype = 1 AND substr(a.deliverid, 1, 4) = #{orderArea} AND a.beginDate <= #{orderDate} AND a.endDate >= #{orderDate} AND a.prodId = b.prodId GROUP BY deliverId, prodid")
    @SelectProvider(type = DeliverDaoImpl.class,method = "getAddMilk")
    public List<ReceiveMilkOrderModel> getAddMilk(ReceiveMilkOrderModel receiveMilkOrderModel);

//    @Select("SELECT b.prodPakage prodPackage, a.deliverid userId, a.prodId,b.prodName, sum(number) number FROM add_taste_apply a, product_info b WHERE a.applytype = 2 AND substr(a.deliverid, 1, 4) = #{orderArea} AND a.beginDate <= #{orderDate} AND a.endDate >= #{orderDate} AND a.prodId = b.prodId GROUP BY deliverId, prodid")
    @SelectProvider(type = DeliverDaoImpl.class,method = "getTasteMilk")
    public List<ReceiveMilkOrderModel> getTasteMilk(ReceiveMilkOrderModel receiveMilkOrderModel);





}
