package service.order;

import DAO.OrderExpireDao;
import model.Result;
import model.Status;
import model.order.OrderExpireModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.OrderExpireService;

import java.util.List;

/**
 * Created by scar on 15/5/26.
 */
@Service
public class OrderExpireServiceImpl implements OrderExpireService {


    @Autowired
    private OrderExpireDao orderExpireDao;

    @Override
    public Result getExpireOrderList(OrderExpireModel orderExpireModel) {

        Result result = new Result();

        List<OrderExpireModel> OrderExpireModels = orderExpireDao.getExpireOrderList(orderExpireModel);
        int counts = orderExpireDao.getExpireOrderCount(orderExpireModel);

        if (OrderExpireModels.size() > 0) {
            result.setStatus(Status.success);
            result.setData(OrderExpireModels);
            result.setCount(counts);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;

    }
}
