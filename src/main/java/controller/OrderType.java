package controller;

import model.Result;
import model.orderType.OrderTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.OrderTypeService;
import service.UtilService;

/**
 * Created by zzq on 15-5-15.
 */
@Controller
@RequestMapping("/orderType")
public class OrderType {

    @Autowired
    private OrderTypeService orderTypeService;

    @Autowired
    private UtilService utilService;

    @RequestMapping("toList")
    public ModelAndView getInfo(){
        return new ModelAndView("orderType/list");
    }


    @RequestMapping(value = "orderTypeInfo")
    @ResponseBody
    public Result getOrderTypeInfo(OrderTypeModel orderTypeModel){
        return orderTypeService.getOrderTypeInfo(orderTypeModel);
    }

    @RequestMapping(value = "del")
    @ResponseBody
    public Result delete(OrderTypeModel orderTypeModel){
        return orderTypeService.del(orderTypeModel);
    }

    @RequestMapping(value = "add")
    @ResponseBody
    public Result add(OrderTypeModel orderTypeModel){
        return orderTypeService.add(orderTypeModel);
    }

    @RequestMapping(value = "dicInfo")
    @ResponseBody
    public Result getPricInfo(){
        return utilService.getDicts("PRICE_TYPE");
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public Result update(OrderTypeModel orderTypeModel){
        return orderTypeService.update(orderTypeModel);
    }
}