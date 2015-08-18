package DAO.total;

import model.total.TotalModel;

/**
 * Created by zhaijinxu on 15-5-14.
 */
public class TotalDaoImpl {

    //查询production_info表中的数据
    public String getDate(TotalModel totalModel){
        String wheresql = "";
        if(totalModel.getStartDate()!=null&&totalModel.getStartDate()!=""){
            wheresql+="'" + totalModel.getStartDate()+"'," ;
        }
        if(totalModel.getEndDate()!=null&&totalModel.getEndDate()!=""){
            wheresql+= "'" + totalModel.getEndDate()+"'," ;
        }
        String sql = "SELECT " +
                " prodName," +
                " sum(total) total" +
                " FROM" +
                " (" +
                " SELECT" +
                " pi.prodName," +
                " pi.prodId," +
                " sum(" +
                " getDaysByRule (" +
                " od.beginDate," +
                " od.endDate," +
                 wheresql+
                " 1" +
                " ) * number" +
                " ) AS total" +
                " FROM" +
                " add_taste_apply od," +
                " product_info pi" +
                " WHERE" +
                " od.prodId = pi.prodId" +
                " GROUP BY" +
                " od.prodId" +
                " UNION ALL" +
                " SELECT" +
                " pi.prodName," +
                " pi.prodId," +
                " sum(" +
                " getDaysByRule (" +
                " od.beginDate," +
                " od.endDate," +
                  wheresql+
                " od.deliverRules" +
                " ) * quantity" +
                " ) AS total" +
                " FROM" +
                " order_detail od," +
                " product_info pi" +
                " WHERE" +
                " od.prodId = pi.prodId" +
                " AND od.prodId IN (" +
                " SELECT" +
                " prodId" +
                " FROM" +
                " price_info" +
                " WHERE" +
                " prodType != 2" +
                " )" +
                " GROUP BY" +
                " prodId" +
                " ) a" +
                " GROUP BY" +
                " a.prodId";
        System.out.println(sql);
        return sql;
    }
}
