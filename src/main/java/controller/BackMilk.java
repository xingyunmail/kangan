package controller;

import model.Result;
import model.finance.BackMilkModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.BackMilkService;

/**
 * Created by zhaijinxu on 15-5-22.
 */
@Controller
@RequestMapping("backMilk")
public class BackMilk {

    @Autowired
    private BackMilkService backMilkService ;

    @RequestMapping("back")
    public ModelAndView getList(){
        return new ModelAndView("finance/backMilk");
    }

    @RequestMapping(value="getDate")
    @ResponseBody
    public Result getDate(BackMilkModel backMilkModel){
        System.out.println("开始时间controller======================"+backMilkModel.getStartDate());
        System.out.println("结束时间======================"+backMilkModel.getEndDate());
        return backMilkService.getDate(backMilkModel);
    }
}
