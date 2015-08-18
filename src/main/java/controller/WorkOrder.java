package controller;

import model.Result;
import model.order.OrderModel;
import model.workorder.WorkOrderModel;
import model.workorder.WorkOrdersModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.OrderService;
import service.UtilService;
import service.WorkOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zzq on 15-5-7.
 */
@Controller
@RequestMapping("/work_order")
public class WorkOrder {

    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private UtilService utilService;

    @RequestMapping("add")
    public ModelAndView add(){
        return new ModelAndView("workorder/add");
    }

    @RequestMapping("complaints")
    public ModelAndView complaintsList(){
        return new ModelAndView("workorder/complaintsList");
    }

    @RequestMapping("purchase")
    public ModelAndView purchaseList(){
        return new ModelAndView("workorder/purchaseList");
    }


    @RequestMapping(value = "statusDict" )
    @ResponseBody
    public Result getStatus(){
        return utilService.getDicts("WORK_TYPE");
    }

    @RequestMapping(value = "addWork" ,method = RequestMethod.POST)
    @ResponseBody
    public Result add(WorkOrderModel workOrderModel){
        Result result = workOrderService.add(workOrderModel);
        return result;
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    public Result edit(WorkOrderModel workOrderModel){
        return workOrderService.update(workOrderModel);
    }

    @RequestMapping(value = "getList")
    @ResponseBody
    public Result getList(WorkOrdersModel workOrdersModel){
       return workOrderService.getList(workOrdersModel);
    }

    @RequestMapping(value = "getExcel")
    @ResponseBody
    public Result getExcel(WorkOrdersModel workOrdersModel,HttpServletResponse response){
        return workOrderService.getExcel(workOrdersModel,response);
    }


}
