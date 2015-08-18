package service;

import model.Result;
import model.order.*;
import model.product.ProdModel;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by sunhao on 15/5/4.
 */
public interface OrderService {

    Result getOrderList(OrderModel orderModel);

    Result getOrderInfoList(OrderModel orderModel);

    Result getOrderProdList(OrderDetialModel orderProdModel);

    String addOrder(OrderModel orderModel);

    //停奶操作
    Result stopMilk(OrderModel orderModel);

    //启奶操作
    Result activeMilk(OrderModel orderModel);

    //转到订单详情
    ModelAndView toOrderDetail(OrderModel orderModel);

    //存储转奶信息
    Result saveTransferInfo(OrderModel orderModel);

    //确认赠品
    Result confirmGift(OrderDetialModel orderProdModel);

    //获取退奶产品
    Result getReturnProds(OrderDetialModel orderDetialModel);

    //退奶操作
    Result returnMilk(OrderModel orderModel);

    Result getLeft(OrderExpireModel orderExpireModel);

    Result getProductInfo(ProdModel prodModel);

    //换奶操作
    Result exchangeMilk(OrderModel orderModel);

    //换赠品操作
    Result exchangeGift(OrderModel orderModel);

    //获取换奶产品差价
    Result getExchangePrice(OrderModel orderModel);

    //查询申请补奶数据
    Result getAddMilkInfo(ApplyModel applyModel);

    Result addMilkApply(AddMilkModel addMilkModel);

    Result getDisProdByOrder(OrderModel orderModel);

}
