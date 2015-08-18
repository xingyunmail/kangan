package service;

import model.Result;
import model.order.OrderExpireModel;

/**
 * Created by scar on 15/5/26.
 */
public interface OrderExpireService {
    Result getExpireOrderList(OrderExpireModel orderExpireModel);
}
