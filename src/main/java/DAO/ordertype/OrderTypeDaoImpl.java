package DAO.ordertype;

import DAO.OrderTypeDao;
import model.orderType.OrderTypeModel;

/**
 * Created by zzq on 15-5-15.
 */
public class OrderTypeDaoImpl {
    public String getOrderTypeInfo(OrderTypeModel orderTypeModel){
        String str="select a.*,b.itemDiscrible priceTypeName from order_type a,dic_item b where a.priceType=b.itemValue and b.itemKey='PRICE_TYPE' and a.valid=1 order by a.id desc";
        return str;
    }
}
