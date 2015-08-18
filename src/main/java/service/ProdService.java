package service;

import model.Result;
import model.price.PriceModel;
import model.product.ProdModel;

/**
 * Created by sunhao on 15/5/4.
 */
public interface ProdService {

    Result getProdInfo(ProdModel prodModel);

    Result getProductMessage(ProdModel prodModel);

    Result getProdPriceInfo(ProdModel prodModel);

    Result getProdNumName(ProdModel prodModel);

    Result getProdInfoById(ProdModel prodModel);

}
