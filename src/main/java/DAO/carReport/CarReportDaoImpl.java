package DAO.carReport;

import model.carReport.CarReportModel;

/**
 * Created by zhaijinxu on 15-5-19.
 */
public class CarReportDaoImpl {

    public String getData(CarReportModel carReportModel){

        String condition = "";
        if(carReportModel.getProdDate()!=null&&carReportModel.getProdDate()!=""){
            condition = carReportModel.getProdDate() ;
        }

        String lineID = "";
        if(carReportModel.getLineid()!=""&&carReportModel.getLineid()!=null){
            lineID = carReportModel.getLineid() ;
        }

        String sql = "";
        sql = " SELECT " +
                " t.prodName prodName," +
                " t.areaName areaName," +
                " t.areaId areaId," +
                " t.prodId prodId," +
                " SUM(t.total) total" +
                " FROM " +
                " (" +
                " (" +
                " SELECT" +
                " pi.prodName prodName," +
                " ai.areaName areaName," +
                " oi.areaId areaId," +
                " od.prodId prodId," +
                " sum(" +
                " getDaysByRule (" +
                " od.beginDate," +
                " od.endDate,'" +
                  condition+
                "','" +
                  condition+
                "'," +
                " od.deliverRules" +
                " ) * quantity" +
                " ) AS total" +
                " FROM" +
                " order_detail od," +
                " product_info pi," +
                " order_info oi," +
                " (" +
                " SELECT" +
                " *" +
                " FROM" +
                " area_info" +
                " WHERE" +
                " lineid = '"+
                  lineID+
                "' ) ai" +
                " WHERE" +
                " od.prodId = pi.prodId" +
                " AND ai.areaId = oi.areaId" +
                " AND od.orderId = oi.orderid" +
                " AND od.status not in(0,3,5)"+
                " AND od.prodId IN (" +
                " SELECT" +
                " prodId" +
                " FROM" +
                " price_info" +
                " WHERE" +
                " prodType != 2" +
                " )" +
                " GROUP BY" +
                " od.prodId," +
                " oi.areaId" +
                " )" +
                " UNION ALL" +
                " (" +
                " SELECT  " +
                " a.prodName," +
                " ai.areaName," +
                " a.areaId," +
                " a.prodId," +
                " a.total" +
                " FROM area_info ai," +
                " (" +
                " SELECT" +
                " od.prodId ," +
                " pt.prodName," +
                " ui.areaId," +
                " sum(" +
                " getDaysByRule (" +
                " od.beginDate," +
                " od.endDate,'" +
                  condition+
                "','" +
                  condition+
                "'," +
                "1" +
                " ) * number" +
                " ) AS total" +
                " FROM" +
                " add_taste_apply od," +
                " product_info pt," +
                " user_info ui" +
                " WHERE pt.prodId = od.prodId" +
                " AND ui.userId = od.deliverid" +
                " GROUP BY" +
                " od.prodId," +
                " od.deliverid" +
                " ) a WHERE a.areaId = ai.areaId" +
                " AND a.areaId IN(" +
                " SELECT" +
                " areaId" +
                " FROM" +
                " area_info" +
                " WHERE" +
                " lineid = '"+
                  lineID+
                "' )" +
                " )  " +
                " )as t where total > 0 " +
                " GROUP BY " +
                " t.areaId," +
                " t.prodId" ;

//        System.out.println(sql);
        return sql ;
    }

}
