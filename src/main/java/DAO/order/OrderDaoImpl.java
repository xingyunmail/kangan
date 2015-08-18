package DAO.order;

import model.order.*;

import java.util.Calendar;
import java.util.List;

/**
 * Created by sunhao on 15/5/4.
 */
public class OrderDaoImpl {

    private static final int ORDER_STATUS_ALL = 0;
    private static final int ORDER_STATUS_STOP = 1;
    private static final int ORDER_STATUS_ACTIVE = 2;
    private static final int ORDER_STATUS_EXCHANGE = 3;


    /**
     * 查询订单SQL实现
     * 其中order_products为视图
     *
     * @param orderModel
     * @return orderModel
     */
    public String getOrderInfo(OrderModel orderModel) {
       /* StringBuffer sql = new StringBuffer("SELECT DISTINCTROW " +
                " order_info.orderid orderId," +
                " order_info.orderType orderType," +
                " dic_item.itemDiscrible orderTypeName," +
                " order_info.custId custId," +
                " order_info.custname custName," +
                " order_info.custphone custPhone," +
                " order_info.custaddr custAddr," +
                " order_info.orderprice orderPrice," +
                " order_products.prodnames products," +
                " order_info.orderDate orderDate," +
                " order_info.originalOrder originalOrder," +
                " user_info.username userName," +
                " order_info.deliverId deliverId" +
                " FROM " +
                " order_info " +
                " JOIN order_detail ON order_info.orderid = order_detail.orderid " +
                " JOIN order_products ON order_detail.orderid = order_products.orderid " +
                " LEFT JOIN milkbox_info ON order_info.custid = milkbox_info.customeid " +
                " join user_info on user_info.userid = order_info.deliverid " +
                " join dic_item on order_info.orderType = dic_item.itemValue where dic_item.itemKey='ORDER_TYPE'  ");*/

        StringBuffer sql = new StringBuffer("SELECT DISTINCTROW " +
                " order_info.orderid orderId, " +
                " order_info.orderType orderType, " +
                " order_type.name orderTypeName, " +
                " order_info.custId custId, " +
                " order_info.custname custName, " +
                " order_info.custphone custPhone, " +
                " order_info.custaddr custAddr, " +
                " order_info.orderprice orderPrice, " +
                " order_info.orderDate orderDate, " +
                " order_info.originalOrder originalOrder, " +
                " order_info.deliverId deliverId," +
                " order_info.addUserId addUserId, " +
                " user_info.username userName, " +
                " t.products, " +
                " t.prodids " +
                " FROM " +
                " (select a.orderId, CAST( GROUP_CONCAT(a.prodname SEPARATOR ',') AS CHAR charset utf8 ) products,CAST( GROUP_CONCAT(a.prodid SEPARATOR ',') AS CHAR charset utf8 ) prodids  " +
                "   from ( SELECT order_detail.orderId, CONCAT( product_info.prodName, '*', sum( getLeftNumber (order_detail.id, CURRENT_DATE()) ) ) prodname, order_detail.prodId " +
                "     FROM order_detail " +
                "     JOIN product_info ON order_detail.prodId = product_info.prodId " +
                "     LEFT JOIN order_info ON order_detail.orderId = order_info.orderId " +
                "     WHERE 1=1  ") ;
//                "     AND order_detail.endDate >= order_detail.beginDate " +
//                "     AND order_detail.endDate >= CURRENT_DATE () " +

        StringBuffer sqlMiddle = new StringBuffer();

        StringBuffer sqlEnd = new StringBuffer("     GROUP BY order_detail.prodId, order_detail.orderId) a group by a.orderid " +
                " ) t   " +
                " join order_info ON t.orderid = order_info.orderid " +
                " LEFT JOIN milkbox_info ON order_info.custid = milkbox_info.customeid  " +
                " join user_info on user_info.userid = order_info.deliverid " +
                " join order_type on order_info.orderType = order_type.id  ");


        StringBuffer sqlWhere = new StringBuffer();


        if (null != orderModel.getCustId() && !"".equals(orderModel.getCustId())) {
            //sqlWhere.append( " and order_info.custid like CONCAT('%',#{custId},'%' ) ");
            sqlMiddle.append("  and order_info.custid like CONCAT('%',#{custId},'%' ) ");
        }
        if (null != orderModel.getCustAddr() && !"".equals(orderModel.getCustAddr())) {
            //sqlWhere.append( " and order_info.custaddr like CONCAT('%',#{custAddr},'%' ) ");
            sqlMiddle.append( " and order_info.custaddr like CONCAT('%',#{custAddr},'%' ) ");

        }
        if (null != orderModel.getMilkBoxId() && !"".equals(orderModel.getMilkBoxId())) {
            sqlWhere.append( " and milkbox_info.boxid like CONCAT('%',#{milkBoxId},'%' )  ");
        }
        if (null != orderModel.getCustPhone() && !"".equals( orderModel.getCustPhone())) {
            //sqlWhere.append( " AND order_info.custphone like CONCAT('%',#{custPhone},'%' ) ");
            sqlMiddle.append( " AND order_info.custphone like CONCAT('%',#{custPhone},'%' ) ");

        }
        if (null != orderModel.getProducts() && !"".equals(orderModel.getProducts())) {
            sqlWhere.append( " AND t.prodids like CONCAT('%',#{products},'%' )   ");
        }
        if (null != orderModel.getOrderId() && !"".equals(orderModel.getOrderId())) {
            //sqlWhere.append( "  and order_info.ORDERID LIKE CONCAT('%',#{orderId},'%' ) ");
            sqlMiddle.append("  and order_detail.ORDERID LIKE CONCAT('%',#{orderId},'%' ) " );
        }
        if (null != orderModel.getOrderDate() && !"".equals(orderModel.getOrderDate())) {
           // sqlWhere.append( " and DATE_FORMAT(order_info.orderdate,'%Y-%m-%d') >= #{orderDate} ");
            sqlMiddle.append( " and DATE_FORMAT(order_info.orderdate,'%Y-%m-%d') >= #{orderDate} ");

        }
        if(null != orderModel.getOrderDateEnd() && !"".equals( orderModel.getOrderDateEnd()))
        {
            //sqlWhere.append( " and DATE_FORMAT(order_info.orderdate,'%Y-%m-%d') <= #{orderDateEnd}  ");
            sqlMiddle.append( " and DATE_FORMAT(order_info.orderdate,'%Y-%m-%d') <= #{orderDateEnd}  ");

        }

        if(orderModel.getLimit() >0)
        {
            sqlWhere.append(" limit " + (orderModel.getStartNum() - 1) * orderModel.getLimit() + "," + orderModel.getLimit());
        }

        sql.append(sqlMiddle).append(sqlEnd).append(sqlWhere);

        return sql.toString();
    }

