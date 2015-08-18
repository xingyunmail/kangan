package DAO.finance;

import model.finance.BackMilkModel;
import model.finance.ProductModel;

/**
 * Created by zhaijinxu on 15-5-22.
 */
public class BackMilkDaoImpl {
    /**获取已退订单**/
    public String getDate(BackMilkModel backMilkModel){
        String sql = "";        //执行的SQL
        String condition = "" ; //已退定原始订单创建时间条件
        String csql = "";       //已经退订原始订单创建时间条件拼接到SQL
        String wheresql = "";   //已退订单的操作时间
        String orderSql = "";   //根据订单编号查询
        String areaId_info = "";//根据区域查询条件
        String page = "";//分页

        /**查询已退的原始订单创建时间**/
        boolean start = backMilkModel.getStartDate()!=null&&backMilkModel.getStartDate()!="";
        boolean end = backMilkModel.getEndDate()!=null&&backMilkModel.getEndDate()!="" ;
        if(start&&end){
            if(backMilkModel.getStartDate()!=null&&backMilkModel.getStartDate()!=""){
                condition+=" info.orderDate>= '" + backMilkModel.getStartDate()+"'" ;
            }
            if(backMilkModel.getEndDate()!=null&&backMilkModel.getEndDate()!=""){
                condition+= " and info.orderDate <= '" + backMilkModel.getEndDate()+"'" ;
            }
            csql = " AND rm.orderId IN ("
                    +" SELECT "
                    +" info.orderId "
                    +" FROM "
                    +" order_info info "
                    +" WHERE "
                    + condition
                    +" ) ";
            System.out.println("已退的原始订单创建时间=============="+csql);
        }

        /**根据订单编号查询订单详情**/
        if(backMilkModel.getOrderId()!=""&&backMilkModel.getOrderId()!=null){
            orderSql = " AND rm.orderId like '%"+backMilkModel.getOrderId()+"%'" ;
            System.out.println("根据订单编号查询订单详情============="+backMilkModel.getOrderId());

        }


        /**根据区域条件查询已经退订订单详情**/
        if(backMilkModel.getAreaId()!=""&&backMilkModel.getAreaId()!=null){
            System.out.println("==================区域===================="+backMilkModel.getAreaId());
            areaId_info =" AND rm.orderId"
                        +" IN("
                        +" SELECT"
                        +" detail.orderId"
                        +" FROM"
                        +" returnMilk_info detail,"
                        +" ("
                        +" SELECT"
                        +" info.orderId"
                        +" FROM"
                        +" order_info info"
                        +" WHERE"
                        +" info.deliverId IN ("
                        +" SELECT"
                        +" userId"
                        +" FROM"
                        +" user_info"
                        +" WHERE"
                        +" areaId IN ("
                        +  backMilkModel.getAreaId()
                        +" )"
                        +" )"
                        +" ) oo"
                        +" WHERE"
                        +" oo.orderId = detail.orderId"
                        +" GROUP BY"
                        +" detail.orderId"
                        +" )";
        }


        /**查询已退订单的操作时间**/
        if(backMilkModel.getStartDateBack()!=null&&backMilkModel.getStartDateBack()!=""){
            wheresql+=" AND rm.returnDate>= '" + backMilkModel.getStartDateBack()+" 00:00:00'" ;
        }
        if(backMilkModel.getEndDateBack()!=null&&backMilkModel.getEndDateBack()!=""){
            wheresql+= " AND rm.returnDate <= '" + backMilkModel.getEndDateBack()+" 23:59:59'" ;
        }

        sql = "SELECT "
                +" rm.returnDate backDate, "
                +" rm.reason reasons, "
                +" oi.deliverId, "
                +" ui.userName, "
                +" rm.orderId orderId, "
                +" oi.custId custNo, "
                +" oi.custPhone phoneNo, "
                +" oi.custAddr address, "
                +" pi.prodName, "
                +" rm.prodNum, "
                +" rm.returnPrice, "
                +" SUM(rm.returnPrice) as  returnTotal "
                +" FROM "
                +" returnMilk_info rm, "
                +" order_info oi, "
                +" user_info ui , "
                +" product_info pi "
                +" WHERE "
                +" rm.orderId = oi.orderid "
                +" AND "
                +" oi.deliverId=ui.userId "
                +" AND "
                +" pi.prodId = rm.prodId "
                +  csql
                +  orderSql
                +  areaId_info
                +  wheresql
                +" GROUP BY "
                +" rm.orderId " ;
         if(backMilkModel.getLimit() > 0){
            page =  " limit " + (backMilkModel.getStartNum() - 1) * backMilkModel.getLimit() + "," + backMilkModel.getLimit();
         }
        System.out.println("backMilkModel.getLimit()========================"+backMilkModel.getLimit());
        System.out.println(sql+page);
        return sql +page ;
    }



