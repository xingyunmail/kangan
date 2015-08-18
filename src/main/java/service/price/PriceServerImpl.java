package service.price;

import DAO.PriceDao;
import model.Result;
import model.Status;
import model.price.PriceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.PriceService;
import service.tools.ToolService;

import java.util.List;

/**
 * Created by ZH on 2015/5/18.
 */
@Service
public class PriceServerImpl implements PriceService {
    @Autowired
    private PriceDao priceDao;

    @Override
    public Result getList(PriceModel priceModel) {
        Result result = new Result();

        if (priceModel.getWay() != null && priceModel.getWay() != "" && priceModel.getWay().equals("1")){
            priceModel.setBeginDate(ToolService.getCrtDateYYYYMMDDHHmmss());
        }

        List<PriceModel> priceModelList =  priceDao.getList(priceModel);
       if(priceModelList.size()>0){
            Integer count = priceDao.getListCount(priceModel);
            result.setStatus(Status.success);
            result.setCount(count);
        }else{
            result.setStatus(Status.noRecord);
        }
        result.setData(priceModelList);

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveList(PriceModel priceModel) {
        Result result = new Result();
        if(priceModel.getModelList().size()>0){
            //插入调价后的N 条记录
            priceDao.batchInsertUpdatePrice(priceModel);
            //更新上一次开始日期的结束日期
            System.out.println(priceModel.getModelList().get(0).getCustomer()+","+priceModel.getModelList().get(0).getBeginDate()+"--"+priceModel.getModelList().get(0).getEndDate());

            PriceModel pmd = new PriceModel();
            pmd.setBeginDate(priceModel.getModelList().get(0).getBeginDate());
            pmd.setEndDate(priceModel.getModelList().get(0).getEndDate());
            pmd.setCustomer(priceModel.getModelList().get(0).getCustomer());
            priceDao.updateCoustomerPrice(pmd);
            result.setStatus(Status.success);
        }

//        for (OrderDetialModel item : orderModel.getDetialList()) {
//        for (PriceModel item : orderModel.getDetialList()) {
//            System.out.println(item);
//            orderDao.addOrderDetials(item);
//        }
//        orderDao.updateOrderPrice(orderModel);

        return  result;

    }
}