    /**
     *
     * @param orderModel
     * @return
     */
    public String getOrders(OrderModel orderModel)
    {
        StringBuffer sql = new StringBuffer("select " +
                " order_info.orderId orderId," +
                " order_info.orderType orderType," +
                " order_info.custId custId, " +
                " order_info.custname custName, " +
                " order_info.custphone custPhone, " +
                " order_info.custaddr custAddr, " +
                " order_info.orderprice orderPrice, " +
                " order_info.orderDate orderDate, " +
                " order_info.originalOrder originalOrder, " +
                " order_info.deliverId deliverId," +
                " user_info.username deliverName, " +
                " CAST( GROUP_CONCAT(CONCAT(order_detail.id,'##',order_detail.prodId," +
                "'##',product_info.prodName,'##',order_detail.prodType,'##',dic_item.itemDiscrible," +
                "'##',order_detail.quantity,'##',IFNULL(order_detail.deliverDays,0)," +
                "'##',IFNULL(order_detail.deliverRules,0),'##',IFNULL(order_detail.beginDate,'-')," +
                "'##',IFNULL(order_detail.endDate,'-'),'##',IFNULL(order_detail.stopDate,'-')," +
                "'##',order_detail.STATUS) SEPARATOR '@@') AS CHAR charset utf8 ) detail " +
                " from order_info join order_detail on order_info.orderid = order_detail.orderid " +
                " join product_info on product_info.prodid = order_detail.prodid " +
                " join user_info on order_info.deliverId = user_info.userid " +
                " join dic_item on order_detail.prodType = dic_item.itemValue " +
                " where dic_item.itemKey='PROD_TYPE' and order_detail.deliverDays>0 ");

        StringBuffer sqlWhere = new StringBuffer();

        if (null != orderModel.getCustId() && !"".equals(orderModel.getCustId())) {
            sqlWhere.append("  and order_info.custid like CONCAT('%',#{custId},'%' ) ");
        }
        if (null != orderModel.getCustName() && !"".equals(orderModel.getCustName()))
        {
            sqlWhere.append( " and order_info.custName like CONCAT('%',#{custName},'%' ) ");
        }
        if (null != orderModel.getCustAddr() && !"".equals(orderModel.getCustAddr())) {
            sqlWhere.append( " and order_info.custaddr like CONCAT('%',#{custAddr},'%' ) ");
        }
        if (null != orderModel.getCustPhone() && !"".equals(orderModel.getCustPhone())) {
            sqlWhere.append( " AND order_info.custphone like CONCAT('%',#{custPhone},'%' ) ");
        }
        if(null != orderModel.getDeliverId() && !"".equals(orderModel.getDeliverId()))
        {
            sqlWhere.append( " AND order_info.deliverid  = #{deliverId} ");
        }
        if (null != orderModel.getOrderId() && !"".equals(orderModel.getOrderId())) {
            sqlWhere.append("  and order_info.orderid LIKE CONCAT('%',#{orderId},'%' ) " );
        }
        if( null != orderModel.getProducts() && !"".equals(orderModel.getProducts()))
        {
            sqlWhere.append("  and order_detail.prodid LIKE CONCAT('%',#{products},'%' ) " );
        }

        if(null != orderModel.getOrderStatus() && "beginnull".equals(orderModel.getOrderStatus().toLowerCase()))
        {
            sqlWhere.append( " and (order_detail.beginDate is null or order_detail.beginDate = '' or  order_detail.beginDate = '0000-00-00') ");
        }

        if (null != orderModel.getOrderDate() && !"".equals(orderModel.getOrderDate())) {
            sqlWhere.append( " and DATE_FORMAT(order_info.orderdate,'%Y-%m-%d') >= #{orderDate} ");
        }
        if(null != orderModel.getOrderDateEnd() && !"".equals( orderModel.getOrderDateEnd()))
        {
            sqlWhere.append( " and DATE_FORMAT(order_info.orderdate,'%Y-%m-%d') <= #{orderDateEnd}  ");
        }

        sqlWhere.append(" group by order_info.orderid ");

        if(orderModel.getLimit() >0)
        {
            sqlWhere.append(" limit " + (orderModel.getStartNum() - 1) * orderModel.getLimit() + "," + orderModel.getLimit());
        }

        sql.append(sqlWhere);

        return sql.toString();
    }


