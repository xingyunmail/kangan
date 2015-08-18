package service;

import model.Result;
import model.price.PriceModel;

import java.util.List;

/**
 * Created by ZH on 2015/5/18.
 */
public interface PriceService {
    public Result getList(PriceModel priceModel);

    public Result saveList(PriceModel priceModel);

}
