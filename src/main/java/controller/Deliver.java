package controller;

import model.Result;
import model.deliver.DeliverOrderModel;
import model.deliver.ReceiveMilkOrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.DeliverService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * Created by lining on 15/4/29.
 * 收奶单、配送单controller
 */
@Controller
@RequestMapping("/deliver")
public class Deliver {
    @Autowired
    DeliverService deliverService;

    @RequestMapping("receiveMilkOrder")
    public ModelAndView getMilkOrder() {
        return new ModelAndView("deliver/receiveMilkOrder");
    }

    @RequestMapping("deliverOrder")
    public ModelAndView distributeOrder() {
        return new ModelAndView("deliver/deliverOrder");
    }


    @RequestMapping("getMilkOrderList")
    @ResponseBody
    public Result getMilkOrderList(ReceiveMilkOrderModel receiveMilkOrderModel){
         Result result = deliverService.getMilkOrderList(receiveMilkOrderModel);
         return result;
    }
    @ResponseBody
    @RequestMapping("getDeliverOrder")
    public Result getDeliverOrder(DeliverOrderModel deliverOrderModel){
        Result result = deliverService.getDeliverOrder(deliverOrderModel);
        return result;
    }

    @ResponseBody
    @RequestMapping("getDeliverOrderTest")
    public Result getDeliverOrderTest( @RequestBody DeliverOrderModel deliverOrderModel){
        Result result = deliverService.getDeliverOrder(deliverOrderModel);
        return result;
    }


    @ResponseBody
    @RequestMapping("getChangeOrder")
    public Result getChangeOrder(DeliverOrderModel deliverOrderModel){
        Result result = deliverService.getChangeOrder(deliverOrderModel);
        return result;
    }

    @ResponseBody
    @RequestMapping("getChangeOrderTest")
    public Result getChangeOrderTest(@RequestBody DeliverOrderModel deliverOrderModel){
        Result result = deliverService.getChangeOrder(deliverOrderModel);
        return result;
    }
    @ResponseBody
    @RequestMapping("getTasteMilk")
    public Result getTasteMilk(ReceiveMilkOrderModel receiveMilkOrderModel){
        Result result = deliverService.getTasteMilk(receiveMilkOrderModel);
        return result;
    }
    @ResponseBody
    @RequestMapping("exportReceive")
    public Result exportReceive(ReceiveMilkOrderModel receiveMilkOrderModel,HttpServletResponse response){
        Result result = new Result();
        result = deliverService.exportReceive(receiveMilkOrderModel,response);
        return result;
    }

    @ResponseBody
    @RequestMapping("exportChange")
    public Result exportChange(DeliverOrderModel deliverOrderModel,HttpServletResponse response){
        Result result = deliverService.exportChange(deliverOrderModel,response);
        return result;
    }

    @ResponseBody
    @RequestMapping("exportDeliver")
    public Result exportDeliver(DeliverOrderModel deliverOrderModel,HttpServletResponse response){
        Result result = deliverService.exportDeliver(deliverOrderModel,response);
        return result;
    }

    @ResponseBody
    @RequestMapping("numberCount")
    public Result numberCount(DeliverOrderModel deliverOrderModel){
        Result result = deliverService.numberCount(deliverOrderModel);
        return result;
    }

}