    /**
     * 获取查询的数据条数
     * @param orderModel
     * @return
     */
    public String getOrdersCount(OrderModel orderModel)
    {
        StringBuffer sql = new StringBuffer("select count(*) count from ( select count(*) " +
                " from order_info join order_detail on order_info.orderid = order_detail.orderid " +
                " join product_info on product_info.prodid = order_detail.prodid " +
                " join user_info on order_info.deliverId = user_info.userid " +
                " join dic_item on order_detail.prodType = dic_item.itemValue " +
                " where dic_item.itemKey='PROD_TYPE' and order_detail.deliverDays>0 ");

        StringBuffer sqlWhere = new StringBuffer();

        if (null != orderModel.getCustId() && !"".equals(orderModel.getCustId())) {
            sqlWhere.append("  and order_info.custid like CONCAT('%',#{custId},'%' ) ");
        }
        if (null != orderModel.getCustAddr() && !"".equals(orderModel.getCustAddr())) {
            sqlWhere.append( " and order_info.custaddr like CONCAT('%',#{custAddr},'%' ) ");
        }
        if (null != orderModel.getCustPhone() && !"".equals(orderModel.getCustPhone())) {
            sqlWhere.append( " AND order_info.custphone like CONCAT('%',#{custPhone},'%' ) ");
        }
        if(null != orderModel.getDeliverId() && !"".equals(orderModel.getDeliverId()))
        {
            sqlWhere.append( " AND order_info.deliverid  = #{deliverId} ");
        }
        if (null != orderModel.getOrderId() && !"".equals(orderModel.getOrderId())) {
            sqlWhere.append("  and order_detail.ORDERID LIKE CONCAT('%',#{orderId},'%' ) " );
        }
        if( null != orderModel.getProducts() && !"".equals(orderModel.getProducts()))
        {
            sqlWhere.append("  and order_detail.prodid LIKE CONCAT('%',#{orderId},'%' ) " );
        }
        //orderDate传NULL字符串标示查询包含停送产品的订单
        if(null != orderModel.getOrderDate() && "null".equals(orderModel.getOrderDate().toLowerCase()))
        {
            sqlWhere.append( " and (order_detail.beginDate is null or order_detail.beginDate = '' or  order_detail.beginDate = '0000-00-00') ");
        }
        else if (null != orderModel.getOrderDate() && !"".equals(orderModel.getOrderDate())) {
            sqlWhere.append( " and DATE_FORMAT(order_info.orderdate,'%Y-%m-%d') >= #{orderDate} ");
        }
        if(null != orderModel.getOrderDateEnd() && !"".equals( orderModel.getOrderDateEnd()))
        {
            sqlWhere.append( " and DATE_FORMAT(order_info.orderdate,'%Y-%m-%d') <= #{orderDateEnd}  ");
        }

        sqlWhere.append(" group by order_info.orderid ) orders");

        sql.append(sqlWhere);

        return sql.toString();
    }

