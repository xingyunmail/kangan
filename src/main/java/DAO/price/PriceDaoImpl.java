package DAO.price;

import model.price.PriceModel;

import java.util.List;

/**
 * Created by ZH on 2015/5/18.
 */
public class PriceDaoImpl {
    public String getList(PriceModel priceModel) {
        String sql = "select pi.prodName,pri.priceId,pri.price,pri.beginDate,pri.endDate,pri.priceType,pri.prodId," +
                " pri.valid,pri.customer,pri.prodType,updateTime,pri.isdelete from price_info pri " +
                " left join product_info pi on pi.prodId= pri.prodId";
        sql += " where pri.isdelete=0  ";

        if (priceModel.getCustomer() != null && "" != priceModel.getCustomer()) {
            //获取特殊客户的商品价格列表（custid ,根据系统时间来查看价格）way = 1查看价格 根据系统时间。
            if (priceModel.getWay() != null && priceModel.getWay() != "" && priceModel.getWay().equals("1")) {
//                sql += " and beginDate ='" + priceModel.getBeginDate() + "' and endDate is null order by updateTime desc";
                if (priceModel.getBeginDate() != null && "" != priceModel.getBeginDate()) {
                    sql += " and customer='"+priceModel.getCustomer()+"' and beginDate <='" + priceModel.getBeginDate() +
                            "' and IFNULL(endDate>='"+priceModel.getBeginDate()+"',1)";
                }
                sql += " order by updateTime desc";

                //更加点击按钮方式 way 来确定用户点击  way = 3查看价格历史修改记录，有效时间是开始-结束是等于
            } else if (priceModel.getWay() != null && priceModel.getWay() != "" && priceModel.getWay().equals("3")) {
                if (priceModel.getBeginDate() != null && "" != priceModel.getBeginDate()) {
                    sql += " and beginDate ='" + priceModel.getBeginDate() + "'";
                }
                if (priceModel.getEndDate() != null && "" != priceModel.getEndDate()) {
                    sql += " and endDate ='" + priceModel.getEndDate() + "'";
                }
                sql += " and customer ='" + priceModel.getCustomer() + "'";
                //way = 2 查看修改记录
            } else if (priceModel.getWay() != null && priceModel.getWay() != "" && priceModel.getWay().equals("2")) {
                sql += " and customer ='" + priceModel.getCustomer() + "' GROUP BY pri.beginDate order by pri.beginDate";
                //way = 4 获取custid=0的所有商品价格
            } else{
                    //查选一或多条customer记录， 根据customerID
                    if(priceModel.getBeginDate() != null && "" != priceModel.getBeginDate()){
                        sql += " and beginDate >='" + priceModel.getBeginDate() + "'";
                    }
                    sql += " and pri.valid=1 and pri.customer!=0  and customer in(" + priceModel.getCustomer() + ") and endDate is null group by customer order by updateTime desc";
                }
        } else {
            if (priceModel.getWay() != null && priceModel.getWay() != "" && priceModel.getWay().equals("4")) {
                sql += " and pri.valid=1 and pri.customer=0 and prodType in (1) ";
            }
        }
        if (priceModel.getLimit() > 0) {
            sql += " limit " + (priceModel.getStartNum() - 1) * priceModel.getLimit() + "," + priceModel.getLimit();
        }
        System.out.println(sql);
        return sql;


    }

    public String getListCount(PriceModel priceModel) {
        String sql = "select count(*) count from price_info pri " +
                " left join product_info pi on pi.prodId= pri.prodId";
        sql += " where pri.isdelete=0  ";
        if (priceModel.getCustomer() != null && "" != priceModel.getCustomer()) {
            //获取特殊客户的商品价格列表（custid ,根据系统时间来查看价格）way = 1查看价格 根据系统时间。
            if (priceModel.getWay() != null && priceModel.getWay() != "" && priceModel.getWay().equals("1")) {
//                sql += " and beginDate ='" + priceModel.getBeginDate() + "' and endDate is null order by updateTime desc";
                if (priceModel.getBeginDate() != null && "" != priceModel.getBeginDate()) {
                    sql += " and customer='"+priceModel.getCustomer()+"' and beginDate <='" + priceModel.getBeginDate() +
                            "' and IFNULL(endDate>='"+priceModel.getBeginDate()+"',1)";
                }
                sql += " order by updateTime desc";

                //更加点击按钮方式 way 来确定用户点击  way = 3查看价格历史修改记录，有效时间是开始-结束是等于
            } else if (priceModel.getWay() != null && priceModel.getWay() != "" && priceModel.getWay().equals("3")) {
                if (priceModel.getBeginDate() != null && "" != priceModel.getBeginDate()) {
                    sql += " and beginDate ='" + priceModel.getBeginDate() + "'";
                }
                if (priceModel.getEndDate() != null && "" != priceModel.getEndDate()) {
                    sql += " and endDate ='" + priceModel.getEndDate() + "'";
                }
                sql += " and customer ='" + priceModel.getCustomer() +"'";
                //way = 2 查看修改记录--按时间分组统计
            } else if (priceModel.getWay() != null && priceModel.getWay() != "" && priceModel.getWay().equals("2")) {
                sql = "select sum(1) from (select count(*) count from price_info pri  left join product_info pi on pi.prodId= pri.prodId where pri.isdelete=0   and customer ="+priceModel.getCustomer()+" GROUP BY pri.beginDate order by pri.beginDate) history_recode";
                //way = 4 获取custid=0的所有商品价格
            } else{
                //查选一或多条customer记录， 根据customerID--按用户分组统计
                sql = "select sum(1) from ( select count(*) from price_info pri LEFT JOIN product_info pi ON pi.prodId = pri.prodId WHERE pri.isdelete = 0 AND pri.valid = 1 AND pri.customer != 0 ";
                if(priceModel.getBeginDate() != null && "" != priceModel.getBeginDate()){
                    sql += " and beginDate >='" + priceModel.getBeginDate() + "'";
                }
                sql += " and customer in(" + priceModel.getCustomer() + ") and endDate is null group by customer order by updateTime desc";
                sql += " )  all_info";
            }
        } else {
            if (priceModel.getWay() != null && priceModel.getWay() != "" && priceModel.getWay().equals("4")) {
                sql += " and pri.valid=1 and pri.customer=0 and prodType in (1) ";
            }
        }
        System.out.println(sql);
        return sql;

    }

    public String batchInsert(PriceModel priceModel){
        String sql = "insert into price_info(price,beginDate,priceType,prodId,valid,customer,prodType,updateTime,isdelete) values  ";

        List<PriceModel> priceModelList = priceModel.getModelList();
        for (int i = 0; i < priceModelList.size(); i++) {
            sql += "("+priceModelList.get(i).getPrice()+",'"+priceModelList.get(i).getEndDate()+"',1,'"+
                    priceModelList.get(i).getProdId()+"',1,'"+priceModelList.get(i).getCustomer()+"',1,now(),0)";
            if (i < priceModelList.size() - 1) {
                sql += ",";
            }
        }
        return sql;
    }

    public String selectMesg(PriceModel priceModel){

        String sql = "select * from price_info where customer='"+priceModel.getCustomer()+"' GROUP BY beginDate,customer  ";

        return sql;
    }
}
