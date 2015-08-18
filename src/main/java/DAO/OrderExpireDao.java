package DAO;

import DAO.order.OrderExpireDaoImpl;
import model.order.OrderExpireModel;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by scar on 15/5/26.
 */
@Repository
public interface OrderExpireDao {


    @SelectProvider(type = OrderExpireDaoImpl.class, method = "getExpireOrderList")
    List<OrderExpireModel> getExpireOrderList(OrderExpireModel orderExpireModel);

    @SelectProvider(type = OrderExpireDaoImpl.class, method = "getExpireOrderCount")
    int getExpireOrderCount(OrderExpireModel orderExpireModel);
}