    /**
     * 查询定产产品
     *
     * @param orderProdModel
     * @return
     */
    public String getOrderProds(OrderDetialModel orderProdModel) {
        StringBuffer sql = new StringBuffer("select order_detail.id detailId, " +
                " order_detail.orderid orderId," +
                " order_detail.prodid prodId," +
                " order_detail.prodType prodType," +
                " dic_item.itemdiscrible prodTypeName," +
                " product_info.prodname prodName," +
                " order_detail.quantity quantity," +
                " order_detail.priceId priceId," +
                " price_info.price prodPrice," +
                " order_detail.deliverdays deliverDays," +
                " order_detail.begindate beginDate," +
                " order_detail.deliverRules deliverRules," +
                " order_detail.enddate endDate," +
                " order_detail.isGiven isGiven " +
                " from " +
                " order_detail,product_info," +
                " dic_item," +
                " price_info " +
                " where dic_item.itemkey = 'PROD_TYPE' " +
                " and order_detail.prodtype = dic_item.itemvalue " +
                " and order_detail.prodid = product_info.prodid " +
                " and order_detail.priceid = price_info.priceid ");

        StringBuffer sqlWhere = new StringBuffer();

        if (null != orderProdModel.getOrderId() && !"".equals( orderProdModel.getOrderId()))
        {
            sqlWhere.append(" and order_detail.orderid = #{orderId} ");
        }

        switch (orderProdModel.getStatus())
        {
            case ORDER_STATUS_ACTIVE:
                sqlWhere.append("and  order_detail.deliverDays > 0 and order_detail.status not in (0,3,5)  and (order_detail.beginDate is null or (order_detail.beginDate is not null and order_detail.endDate is not null and order_detail.enddate >= order_detail.beginDate and order_detail.beginDate>current_date() )) ");
                break;
            case ORDER_STATUS_EXCHANGE:
                sqlWhere.append(" and order_detail.deliverDays>0  and  order_detail.status not in (0,3,5) and (order_detail.beginDate is null or (order_detail.beginDate is not null and order_detail.endDate is not null and order_detail.enddate >= order_detail.beginDate and order_detail.enddate>current_date()))");
                break;
            case ORDER_STATUS_STOP:
                sqlWhere.append(" and order_detail.deliverDays>0  and  order_detail.status not in (0,3,5) and (order_detail.beginDate is not null and order_detail.endDate is not null and order_detail.enddate >= order_detail.beginDate and order_detail.enddate > current_date())");
                break;
            case ORDER_STATUS_ALL:
                break;
            default:
                sqlWhere.append("  and order_detail.status<>0 ");

        }


        if (null != orderProdModel.getProdType())
        {
            sqlWhere.append( " and order_detail.prodType in ( " + orderProdModel.getProdType() + ")");
        }

        sql.append(sqlWhere);

        return sql.toString();

    }


