package service.base.product;

import DAO.PriceDao;
import DAO.ProductDao;
import model.Result;
import model.Status;
import model.base.product.Product;
import model.price.PriceModel;
import model.product.ProdModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.base.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private PriceDao priceDao;

    @Override
    //http://localhost:8082/product/addProduct?prodId=12345&prodName=哈密瓜&prodweight=11&prodPakage=22&prodStatus=1
    public Result addProduct(Product product, PriceModel priceModel) {
        Result result = new Result();
        //获取最后一条信息id
        int prodId = productDao.getLastProudctId();

        product.setProdId((prodId + 1) + "");
        priceModel.setProdId((prodId + 1) + "");
        int num = productDao.addProudct(product);
        if (num > 0) {
            priceDao.insertPrice(priceModel);
            result.setStatus(Status.success);
            result.setData(num);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result deleteProduct(Product product, String priceType) {
        Result result = new Result();
        int num = productDao.deleteProudct(product);
        if (num > 0) {
            PriceModel priceModel = new PriceModel();
            priceModel.setProdId(product.getProdId());
            priceDao.deletePrice(priceModel);
            result.setStatus(Status.success);
            result.setData(num);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
//    http://localhost:8082/product/updateProduct?prodId=12345&prodName=哈00密瓜&prodweight=11&prodPakage=22&prodStatus=1
    public Result updateProduct(Product product, PriceModel priceModel) {
        Result result = new Result();
        product.setProdStatus("0");
        int num = productDao.updateProudct(product);
        if (num > 0) {
            priceModel.setValid(1);
            priceDao.updatePrice(priceModel);

            result.setStatus(Status.success);
            result.setData(num);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    public Result getProductById(ProdModel prodModel) {
        Result result = new Result();
        ProdModel product = productDao.getProductById(prodModel);
        result.setStatus(Status.success);
        result.setData(product);
        return result;
    }

    //http://localhost:8082/product/getProductList?prodType=2  查询所有
    public Result getList(ProdModel prodModel) {
        Result result = new Result();
        List<ProdModel> list = productDao.getProductByCity(prodModel);
        if (list.size() > 0) {
            int count = productDao.getProductByCityCount(prodModel);
            result.setCount(count);
            result.setStatus(Status.success);
            result.setData(list);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }
}
