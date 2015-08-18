package DAO.finance;

import model.finance.ExchangeCostModel;
import model.finance.FinanceModel;

import java.util.List;


/**
 * Created by scar on 15/5/20.
 */
public class FinanceDaoImpl {

    public String getNewList(FinanceModel financeModel) {
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT b.userName, c.areaName, count(a.id) orderCounts, sum(a.orderPrice) priceCounts, a.deliverId userId FROM order_info a, user_info b, area_info c, order_type d WHERE  a.deliverId = b.userId AND b.areaId = c.areaId AND a.orderType = d.id AND d.receiveType = 2 ");
        if (financeModel.getStartDate() != null) {
            sb.append(" AND a.orderDate >= #{startDate} ");
        }
        if (financeModel.getEndDate() != null) {
            sb.append(" AND a.orderDate <= #{endDate} ");
        }
        if (financeModel.getAreaId() != null && financeModel.getAreaId().length() > 0) {
            sb.append(" AND b.areaId in (" + financeModel.getAreaId() + ")");
        }
        if (financeModel.getUserId() != null && financeModel.getUserId().length() > 0) {
            sb.append(" AND a.deliverId =#{userId} ");
        }
        sb.append(" GROUP BY a.deliverId ");

        if (financeModel.getLimit() > 0) {
            sb.append(" limit " + (financeModel.getStartNum() - 1) * financeModel.getLimit() + "," + financeModel.getLimit());
        }
        return sb.toString();
    }


    public String getNewListCounts(FinanceModel financeModel) {
        StringBuffer sb = new StringBuffer();

        sb.append("select count(*) counts from (SELECT b.userName, c.areaName, count(a.id) orderCounts, sum(a.orderPrice) priceCounts, a.deliverId userId FROM order_info a, user_info b, area_info c, order_type d WHERE  a.deliverId = b.userId AND b.areaId = c.areaId AND a.orderType = d.id AND d.receiveType = 2 ");
        if (financeModel.getStartDate() != null) {
            sb.append(" AND a.orderDate >= #{startDate} ");
        }
        if (financeModel.getEndDate() != null) {
            sb.append(" AND a.orderDate <= #{endDate} ");
        }
        if (financeModel.getAreaId() != null && financeModel.getAreaId().length() > 0) {
            sb.append(" AND b.areaId in (" + financeModel.getAreaId() + ")");
        }
        if (financeModel.getUserId() != null && financeModel.getUserId().length() > 0) {
            sb.append(" AND a.deliverId =#{userId} ");
        }
        sb.append(" GROUP BY a.deliverId) e");
        return sb.toString();
    }

    public String getNewInfo(FinanceModel financeModel) {
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT *, GROUP_CONCAT(e.product) products, GROUP_CONCAT(e.price) prices, sum( e.price * e.quantity * e.deliverDays ) totalPrice FROM ( SELECT a.orderid, a.custId, a.custAddr, a.custPhone, b.quantity, b.deliverDays, CONCAT_WS( '*', CONVERT (c.prodName, CHAR), CONVERT (b.quantity, CHAR), CONVERT (b.deliverDays, CHAR)) product, d.price FROM order_info a, order_detail_history b, product_info c, price_info d, user_info e WHERE a.deliverId = e.userId AND a.orderid = b.orderId AND b.prodId = c.prodId AND b.priceId = d.priceId ");

        if (financeModel.getStartDate() != null) {
            sb.append(" AND a.orderDate >= #{startDate} ");
        }
        if (financeModel.getEndDate() != null) {
            sb.append(" AND a.orderDate <= #{endDate} ");
        }

        if (financeModel.getUserId() != null && financeModel.getUserId().length() > 0) {
            sb.append(" AND a.deliverId =#{userId} ");
        }
        sb.append(")  e group by e.orderid");
        return sb.toString();
    }

    public String getChangeList(ExchangeCostModel exchangeCostModel) {

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT a.exchangeId exchangeId, a.orderId orderId,b.userId deliverId, b.userName deliverName, sum(a.diffPrice) diffPrice, count(DISTINCT(a.orderId)) exchangeOrderCount" +
                " FROM exchange_info a, user_info b,order_info c where a.deliverId = b.userId and a.orderId=c.orderId ");

        if (null != exchangeCostModel.getStartDate() && !"".equals(exchangeCostModel.getStartDate())) {
            sql.append(" and a.changeDate >= #{startDate}");
        }
        if(null != exchangeCostModel.getEndDate() && !"".equals(exchangeCostModel.getEndDate())) {
            sql.append(" and a.changeDate <= #{endDate}");
        }
        if(null!=exchangeCostModel.getDeliverId() && !"".equals(exchangeCostModel.getDeliverId())){
            String[] deliverIdArr = exchangeCostModel.getDeliverId().split(",");
            String sqlTemp = " and (";
            for(int i=0;i<deliverIdArr.length;i++){
                sqlTemp+=" a.deliverId = '"+deliverIdArr[i]+"' or ";
            }
            sql.append(sqlTemp.substring(0,sqlTemp.lastIndexOf("or")));
            sql.append(")");
        }
        if(null!=exchangeCostModel.getOrderArea() && !"".equals(exchangeCostModel.getOrderArea())){
            String[] areaIdArr = exchangeCostModel.getOrderArea().split(",");
            String sqlTemp = " and (";
            for(int i=0;i<areaIdArr.length;i++){
                sqlTemp+=" substr(a.deliverId,1,4) = '"+areaIdArr[i]+"' or ";
            }
            sql.append(sqlTemp.substring(0,sqlTemp.lastIndexOf("or")));
            sql.append(")");
        }

        if(null!=exchangeCostModel.getOrderType() && !"".equals(exchangeCostModel.getOrderType())){
            sql.append(" and c.orderType=#{orderType}");
        }
        if(exchangeCostModel.getIsLessZero()==1){
            sql.append(" and a.diffPrice<0");
        }
        sql.append(" GROUP BY a.deliverId");
        return sql.toString();
    }