    //分页查询
    public String getDateInfo(BackMilkModel backMilkModel){
        String sql = "";        //执行的SQL
        String condition = "" ; //已退定原始订单创建时间条件
        String csql = "";       //已经退订原始订单创建时间条件拼接到SQL
        String wheresql = "";   //已退订单的操作时间
        String orderSql = "";   //根据订单编号查询
        String areaId_info = "";//根据区域查询条件

        /**查询已退的原始订单创建时间**/
        boolean start = backMilkModel.getStartDate()!=null&&backMilkModel.getStartDate()!="";
        boolean end = backMilkModel.getEndDate()!=null&&backMilkModel.getEndDate()!="" ;
        if(start&&end){
            if(backMilkModel.getStartDate()!=null&&backMilkModel.getStartDate()!=""){
                condition+=" info.orderDate>= '" + backMilkModel.getStartDate()+"'" ;
            }
            if(backMilkModel.getEndDate()!=null&&backMilkModel.getEndDate()!=""){
                condition+= " and info.orderDate <= '" + backMilkModel.getEndDate()+"'" ;
            }
            csql = " AND rm.orderId IN ("
                    +" SELECT "
                    +" info.orderId "
                    +" FROM "
                    +" order_info info "
                    +" WHERE "
                    + condition
                    +" ) ";
            System.out.println("已退的原始订单创建时间=============="+csql);
        }

        /**根据订单编号查询订单详情**/
        if(backMilkModel.getOrderId()!=""&&backMilkModel.getOrderId()!=null){
            orderSql = " AND rm.orderId like '%"+backMilkModel.getOrderId()+"%'" ;
            System.out.println("根据订单编号查询订单详情============="+backMilkModel.getOrderId());

        }


        /**根据区域条件查询已经退订订单详情**/
        if(backMilkModel.getAreaId()!=""&&backMilkModel.getAreaId()!=null){
            System.out.println("==================区域===================="+backMilkModel.getAreaId());
            areaId_info =" AND rm.orderId"
                    +" IN("
                    +" SELECT"
                    +" detail.orderId"
                    +" FROM"
                    +" returnMilk_info detail,"
                    +" ("
                    +" SELECT"
                    +" info.orderId"
                    +" FROM"
                    +" order_info info"
                    +" WHERE"
                    +" info.deliverId IN ("
                    +" SELECT"
                    +" userId"
                    +" FROM"
                    +" user_info"
                    +" WHERE"
                    +" areaId IN ("
                    +  backMilkModel.getAreaId()
                    +" )"
                    +" )"
                    +" ) oo"
                    +" WHERE"
                    +" oo.orderId = detail.orderId"
                    +" GROUP BY"
                    +" detail.orderId"
                    +" )";
        }


        /**查询已退订单的操作时间**/
        if(backMilkModel.getStartDateBack()!=null&&backMilkModel.getStartDateBack()!=""){
            wheresql+=" AND rm.returnDate>= '" + backMilkModel.getStartDateBack()+" 00:00:00'" ;
        }
        if(backMilkModel.getEndDateBack()!=null&&backMilkModel.getEndDateBack()!=""){
            wheresql+= " AND rm.returnDate <= '" + backMilkModel.getEndDateBack()+" 23:59:59'" ;
        }

        sql = "SELECT count(*) counts "
                +" FROM( SELECT"
                +" rm.returnDate backDate, "
                +" rm.reason reasons, "
                +" rm.orderId orderId, "
                +" oi.custId custNo, "
                +" oi.custPhone phoneNo, "
                +" oi.custAddr address, "
                +" pi.prodName, "
                +" rm.prodNum, "
                +" rm.returnPrice, "
                +" SUM(rm.returnPrice) as  returnTotal "
                +" FROM "
                +" returnMilk_info rm, "
                +" order_info oi, "
                +" product_info pi "
                +" WHERE "
                +" rm.orderId = oi.orderid "
                +" AND "
                +" pi.prodId = rm.prodId "
                +  csql
                +  orderSql
                +  areaId_info
                +  wheresql
                +" GROUP BY "
                +" rm.orderId)e " ;


        System.out.println(sql);
        return sql ;
    }












    /**获取订单中产品详情**/
    public String getInfo(ProductModel productModel){

        String sql =" SELECT"
                +" pi.prodName, "
                +" rm.prodNum number, "
                +" rm.returnPrice price"
                +" FROM "
                +" returnMilk_info rm ,"
                +" product_info pi "
                +" WHERE rm.orderId = '"
                +productModel.getOrderId()
                +"' "
                +"  AND pi.prodId = rm.prodId";
        System.out.println(sql);
        return sql ;
    }


}