    /**
     * 更新订购的产品信息
     *
     * @param orderProdModel
     * @return
     */
    public String updateOrderProd(OrderDetialModel orderProdModel) {
        String sql = "update order_detail set order_detail.id = order_detail.id  ";

        String sqlSet = " ";

        if (null == orderProdModel.getBeginDate()) {
            sqlSet += ", order_detail.beginDate = null ";
        } else {
            sqlSet += ", order_detail.beginDate = #{beginDate} ";
        }

        if (null == orderProdModel.getStopDate()) {
            sqlSet += ", order_detail.stopDate = null ";
        } else {
            sqlSet += ", order_detail.stopDate = #{stopDate} ";
        }

        if (0 != orderProdModel.getDeliverRules()) {
            sqlSet += ", order_detail.deliverRules = #{deliverRules} ";
        }

        if (0 != orderProdModel.getQuantity()) {
            sqlSet += ", order_detail.quantity = #{quantity} ";
        }

        if (0 != orderProdModel.getDeliverDays()) {
            sqlSet += ", order_detail.deliverDays = #{deliverDays}  ";
        }

        if (null != orderProdModel.getIsGiven()) {
            sqlSet += ", order_detail.isGiven = #{isGiven}";
        }

        sqlSet += ", order_detail.status = #{status} ";

        String sqlWhere = " where 1=1 ";

        if (0 != orderProdModel.getDetailId()) {
            sqlWhere += " and order_detail.id = #{detailId} ";
        }

        if (null != orderProdModel.getProdType()) {
            sqlWhere += " and order_detail.prodType = #{prodType} ";
        }

        if (null != orderProdModel.getOrderId()) {
            sqlWhere += " and order_detail.orderId = #{orderId} ";
        }

        sql = sql + sqlSet + sqlWhere;

        return sql;
    }


