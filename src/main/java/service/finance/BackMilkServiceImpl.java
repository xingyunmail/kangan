package service.finance;

import DAO.BackMilkDao;
import model.Result;
import model.Status;
import model.finance.BackMilkModel;
import model.finance.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import service.BackMilkService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaijinxu on 15-5-22.
 */

@Service
public class BackMilkServiceImpl implements BackMilkService {

    @Autowired
    private BackMilkDao backMilkDao ;

    @Override
    public Result getDate(BackMilkModel backMilkModel){
        Result result = new Result() ;
        ProductModel productModel = new ProductModel() ;

        List<BackMilkModel> backMilkModelList = backMilkDao.getDate(backMilkModel);
        int counts = backMilkDao.getDateInfo(backMilkModel);
        List<ProductModel> productModelList  = new ArrayList<ProductModel>();
        if (backMilkModelList.size() > 0) {


            for(int i = 0 ; i < backMilkModelList.size() ; i++){
                String orderId = backMilkModelList.get(i).getOrderId() ;
                productModel.setOrderId(orderId) ;
                productModelList =  backMilkDao.getInfo(productModel) ;
                backMilkModelList.get(i).setProductModel(productModelList);
            }
            result.setStatus(Status.success);
            result.setData(backMilkModelList);
            result.setCount(counts);

        } else {
            result.setStatus(Status.noRecord);
        }
        return result;

    }

}
