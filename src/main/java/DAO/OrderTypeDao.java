package DAO;

import DAO.ordertype.OrderTypeDaoImpl;
import model.orderType.OrderTypeModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zzq on 15-5-15.
 */
@Repository
public interface OrderTypeDao {

    @Select("select a.*,b.itemDiscrible priceTypeName from order_type a,dic_item b where a.priceType=b.itemValue and b.itemKey='PRICE_TYPE' and a.valid=1 order by a.id desc")
    public List<OrderTypeModel> getOrderTypeInfo(OrderTypeModel orderTypeModel);

    @Select("select count(*) count from order_type a,dic_item b where a.priceType=b.itemValue and b.itemKey='PRICE_TYPE' and a.valid=1 order by a.id desc")
    public int getOrderTypeInfoCount(OrderTypeModel orderTypeModel);

    @Update("update order_type set valid=0 where id=#{id} ")
    public void del(OrderTypeModel orderTypeModel);

    @Insert("insert into order_type (name,priceType,receiveType) values(#{name},#{priceType},#{receiveType})")
    public void add(OrderTypeModel orderTypeModel);

    @Update("update order_type set name=#{name},priceType=#{priceType},receiveType=#{receiveType} where id=#{id}")
    public void update(OrderTypeModel orderTypeModel);
}
