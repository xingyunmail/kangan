package DAO.deliver;

import DAO.DeliverDao;

import model.deliver.DeliverOrderModel;
import model.deliver.ReceiveMilkOrderModel;
import model.order.OrderModel;


import java.util.List;
import java.util.Map;

/**
 * Created by lining on 15-5-13.
 */


public class DeliverDaoImpl{

    public String getDeliverOrderInfo(DeliverOrderModel deliverOrderModel){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM " +
                        "(SELECT a.id," +
                            "b.custId," +
                            "c.userId," +
                            "c.userName," +
                            "b.custName," +
                            "e.areaName," +
                            "b.custPhone phone," +
                            "b.custAddr address," +
                            "d.prodId," +
                            "d.prodName," +
                            "sum(getDaysByRule ( a.beginDate, a.endDate, #{startDate}, #{endDate}, #{orderType})) * a.quantity number " +
                            " FROM order_detail a JOIN order_info b ON a.orderId = b.orderid " +
                            " JOIN user_info c ON b.deliverId = c.userId " +
                            " JOIN product_info d ON a.prodId = d.prodId " +
                            " JOIN area_info e ON b.areaId = e.areaId " +
                            " where 1=1");

        if(null!=deliverOrderModel.getOrderArea() &&  !"".equals(deliverOrderModel.getOrderArea())){
            String[] areaIdArr = deliverOrderModel.getOrderArea().split(",");
            String sqlTemp = " and (";
            for(int i=0;i<areaIdArr.length;i++){
                sqlTemp+=" substr(b.deliverId,1,4)='"+areaIdArr[i]+"' or ";
            }
            sql.append(sqlTemp.substring(0,sqlTemp.lastIndexOf("or")));
            sql.append(")");
        }
        if(null!=deliverOrderModel.getOrderId() &&  !"".equals(deliverOrderModel.getOrderId())){
            String[] orderIdArr = deliverOrderModel.getOrderId().split(",");
            String sqlTemp = " and (";
            for(int i=0;i<orderIdArr.length;i++){
                sqlTemp+=" a.orderId = '"+orderIdArr[i]+"' or ";
            }
            sql.append(sqlTemp.substring(0,sqlTemp.lastIndexOf("or")));
            sql.append(")");
        }
        if(null!=deliverOrderModel.getUserName() &&  !"".equals(deliverOrderModel.getUserName())){
            sql.append(" and c.userName like CONCAT('%',#{userName},'%')");
        }
        if(null!=deliverOrderModel.getProdId() &&  !"".equals(deliverOrderModel.getProdId())){
            String[] productNoArr = deliverOrderModel.getProdId().split(",");
            String sqlTemp = " and (";
            for(int i=0;i<productNoArr.length;i++){
                sqlTemp+=" a.prodId = '"+productNoArr[i]+"' or ";
            }
            sql.append(sqlTemp.substring(0,sqlTemp.lastIndexOf("or")));
            sql.append(")");
        }


        sql.append(" GROUP BY b.custId,b.deliverId,a.prodId ) t where t.number!=0");


        if (deliverOrderModel.getLimit() > 0) {
            sql.append( " limit " + (deliverOrderModel.getStartNum() - 1) * deliverOrderModel.getLimit() + "," + deliverOrderModel.getLimit());
        }

        return sql.toString();
    }

    public String  getDeliverOrderCount(DeliverOrderModel deliverOrderModel){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT count(*) FROM (SELECT a.id," +
                " sum(getDaysByRule ( a.beginDate, a.endDate, #{startDate}, #{endDate}, #{orderType})) * a.quantity number " +
                " FROM order_detail a JOIN order_info b ON a.orderId = b.orderid " +
                " JOIN user_info c ON b.deliverId = c.userId " +
                " JOIN product_info d ON a.prodId = d.prodId "+
                " where 1=1");

        if(null!=deliverOrderModel.getOrderArea() &&  !"".equals(deliverOrderModel.getOrderArea())){
            String[] areaIdArr = deliverOrderModel.getOrderArea().split(",");
            String sqlTemp = " and (";
            for(int i=0;i<areaIdArr.length;i++){
                sqlTemp+=" substr(b.deliverId,1,4)='"+areaIdArr[i]+"' or ";
            }
            sql.append(sqlTemp.substring(0,sqlTemp.lastIndexOf("or")));
            sql.append(")");
        }
        if(null!=deliverOrderModel.getOrderId() &&  !"".equals(deliverOrderModel.getOrderId())){
            String[] orderIdArr = deliverOrderModel.getOrderId().split(",");
            String sqlTemp = " and (";
            for(int i=0;i<orderIdArr.length;i++){
                sqlTemp+=" a.orderId = '"+orderIdArr[i]+"' or ";
            }
            sql.append(sqlTemp.substring(0,sqlTemp.lastIndexOf("or")));
            sql.append(")");
        }
        if(null!=deliverOrderModel.getUserName() &&  !"".equals(deliverOrderModel.getUserName())){
            sql.append(" and c.userName like CONCAT('%','#{userName}','%')");
        }
        if(null!=deliverOrderModel.getProdId() &&  !"".equals(deliverOrderModel.getProdId())){
            String[] productNoArr = deliverOrderModel.getProdId().split(",");
            String sqlTemp = " and (";
            for(int i=0;i<productNoArr.length;i++){
                sqlTemp+=" a.prodId = '"+productNoArr[i]+"' or ";
            }
            sql.append(sqlTemp.substring(0,sqlTemp.lastIndexOf("or")));
            sql.append(")");
        }


        sql.append(" GROUP BY b.custId,b.deliverId,a.prodId ) t where t.number!=0");

        return sql.toString();
    }

    public String getAddMilk(ReceiveMilkOrderModel receiveMilkOrderModel){

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT b.prodPakage prodPackage, a.deliverId userId,c.userName, a.prodId,b.prodName, sum(number) number " +
                   " FROM add_taste_apply a, product_info b,user_info c WHERE a.applytype = 1 " +
                   " ");
        if(null!=receiveMilkOrderModel.getOrderArea() && !"".equals(receiveMilkOrderModel.getOrderArea())){
            sql.append(" AND substr(a.deliverId, 1, 4) = #{orderArea}");
        }
        if(null!=receiveMilkOrderModel.getOrderDate() && !"".equals(receiveMilkOrderModel.getOrderDate())){
            sql.append(" AND a.beginDate <= #{orderDate} AND a.endDate >= #{orderDate}");
        }
        sql.append(" AND a.prodId = b.prodId And a.deliverId = c.userId GROUP BY deliverId, prodId");
        return sql.toString();
    }

    public String getTasteMilk(ReceiveMilkOrderModel receiveMilkOrderModel){

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT b.prodPakage prodPackage, a.deliverId userId,c.userName, a.prodId,b.prodName,sum(number) number " +
                " FROM add_taste_apply a, product_info b,user_info c WHERE a.applytype = 2 " +
                " ");
        if(null!=receiveMilkOrderModel.getOrderArea() && !"".equals(receiveMilkOrderModel.getOrderArea())){
            sql.append(" AND substr(a.deliverId, 1, 4) = #{orderArea}");
        }
        if(null!=receiveMilkOrderModel.getOrderDate() && !"".equals(receiveMilkOrderModel.getOrderDate())){
            sql.append(" AND a.beginDate <= #{orderDate} AND a.endDate >= #{orderDate}");
        }
        sql.append(" AND a.prodId = b.prodId AND a.deliverId=c.userId GROUP BY deliverId, prodId");

        return sql.toString();
    }



}
