package DAO.workorder;

import com.sun.org.apache.xpath.internal.SourceTree;
import model.workorder.WorkOrderModel;
import model.workorder.WorkOrdersModel;

/**
 * Created by zzq on 15-5-7.
 */
public class WorkOderDaoImpl {
    public String getPurchaseStr(WorkOrderModel workOrderModel){
        return "";
    }

    public String add(WorkOrderModel workOrderModel){
        String strSql = "";
        if(workOrderModel.getType()==1){ //订购
            strSql = " insert into work_order (workNum,type,customerName,customerPhone,milkUser,address,area,remark) VALUES('"+workOrderModel.getWorkNum()+"',1,'"+workOrderModel.getCustomerName()+"','"+workOrderModel.getCustomerPhone()+"','"+workOrderModel.getMilkUser()+"','"+workOrderModel.getAddress()+"','"+workOrderModel.getAreaName()+"','"+workOrderModel.getRemark()+"') ";
        }else { //投诉
            strSql = " insert into work_order (workNum,type,customerName,customerPhone,milkUser,address,area,compID,products,remark) VALUES('"+workOrderModel.getWorkNum()+"',2,'"+workOrderModel.getCustomerName()+"','"+workOrderModel.getCustomerPhone()+"','"+workOrderModel.getMilkUser()+"','"+workOrderModel.getAddress()+"','"+workOrderModel.getAreaName()+"',"+workOrderModel.getCompID()+",'"+workOrderModel.getProducts()+"','"+workOrderModel.getRemark()+"') ";
        }
        return strSql;
    }

    public String getList(WorkOrdersModel workOrdersModel){
        String strSql="";
        String orderStr=" order by e.createTime desc";
        if(workOrdersModel.getType() ==1){
           strSql="SELECT * FROM ( SELECT a.*, b.itemDiscrible, c.areaName, d.userName FROM work_order a INNER JOIN dic_item b ON a.`status` = b.itemValue INNER JOIN area_info c ON a.area = c.areaId LEFT JOIN user_info d ON a.milkUser = d.userId WHERE a.type = 1 AND b.itemKey = 'WORK_TYPE' ) e WHERE 1 = 1 ";
        }
        if(workOrdersModel.getType() ==2){
            strSql = "SELECT * FROM ( SELECT a.*, b.itemDiscrible, concat(c. NAME, '—', c.compName) AS complains, d.areaName, f.userName FROM work_order a INNER JOIN dic_item b ON a.`status` = b.itemValue INNER JOIN complaints_info c ON a.compID = c.id INNER JOIN area_info d ON a.area = d.areaId LEFT JOIN user_info f ON a.milkUser = f.userId WHERE a.type = 2 AND b.itemKey = 'WORK_TYPE' ) e WHERE 1 = 1  ";

        }
        if(workOrdersModel.getBeginTime().trim()!=""){
            strSql +=" and e.createTime >= '"+workOrdersModel.getBeginTime()+"'";
        }
        if(workOrdersModel.getEndTime().trim()!=""){
            strSql +=" and e.createTime <= '"+workOrdersModel.getEndTime()+"'";
        }
        if(workOrdersModel.getStats().trim()!=""){
            strSql +=" and e.status = "+workOrdersModel.getStats()+" ";
        }
        if(workOrdersModel.getArea().trim()!=""){
            strSql +=" and e.area in ("+workOrdersModel.getArea()+")";
        }
        if(workOrdersModel.getLimit()>0){
            strSql +=" limit "+(workOrdersModel.getStartNum() - 1) * workOrdersModel.getLimit()+" ,"+workOrdersModel.getLimit()+"";
        }
        return strSql+orderStr;
    }

    public String getLIstCount(WorkOrdersModel workOrdersModel){
        String strSql="";
        String orderStr=" order by e.createTime desc";
        if(workOrdersModel.getType() ==1){
            strSql="SELECT count(*) count FROM ( SELECT a.*, b.itemDiscrible, c.areaName, d.userName FROM work_order a INNER JOIN dic_item b ON a.`status` = b.itemValue INNER JOIN area_info c ON a.area = c.areaId LEFT JOIN user_info d ON a.milkUser = d.userId WHERE a.type = 1 AND b.itemKey = 'WORK_TYPE' ) e WHERE 1 = 1 ";
        }
        if(workOrdersModel.getType() ==2){
            strSql = "SELECT count(*) count FROM ( SELECT a.*, b.itemDiscrible, concat(c. NAME, '—', c.compName) AS complains, d.areaName, f.userName FROM work_order a INNER JOIN dic_item b ON a.`status` = b.itemValue INNER JOIN complaints_info c ON a.compID = c.id INNER JOIN area_info d ON a.area = d.areaId LEFT JOIN user_info f ON a.milkUser = f.userId WHERE a.type = 2 AND b.itemKey = 'WORK_TYPE' ) e WHERE 1 = 1  ";

        }
        if(workOrdersModel.getBeginTime().trim()!=""){
            strSql +=" and e.createTime >= '"+workOrdersModel.getBeginTime()+"'";
        }
        if(workOrdersModel.getEndTime().trim()!=""){
            strSql +=" and e.createTime <= '"+workOrdersModel.getEndTime()+"'";
        }
        if(workOrdersModel.getStats().trim()!=""){
            strSql +=" and e.status = "+workOrdersModel.getStats()+" ";
        }
        if(workOrdersModel.getArea().trim()!=""){
            strSql +=" and e.area in ("+workOrdersModel.getArea()+")";
        }
        return strSql+orderStr;
    }

    public String update(WorkOrderModel workOrderModel){
        String strSql = "update work_order set `status` = "+workOrderModel.getStatus()+",editRemark='"+workOrderModel.getEditRemark()+"' where id= "+workOrderModel.getId();
        return strSql;
    }
}
