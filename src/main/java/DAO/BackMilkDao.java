package DAO;

import DAO.finance.BackMilkDaoImpl;
import model.finance.BackMilkModel;
import model.finance.ProductModel;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhaijinxu on 15-5-22.
 */
@Repository
public interface BackMilkDao {

    @SelectProvider(type = BackMilkDaoImpl.class,method = "getDate")
    public List<BackMilkModel> getDate(BackMilkModel backMilkModel);

    @SelectProvider(type = BackMilkDaoImpl.class,method = "getInfo")
    public List<ProductModel> getInfo(ProductModel productModel);

    @SelectProvider(type = BackMilkDaoImpl.class,method = "getDateInfo")
    public int getDateInfo(BackMilkModel backMilkModel);
}
