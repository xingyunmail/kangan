package controller;

import model.Result;
import model.Status;
import model.milkbox.MilkBox;
import model.order.*;
import model.product.ProdModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.OrderExpireService;
import service.OrderService;
import service.ProdService;
import service.UtilService;
import service.order.OrderServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * Created by scar on 15/4/29.
 */
@Controller
@RequestMapping("/order")
public class Order {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProdService prodService;

    @Autowired
    private UtilService utilService;
    @Autowired
    private OrderExpireService orderExpireService;


    @RequestMapping("toList")
    public ModelAndView toListOrder() {
        return new ModelAndView("order/list");
    }

    @RequestMapping("toTaste")
    public ModelAndView toTaste() {
        return new ModelAndView("order/taste");
    }

    @RequestMapping("toRenew")
    public ModelAndView toRenew() {
        return new ModelAndView("order/renew");
    }

    @RequestMapping(value = "orderDict")
    @ResponseBody
    public Result getOrderType() {
        return utilService.getDicts("ORDER_TYPE");
    }

    @RequestMapping(value = "deliDict")
    @ResponseBody
    public Result getdeliType() {
        return utilService.getDicts("DELI_TYPE");
    }

    @RequestMapping(value = "getProdInfo")
    @ResponseBody
    public Result getProdInfo(ProdModel prodModel) {
        return prodService.getProdInfo(prodModel);
    }

    @RequestMapping(value = "getOrders")
    @ResponseBody
    public Result getOrderList(OrderModel orderModel) {
        return orderService.getOrderList(orderModel);
    }


    @RequestMapping(value = "getOrderInfo")
    @ResponseBody
    public Result getOrders(OrderModel orderModel)
    {
        return orderService.getOrderInfoList(orderModel);
    }

    @RequestMapping(value = "getOrderProds")
    @ResponseBody
    public Result getOrderProds(OrderDetialModel orderProdModel) {
        return orderService.getOrderProdList(orderProdModel);
    }


    @RequestMapping(value = "stopMilk", method = RequestMethod.POST)
    @ResponseBody
    public Result stopMilkCommit(@RequestBody OrderModel orderModel) {
        return orderService.stopMilk(orderModel);
    }

    @RequestMapping(value = "activeMilk", method = RequestMethod.POST)
    @ResponseBody
    public Result activeMilkCommit(@RequestBody OrderModel orderModel) {
        return orderService.activeMilk(orderModel);
    }


    @RequestMapping(value = "installBox", method = RequestMethod.POST)
    @ResponseBody
    public Result installBox(MilkBox milkBox) {
        return utilService.installMilkBox(milkBox);
    }


    @RequestMapping(value = "returnBox", method = RequestMethod.POST)
    @ResponseBody
    public Result returnMilkBox(MilkBox milkBox) {
        return utilService.returnMilkBox(milkBox);
    }

    @RequestMapping(value = "confirmGift", method = RequestMethod.POST)
    @ResponseBody
    public Result confirmGift(OrderDetialModel orderDetialModel) {
        return orderService.confirmGift(orderDetialModel);
    }

    @RequestMapping(value = "toDetail")
    public ModelAndView toOrderDetail(OrderModel orderModel) {
        return orderService.toOrderDetail(orderModel);
    }

