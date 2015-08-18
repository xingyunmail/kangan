package service.base;

import model.Result;
import model.base.product.Product;
import model.price.PriceModel;
import model.product.ProdModel;
import org.springframework.stereotype.Service;


@Service
public interface ProductService {

    Result addProduct(Product  product,PriceModel priceModel);
    Result deleteProduct(Product  product,String priceType);
    Result updateProduct(Product  product,PriceModel priceModel);
    Result getProductById(ProdModel prodModel);
    Result getList(ProdModel prodModel);

}
