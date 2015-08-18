package service.order;

import DAO.*;
import model.Result;
import model.Status;
import model.customer.CustomerModel;
import model.dictionary.DictModel;
import model.order.*;
import model.order.OrderModel;
import model.order.OrderDetialModel;
import model.order.OrderModel;
import model.order.ReturnMilkModel;
import model.product.ProdModel;
import model.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import service.OrderService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sunhao on 15/5/4.
 * 订单业务类
 */
@Service
public class OrderServiceImpl implements OrderService {

    //赠品是否赠送
    private final static String GIFT_IS_GIVEN = "1";

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private UtilDao utilDao;

    @Autowired
    private ProductDao productDao;


    @Autowired
    private UserDao userDao;

    /**
     * 订单查询
     *
     * @param orderModel
     * @return Result 包含订单信息的LIST
     */
    public Result getOrderList(OrderModel orderModel) {
        Result result = new Result();

        List<OrderModel> orderList = orderDao.getOrderInfo(orderModel);
        orderModel.setLimit(0);
        int count = orderDao.getOrderInfo(orderModel).size();
        if (null != orderList && orderList.size() > 0) {
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.noRecord);
        }

        result.setData(orderList);
        result.setCount(count);


        return result;
    }

    /**
     * 订单查询(2015-07-20修改)
     * sunhao
     * @param orderModel
     * @return Result 包含订单信息的LIST
     */
    public Result getOrderInfoList(OrderModel orderModel) {
        Result result = new Result();

        List<QueryOrderModel> orderList = orderDao.getOrders(orderModel);

        //获取count
        int count = orderDao.getOrdersCount(orderModel);

        if (null != orderList && orderList.size() > 0)
        {
            result.setStatus(Status.success);
            //构建OrderModel数据，用于在前台显示
            orderList = buildQueryOrder(orderList);
        }
        else
        {
            result.setStatus(Status.noRecord);
        }

        result.setData(orderList);
        result.setCount(count);

        return result;
    }


    /**
     * 构建集合
     * @param orderList
     * @return
     */
    public List<QueryOrderModel> buildQueryOrder(List<QueryOrderModel> orderList)
    {
        for(QueryOrderModel queryOrderModel:orderList)
        {
            String detail = queryOrderModel.getDetail();
            String[] details = detail.split("@@");
            List<OrderDetialModel> orderDetialModelList = new ArrayList<OrderDetialModel>();

            for(String d :details)
            {
                OrderDetialModel orderDetialModel = new OrderDetialModel();

                String[] detailInfo = d.split("##");

                orderDetialModel.setDetailId(Integer.parseInt(detailInfo[0]));
                orderDetialModel.setProdId(detailInfo[1]);
                orderDetialModel.setProdName(detailInfo[2]);
                orderDetialModel.setProdType(detailInfo[3]);
                orderDetialModel.setProdTypeName(detailInfo[4]);
                orderDetialModel.setQuantity(Integer.parseInt(detailInfo[5]));
                orderDetialModel.setDeliverDays(Integer.parseInt(detailInfo[6]));
                orderDetialModel.setDeliverRules(Integer.parseInt(detailInfo[7]));
                orderDetialModel.setBeginDate(detailInfo[8]);
                orderDetialModel.setEndDate(detailInfo[9]);
                orderDetialModel.setStopDate(detailInfo[10]);
                orderDetialModel.setStatus(Integer.parseInt(detailInfo[11]));

                orderDetialModelList.add(orderDetialModel);
            }

            queryOrderModel.setOrderDetail(orderDetialModelList);
        }
        return orderList;
    }

    @Override
    public Result getOrderProdList(OrderDetialModel orderProdModel) {
        Result result = new Result();

        List<OrderDetialModel> orderProdList = orderDao.getOrderProds(orderProdModel);
        //获取剩余配送量
        for (int i = 0; i < orderProdList.size(); i++) {
            int leftNum = orderDao.getLeftNumbers(orderProdList.get(i));
            orderProdList.get(i).setLeftNum(leftNum);
        }


        if (null != orderProdList && orderProdList.size() > 0) {
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.noRecord);
        }

        result.setData(orderProdList);


        return result;
    }


    @Override
    public Result getDisProdByOrder(OrderModel orderModel) {
        Result result = new Result();

        List<ProdModel> prodModels = orderDao.getDisProds(orderModel);
        if (null == prodModels) {
            result.setStatus(Status.error);
        } else if (0 == prodModels.size()) {
            result.setStatus(Status.noRecord);
        } else {
            result.setStatus(Status.success);
        }

        result.setData(prodModels);

        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addOrder(OrderModel orderModel) {
        if (orderModel.getCustId() == null || orderModel.getCustId().length() == 0) {
            CustomerModel customerModel = new CustomerModel();
            String newId = customerDao.getNewCustomerId(orderModel.getDeliverId());

            if (newId.length() == 4) {
                customerModel.setCustId(orderModel.getDeliverId() + newId);
            } else {
                return "customerError";
            }
            customerModel.setAddress(orderModel.getCustAddr());
            customerModel.setPhone(orderModel.getCustPhone());
            customerModel.setCustName(orderModel.getCustName());
            customerModel.setStatus(1);
            int insertRow = customerDao.insertCustomer(customerModel);
            if (insertRow == 1) {
                System.out.println(customerModel.getCustId());
                orderModel.setCustId(customerModel.getCustId());
            } else {
                return "customerError";
            }
        }
        orderModel.setAreaId(orderModel.getDeliverId().substring(0, 4));
        orderDao.addOrder(orderModel);
        for (OrderDetialModel odm : orderModel.getDetialList()) {
            if (odm.getBeginDate().equals("0000-00-00")) {
                odm.setBeginDate(null);
                odm.setStatus(2);
            } else {
                odm.setStatus(1);
            }
            orderDao.addOrderDetial(odm);
            orderDao.addOrderDetialHitory(odm);
        }
        orderDao.updateOrderPrice(orderModel);
        return "success";
    }


    @Override
    @Transactional
    public Result stopMilk(OrderModel orderModel) {
        Result result = new Result();

        List<OrderDetialModel> detailList = orderModel.getDetialList();

        int num = 0;
        for (OrderDetialModel orderProdModel : detailList) {

            if ("".equals(orderProdModel.getBeginDate())) {
                orderProdModel.setBeginDate(null);
            }
            num += orderDao.stopMilk(orderProdModel);
        }

        if (num == detailList.size()) {
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.error);
        }
        result.setData(num);

        return result;

    }

    @Override
    @Transactional
    public Result activeMilk(OrderModel orderModel) {
        Result result = new Result();

        List<OrderDetialModel> detailList = orderModel.getDetialList();

        int num = 0;
        for (OrderDetialModel orderProdModel : detailList) {
            num += orderDao.activeMilk(orderProdModel);
        }

        if (num == detailList.size()) {
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.error);
        }
        result.setData(num);

        return result;
    }


    @Override
    public Result confirmGift(OrderDetialModel orderProdModel) {
        Result result = new Result();
        int affect = 0;
        String[] orderids = orderProdModel.getOrderId().split(",");
        //前台传来的ID为多个ID，在此分割
        for (String orderid : orderids) {
            orderProdModel.setOrderId(orderid);
            orderProdModel.setIsGiven(GIFT_IS_GIVEN);

            affect += orderDao.updateOrderProd(orderProdModel);
        }

        if (affect > 0) {
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.error);
        }
        result.setData(affect);
        return result;
    }


    @Override
    public ModelAndView toOrderDetail(OrderModel orderModel) {
        OrderModel om = orderDao.getOrderInfo(orderModel).get(0);

        //获取开户员名字
        UserModel addUser = new UserModel();
        addUser.setUserId(om.getAddUserId());
        om.setAddUserName(userDao.info(addUser).get(0).getUserName());

        OrderDetialModel detail = new OrderDetialModel();
        detail.setOrderId(orderModel.getOrderId());
        List<OrderDetialModel> prodList = orderDao.getOrderProds(detail);

        //获取剩余配送量
        for (int i = 0; i < prodList.size(); i++) {
            int leftNum = orderDao.getLeftNumbers(prodList.get(i));
            prodList.get(i).setLeftNum(leftNum);
        }

        ModelMap map = new ModelMap();
        map.addAttribute("oderModel", om);
        map.addAttribute("prodList", convertToJS(prodList));

        return new ModelAndView("order/orderdetail", map);

    }

    //将LIST数据转换成JS文本之后给到页面上
    public String convertToJS(List<OrderDetialModel> orderDetialModels) {
        String result = "<script type='application/javascript'> var prodList=[";
        String data = "";
        for (OrderDetialModel orderDetialModel : orderDetialModels) {
            data += "{'prodiId':'" + orderDetialModel.getProdId()
                    + "','prodType':'" + orderDetialModel.getProdType()
                    + "','prodTypeName':'" + orderDetialModel.getProdTypeName()
                    + "','prodName':'" + orderDetialModel.getProdName()
                    + "','prodPrice':'" + orderDetialModel.getProdPrice()
                    + "','quantity':'" + orderDetialModel.getQuantity()
                    + "','deliverDays':'" + orderDetialModel.getDeliverDays()
                    + "','deliverRules':'" + orderDetialModel.getDeliverRules()
                    + "','beginDate':'" + orderDetialModel.getBeginDate()
                    + "','endDate':'" + orderDetialModel.getEndDate()
                    + "','leftNum':'" + orderDetialModel.getLeftNum()
                    + "','detailId':'" + orderDetialModel.getDetailId()
                    + "','isGiven':'" + orderDetialModel.getIsGiven()
                    + "'},";
        }
        result += data + "];</script>";
        return result;

    }

    @Override
    @Transactional
    public Result saveTransferInfo(OrderModel orderModel) {
        Result result = new Result();

        int resultNum = utilDao.insertTransferInfo(orderModel);
        if (resultNum == 0) {
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.error);
        }
        result.setData(resultNum);

        return result;
    }


    @Override
    public Result getProductInfo(ProdModel prodModel) {
        Result result = new Result();

        List<ProdModel> prodModelList = productDao.getProductMessage(prodModel);

        if (null == prodModelList) {
            result.setStatus(Status.error);
        } else if (0 == prodModelList.size()) {
            result.setStatus(Status.noRecord);
        } else {
            result.setStatus(Status.success);
        }

        result.setData(prodModelList);

        return result;

    }


    @Override
    public Result getReturnProds(OrderDetialModel orderDetialModel) {

        Result result = new Result();
        List<OrderDetialModel> orderProdList = orderDao.getOrderProds(orderDetialModel);

        for (int i = 0; i < orderProdList.size(); i++) {
            int leftNum = orderDao.getLeftNumbers(orderProdList.get(i));
            orderProdList.get(i).setLeftNum(leftNum);
        }

        if (0 != orderProdList.size()) {
            result.setStatus(Status.success);
        } else if (0 == orderProdList.size()) {
            result.setStatus(Status.noRecord);
        } else {
            result.setStatus(Status.error);
        }

        result.setData(orderProdList);
        return result;
    }


    @Override
    @Transactional
    public Result returnMilk(OrderModel orderModel) {
        Result result = new Result();

        int affects = 0;
        List<OrderDetialModel> details = orderModel.getDetialList();

        for (OrderDetialModel detail : details) {
            ReturnMilkModel rmm = transToReturnModel(detail);
            affects += orderDao.returnMilk(rmm);
        }
        if (affects == details.size()) {
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.error);
        }

        result.setData(affects);

        return result;

    }

    @Override
    public Result getLeft(OrderExpireModel orderExpireModel) {

        orderDao.getLeft(orderExpireModel);


        return null;
    }


    /**
     * 通过OrderDetialModel构建ReturnMilkModel
     *
     * @param orderDetialModel
     * @return
     */
    public ReturnMilkModel transToReturnModel(OrderDetialModel orderDetialModel) {
        ReturnMilkModel returnMilkModel = new ReturnMilkModel();

        returnMilkModel.setOrderId(orderDetialModel.getOrderId());//订单编号
        returnMilkModel.setProdId(orderDetialModel.getProdId());//退订产品
        returnMilkModel.setReturnPrice(orderDetialModel.getProdPrice());//价格
        returnMilkModel.setProdNum(orderDetialModel.getLeftNum());//退订量
        returnMilkModel.setReason(orderDetialModel.getProdTypeName());//退订原因
        returnMilkModel.setDetailId(orderDetialModel.getDetailId());//订购详情编码

        return returnMilkModel;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result exchangeMilk(OrderModel orderModel) {
        Result result = new Result();

        List<OrderDetialModel> details = orderModel.getDetialList();

        int new_affects = 0;
        int old_affects = 0;
        int updateAffect = 0;
        int orderUpdate = 0;

        //exchange_info入表
        OrderDetialModel detail = details.get(0);
        orderDao.addExchangeInfo(detail);
        long exchangeId = detail.getExchangeId();
        String[] detailids = new String[]{};

        if (exchangeId != 0) {
            //新产品exchange_prod入表
            for (OrderDetialModel orderDetialModel : details) {
                orderDetialModel.setExchangeId(exchangeId);
                if (orderDetialModel.getStatus() == 1) {
                    detailids = orderDetialModel.getDetailIds().split(",");
                }
                //新旧产品入表处理

                new_affects += orderDao.addExchangeNew(orderDetialModel);
            }


            if (new_affects == details.size()) {
                //旧产品exchange_prod入库
                for (int i = 0; i < detailids.length; i++) {
                    old_affects += orderDao.addExchangeOld(detailids[i], exchangeId);
                }

                //更新exchange_info diffprice
                updateAffect = orderDao.updateExchangePrice(exchangeId);
                if (1 == updateAffect) {
                    orderUpdate = orderDao.updateOrderInfoPrice(detail);
                }

                //更新订单总价
                if (1 == orderUpdate) {
                    result.setStatus(Status.success);
                } else {
                    result.setStatus(Status.error);
                }

            } else {
                result.setStatus(Status.error);
            }

        } else {
            result.setStatus(Status.error);
        }

        result.setData(exchangeId);

        return result;
    }

    @Override
    public Result exchangeGift(OrderModel orderModel) {
        Result result = new Result();

        List<OrderDetialModel> details = orderModel.getDetialList();

        int new_affects = 0;
        int old_affects = 0;

        //exchange_info入表
        OrderDetialModel detail = details.get(0);
        orderDao.addExchangeInfo(detail);
        long exchangeId = detail.getExchangeId();
        String[] detailids = new String[]{};

        if (exchangeId != 0) {
            //新产品exchange_prod入表
            for (OrderDetialModel orderDetialModel : details) {
                orderDetialModel.setExchangeId(exchangeId);
                if (orderDetialModel.getStatus() == 1) {
                    detailids = orderDetialModel.getDetailIds().split(",");
                }
                //新旧产品入表处理
                new_affects += orderDao.addExchangeGift(orderDetialModel);
            }

            if (new_affects == details.size()) {
                //旧产品exchange_prod入库
                for (int i = 0; i < detailids.length; i++) {
                    old_affects += orderDao.addExchangeOld(detailids[i], exchangeId);
                }
                if (detailids.length == old_affects) {
                    result.setStatus(Status.success);
                }
            } else {
                result.setStatus(Status.error);
            }

        } else {
            result.setStatus(Status.error);
        }

        result.setData(exchangeId);

        return result;
    }


    //获取更换产品的差价
    @Override
    public Result getExchangePrice(OrderModel orderModel) {
        Result result = new Result();
        List<OrderDetialModel> detailModels = orderModel.getDetialList();

        for (int i = 0; i < detailModels.size(); i++) {
            if (0 == detailModels.get(i).getStatus()) {
                detailModels.remove(i);
            }
        }

        float afterPrice = 0;

        for (OrderDetialModel detailModel : detailModels) {
            if (0 != detailModel.getLeftNum() && 0 != detailModel.getQuantity()) {
                float price = orderDao.getProdPrice(detailModel);
                if (0 != price) {
                    afterPrice += price;
                }
            } else {
                result.setStatus(Status.error);
                break;
            }

        }

        if (afterPrice != 0) {
            result.setStatus(Status.success);
        }

        result.setData(afterPrice);
        return result;
    }


    /*functions below are using for add milk or taste milk*/

    @Override
    public Result getAddMilkInfo(ApplyModel applyModel) {
        Result result = new Result();

        List<ApplyModel> applyModelList = orderDao.getApplyInfo(applyModel);


        applyModel.setLimit(0);
        int count = orderDao.getApplyInfo(applyModel).size();


        ApplyModel applyModelTotal = new ApplyModel();
        //查询出上月申请量
        applyModelTotal.setApplyNumber(orderDao.getApplyNumber(applyModel));

        applyModelTotal.setDeliveredNum(orderDao.getDeliveredNum(applyModel));

        applyModelList.add(applyModelTotal);


        if (applyModelList == null) {
            result.setStatus(Status.error);
        } else if (applyModelList.size() == 1) {
            result.setStatus(Status.noRecord);
        } else {
            result.setStatus(Status.success);
        }

        result.setData(applyModelList);
        result.setCount(count);

        return result;

    }

    @Override
    public Result addMilkApply(AddMilkModel addMilkModel) {
        Result result = new Result();
        int affects = 0;


        affects = orderDao.addMilkApply(addMilkModel);

        if (affects == addMilkModel.getApplyModelList().size()) {
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.error);
        }

        result.setData(affects);

        return result;

    }

}
