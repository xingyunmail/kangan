package service.orderType;

import DAO.OrderTypeDao;
import model.Result;
import model.Status;
import model.orderType.OrderTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.OrderTypeService;

import java.util.List;

/**
 * Created by zzq on 15-5-15.
 */
@Service
public class OrderTypeServiceImpl implements OrderTypeService {

    @Autowired
    private OrderTypeDao orderTypeDao;

    @Override
    public Result getOrderTypeInfo(OrderTypeModel orderTypeModel){
        Result result = new Result();
        List<OrderTypeModel> list = orderTypeDao.getOrderTypeInfo(orderTypeModel);
        int count = orderTypeDao.getOrderTypeInfoCount(orderTypeModel);
        if(list.size()>0){
            result.setStatus(Status.success);
            result.setData(list);
            result.setCount(count);
        }else{
            result.setStatus(Status.noRecord);
        }
        return result;
    }
    @Override
    public Result del(OrderTypeModel orderTypeModel){
        Result result = new Result();
        try{
            orderTypeDao.del(orderTypeModel);
            result.setStatus(Status.success);
        }catch (Exception e){
            result.setStatus(Status.error);
        }
        return result;
    }
    @Override
    public Result add(OrderTypeModel orderTypeModel){
        Result result = new Result();
        try{
            orderTypeDao.add(orderTypeModel);
            result.setStatus(Status.success);
        }catch (Exception e){
            result.setStatus(Status.error);
        }
        return result;
    }
    @Override
    public Result update(OrderTypeModel orderTypeModel){
        Result result = new Result();
        try{
            orderTypeDao.update(orderTypeModel);
            result.setStatus(Status.success);
        }catch (Exception e){
            result.setStatus(Status.error);
        }
        return result;
    }
}