    /*public String getOrderChangeInfoByCust(ExchangeCostModel exchangeCostModel){

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT a.orderId, a.custId, a.exchangeId, a.changeDate, sum(a.diffPrice) diffCount FROM exchange_info a  where a.deliverId=#{deliverId}");
        if(exchangeCostModel.getIsLessZero()==1){
            sql.append(" and a.diffPrice<0 ");
        }
        sql.append(" GROUP BY a.custId");
        return sql.toString();
    }
    public String getChangeInfo(ExchangeCostModel exchangeCostModel){

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT distinct a.exchangeId, a.custId, b.custName, b.custAddr, a.changeDate, a.diffPrice FROM exchange_info a, order_info b,exchange_prod c WHERE a.custId = #{custId} AND a.orderId = b.orderid and a.exchangeId=c.exchangeId  ");
        if(exchangeCostModel.getIsLessZero()==1){
            sql.append(" and a.diffPrice<0");
        }
        return sql.toString();
    }

    */

    public String getOrderChangeInfoByCust(ExchangeCostModel exchangeCostModel){

        System.out.println("deliverId="+exchangeCostModel.getDeliverId());
        System.out.println("startDate="+exchangeCostModel.getStartDate());
        System.out.println("endDate="+exchangeCostModel.getEndDate());
        System.out.println("orderType="+exchangeCostModel.getOrderType());


        StringBuffer sql = new StringBuffer();
        sql.append("SELECT a.orderId, a.custId, a.exchangeId, a.changeDate, sum(a.diffPrice) diffCount " +
                "FROM exchange_info a, order_info c where  a.deliverId=#{deliverId} and   a.orderId=c.orderId ");
        if (null != exchangeCostModel.getStartDate() && !"".equals(exchangeCostModel.getStartDate())) {
            sql.append(" and a.changeDate >= #{startDate}");
        }
        if(null != exchangeCostModel.getEndDate() && !"".equals(exchangeCostModel.getEndDate())) {
            sql.append(" and a.changeDate <= #{endDate}");
        }
        if(null!=exchangeCostModel.getOrderType() && !"".equals(exchangeCostModel.getOrderType())){
            sql.append(" and c.orderType=#{orderType}");
        }

        if(exchangeCostModel.getIsLessZero()==1){
            sql.append(" and a.diffPrice<0 ");
        }
        sql.append(" GROUP BY a.custId");
        return sql.toString();
    }



    public String getChangeInfo(ExchangeCostModel exchangeCostModel){

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT distinct a.exchangeId, a.custId, b.custName, b.custAddr, a.changeDate, a.diffPrice FROM exchange_info a, order_info b,exchange_prod c WHERE a.custId = #{custId} AND a.orderId = b.orderid and a.exchangeId=c.exchangeId  ");
        if(exchangeCostModel.getIsLessZero()==1){
            sql.append(" and a.diffPrice<0");
        }
        return sql.toString();
    }

}