    @RequestMapping(value = "getProdList", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(ProdModel prodModel) {
        return prodService.getProductMessage(prodModel);
    }

    @RequestMapping(value = "saveTransfer", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTransferInfo(OrderModel orderModel) {
        return orderService.saveTransferInfo(orderModel);
    }

    @RequestMapping(value = "getReturnProds")
    @ResponseBody
    public Result getReturnProds(OrderDetialModel orderDetialModel) {
        return orderService.getReturnProds(orderDetialModel);
    }

    @RequestMapping(value = "returnMilk", method = RequestMethod.POST)
    @ResponseBody
    public Result returnMilk(@RequestBody OrderModel orderModel) {
        return orderService.returnMilk(orderModel);
    }

    @RequestMapping(value = "exchangeMilk", method = RequestMethod.POST)
    @ResponseBody
    public Result exchangeProd(@RequestBody OrderModel orderModel) {
        return orderService.exchangeMilk(orderModel);
    }


    @RequestMapping(value = "getExchangePrice", method = RequestMethod.POST)
    @ResponseBody
    public Result getExchangePrice(@RequestBody OrderModel orderModel)
    {
        return orderService.getExchangePrice(orderModel);
    }


    @RequestMapping(value = "getSelectGift", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectGift(OrderModel orderModel) {
        return orderService.getDisProdByOrder(orderModel);
    }

    @RequestMapping(value = "exchangeGift", method = RequestMethod.POST)
    @ResponseBody
    public Result exchangeGift(@RequestBody OrderModel orderModel)
    {
        return orderService.exchangeGift(orderModel);
    }

    /* functions for  add milk or taste milksunhao 2015-05-22 */
    @RequestMapping(value = "getAddMilkInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result getAddMilkInfo(ApplyModel applyModel) {
        return orderService.getAddMilkInfo(applyModel);
    }

    @RequestMapping(value = "addApply", method = RequestMethod.POST)
    @ResponseBody
    public Result addMilkApply(@RequestBody AddMilkModel addMilkModel) {
        return orderService.addMilkApply(addMilkModel);
    }


    /**
     * lu.wang
     */

    @RequestMapping("toAdd")
    public ModelAndView toAddOrder() {
        return new ModelAndView("order/add");
    }

    @RequestMapping("toExpire")
    public ModelAndView toExpireOrder() {
        return new ModelAndView("order/expire");
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Result addOrder(@RequestBody OrderModel orderModel) {
        System.out.println(orderModel);
        Result result = new Result();
        result.setStatus(Status.success);
        try {
            String str = orderService.addOrder(orderModel);
            if (str.equals("success")) {
                result.setStatus(Status.success);
            } else {
                result.setStatus(Status.error);
                result.setData(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(Status.error);
        }
        return result;
    }


    @RequestMapping(value = "expireList")
    @ResponseBody
    public Result getExpireOrderList(OrderExpireModel orderExpireModel) {
        return orderExpireService.getExpireOrderList(orderExpireModel);
    }


    @RequestMapping(value="getProdById")
    @ResponseBody
    public Result getProdById(ProdModel prodModel){
        return prodService.getProdInfoById(prodModel);
    }

//
//    public String test() {
//
////        addOrder(#{orderId},#{prodId},#{priceId},#{quantity},
//// #{deliverRules},#{deliverDays},#{beginDate},#{areaId},#{deliverId})"
//        Random r = new Random();
//
//
//
//
//        for (int i = 40500; i < 80000; i++) {
////
//        Date date=randomDate("2015-05-01", "2015-12-31");
//        SimpleDateFormat dateformat2=new SimpleDateFormat("yyyy-MM-dd");
//        String a2=dateformat2.format(date);
//
//        OrderDetialModel orderDetialModel = new OrderDetialModel();
//        orderDetialModel.setOrderId("z" + i);
//        orderDetialModel.setProdId(r.nex tInt(11) + 100 + "");
//        orderDetialModel.setPriceId(r.nextInt(11) + 100 + "");
//        orderDetialModel.setQuantity(r.nextInt(3) + 1);
//        orderDetialModel.setDeliverRules(r.nextInt(3) + 1);
//        orderDetialModel.setDeliverDays(r.nextInt(100) + 300);
//        orderDetialModel.setBeginDate(a2);
//        orderDetialModel.setAreaId(r.nextInt(4) + 10);
//        orderDetialModel.setDeliverId(r.nextInt(98) + 1000);
//        orderService.test(orderDetialModel);
////        System.out.println(orderDetialModel);
//            System.out.println(i);
//        }
//        return null;
//    }
//    /**
//     * 生成随机时间
//     *
//     * @param beginDate
//     * @param endDate
//     * @return
//     */
//    private static Date randomDate(String beginDate, String endDate) {
//
//        try {
//
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//            Date start = format.parse(beginDate);//构造开始日期
//
//            Date end = format.parse(endDate);//构造结束日期
//
////getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
//
//            if (start.getTime() >= end.getTime()) {
//
//                return null;
//
//            }
//
//            long date = random(start.getTime(), end.getTime());
//
//            return new Date(date);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//        return null;
//
//    }
//    private static long random(long begin, long end) {
//
//        long rtn = begin + (long) (Math.random() * (end - begin));
//
////如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
//
//        if (rtn == begin || rtn == end) {
//
//            return random(begin, end);
//
//        }
//
//        return rtn;
//
//    }


}
