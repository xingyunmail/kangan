package service;

import model.Result;
import model.orderType.OrderTypeModel;

/**
 * Created by zzq on 15-5-15.
 */
public interface OrderTypeService {
    public Result getOrderTypeInfo(OrderTypeModel orderTypeModel);

    public Result del(OrderTypeModel orderTypeModel);

    public Result add(OrderTypeModel orderTypeModel);

    public Result update(OrderTypeModel orderTypeModel);
}
