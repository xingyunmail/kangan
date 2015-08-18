package DAO;

import DAO.util.UtilDaoImpl;
import controller.Order;
import model.dictionary.DictModel;
import model.milkbox.MilkBox;
import model.order.OrderModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunhao on 15/5/4.
 */
@Repository
public interface UtilDao {
    @SelectProvider(type = UtilDaoImpl.class, method = "getDictInfo")
    public List<DictModel> getDictInfo(DictModel dictModel);


    @Update("insert into  milkbox_info(boxId,customeId,boxStatus) values(#{boxId},#{customeId},1)")
    public int insertMilkBox(MilkBox milkBox);

    @Update("update milkbox_info set milkbox_info.boxStatus=0 where milkbox_info.boxId = #{boxId} and milkbox_info.boxStatus = 1 and milkbox_info.customeId=#{customeId}")
    public int returnMilkBox(MilkBox milkBox);


    //转奶调用存储过程
    @Select("call updateCustomer(#{orderId},#{custId},#{custName},#{custPhone},#{custAddr},#{deliverId},#{orderDate})")
    public int insertTransferInfo(OrderModel orderModel);

}
