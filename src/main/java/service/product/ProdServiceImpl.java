package service.product;

import DAO.ProductDao;
import model.Result;
import model.Status;
import model.price.PriceModel;
import model.product.ProdModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ProdService;

import java.util.List;

/**
 * Created by sunhao on 15/5/4.
 */
@Service
public class ProdServiceImpl implements ProdService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Result getProdInfo(ProdModel prodModel) {
        Result result = new Result();

        ProdModel resultProd = productDao.getProdInfo(prodModel);
        if (null != resultProd) {
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.noRecord);
        }

        result.setData(resultProd);

        return result;
    }

    /**
     * 获取商品信息
     * @param prodModel
     * @return
     */
    @Override
    public Result getProductMessage(ProdModel prodModel) {
        Result result = new Result();

        List<ProdModel> resultProd = productDao.getProductMessage(prodModel);
        if (resultProd.size() > 0) {
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.noRecord);
        }

        result.setData(resultProd);

        return result;
    }

    @Override
    public Result getProdPriceInfo(ProdModel prodModel) {
        Result result = new Result();
        List<ProdModel> prodModelList = productDao.getProdPriceInfo(prodModel);
        if (prodModelList.size() > 0) {
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.noRecord);
        }
        result.setData(prodModelList);
        return result;
}


    @Override
    public Result getProdNumName(ProdModel prodModel)
    {
        Result result = new Result();

        List<ProdModel> resultProd = productDao.getProdNumName(prodModel);

        if (resultProd.size() > 0) {
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.noRecord);
        }

        result.setData(resultProd);

        return result;
    }


    public Result getProdInfoById(ProdModel prodModel){
        Result result = new Result();
        ProdModel pm = productDao.getProdInfoById(prodModel);
        if(pm!=null){
            result.setStatus(Status.success);
            result.setData(pm);
        }else{
            result.setStatus(Status.noRecord);
        }

        return result;
    }

}