    public String addOrderDetial(OrderDetialModel orderDetialModel) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into order_detail ");
        stringBuffer.append(" (orderId,prodId,priceId,quantity,deliverRules,deliverDays,beginDate,endDate,leftDays,status) ");
        stringBuffer.append(" values ");
        stringBuffer.append(" (#{orderId},#{prodId},#{priceId},#{quantity},#{deliverRules},#{deliverDays},#{beginDate},getEndDate(#{deliverRules},#{deliverDays},#{beginDate}),#{deliverDays},1) ");
        System.out.println(stringBuffer.toString());
        return stringBuffer.toString();
    }


    public String getLeft(OrderExpireModel orderExpireModel) {

        StringBuffer sb = new StringBuffer();
        sb.append("");

        return null;
    }



    /*functions below are using for add milk or taste milk --by sunhao*/

    public String getApplyInfo(ApplyModel applyModel) {
        StringBuffer sql = new StringBuffer("SELECT add_taste_apply.applyid,add_taste_apply.deliverid,add_taste_apply.managerid,add_taste_apply.applydate, a.username deliverName,add_taste_apply.prodid, product_info.prodname, add_taste_apply.number, add_taste_apply.deliverdays, add_taste_apply.begindate, b.username managerName" +
                " FROM add_taste_apply join user_info a on add_taste_apply.deliverid = a.userid left join user_info b on add_taste_apply.managerid = b.userid join product_info on add_taste_apply.prodid = product_info.prodid  where 1=1 ");

        StringBuffer sqlWhere = new StringBuffer();

        if (null != applyModel.getManagerId() && !"".equals( applyModel.getManagerId()))
        {
            sqlWhere.append( " and  (add_taste_apply.managerId = #{managerId} or add_taste_apply.deliverId = #{deliverId}) ");
        }
        else
        {
            sqlWhere.append(" and  add_taste_apply.deliverId = #{deliverId} ");
        }

        if (0 != applyModel.getApplyType())
        {
            sqlWhere.append( " and add_taste_apply.applyType = #{applyType} ");
        }


        sql.append(sqlWhere).append(" order by add_taste_apply.applydate  DESC");

        if(applyModel.getLimit()>0)
        {
            sql.append(" limit " + (applyModel.getStartNum() - 1) * applyModel.getLimit() + "," + applyModel.getLimit());
        }


        return sql.toString();

    }


    //查询送奶员上个月25号到今天的配送量
    public String getDeliveredNum(ApplyModel applyModel) {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE)>24?24:cal.get(Calendar.DATE);

        String begindate = year+"-"+(month>=10?""+month:"0"+month)+"-"+25;
        String enddate = year+"-"+(month>=10?""+month+1:"0"+(month+1))+"-"+(day>=10?""+day:"0"+day);


        String sql = " select ifnull(sum(getDaysByRule(order_detail.beginDate,order_detail.endDate,'"+begindate+"','"+enddate+"',order_detail.deliverRules)),0) number from order_detail join (select * from order_info where order_info.deliverId = #{deliverId}) orderinfo where order_detail.orderId = orderinfo.orderid ";
        return sql;
    }


    public String addMilkApply(AddMilkModel addMilkModel) {
        String sql = "insert into add_taste_apply(applytype,prodid,number,deliverdays,begindate,enddate,deliverid,managerid,usertype,applydate)values";
        String datas = "";
        List<ApplyModel> applyModels = addMilkModel.getApplyModelList();

        for (ApplyModel applyModel : applyModels)
        {

            datas += "(" + applyModel.getApplyType() + ",'" + applyModel.getProdId() + "'," + applyModel.getNumber() + "," + applyModel.getDeliverDays() + ",'" + applyModel.getBeginDate() + "',DATE_ADD('" + applyModel.getBeginDate() + "',INTERVAL " +(applyModel.getDeliverDays()-1) + " DAY),'" + applyModel.getDeliverId() + "','" + applyModel.getManagerId().trim() + "'," + applyModel.getUserType() + ",now()),";
        }

        sql = sql + datas.substring(0, datas.length() - 1);

        return sql;
    }

    //查询送奶员上个月25号到今天的申请量
    public String getApplyNumber(ApplyModel applyModel)
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE)>24?24:cal.get(Calendar.DATE);

        String begindate = year+"-"+(month>=10?""+month:"0"+month)+"-"+25;
        String enddate = year+"-"+(month>=10?""+month+1:"0"+(month+1))+"-"+(day>=10?""+day:"0"+day);


        String sql = "select ifnull(sum(getDaysByRule(add_taste_apply.beginDate,add_taste_apply.endDate,'"+begindate+"','"+enddate+"',1)),0) applyNumber from add_taste_apply where  add_taste_apply.applytype=#{applyType} ";

        String sqlWhere = "";

        if(null != applyModel.getManagerId() && "" != applyModel.getManagerId())
        {
            sqlWhere = " and add_taste_apply.managerId = #{managerId} or add_taste_apply.deliverid = #{managerId} ";
        }
        else
        {
            sqlWhere = " and add_taste_apply.deliverid = #{deliverId}  ";
        }

        sql +=sqlWhere;

        return sql;
    }

}
