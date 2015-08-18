package controller;

import model.Result;
import model.activity.ActivityCheckModel;
import model.activity.ActivityModel;
import model.activity.ActivityProdsModel;
import model.product.ProdModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.ActivityService;
import service.ProdService;

/**
 * Created by ZH on 2015/5/6.
 */
@Controller
@RequestMapping("/activity")
public class Activity {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ProdService prodService;

    @RequestMapping("list")
    public ModelAndView getList() {
        return new ModelAndView("activity/list");
    }
    @RequestMapping("add")
    public ModelAndView add() {
        return new ModelAndView("activity/add");
    }
    @RequestMapping("edit")
    public ModelAndView edit(@RequestParam("discountId") String discountId,Model model) {
        model.addAttribute("discountID",discountId);
        return new ModelAndView("activity/add");
    }

    @RequestMapping(value = "getListResult")
    @ResponseBody
    public Result getListResult(ActivityModel activityModel) {
        return activityService.getInfo(activityModel);
    }

    @RequestMapping(value = "editMessage")
    @ResponseBody
    public Result editMessage(ActivityModel activityModel) {
        return activityService.editMessage(activityModel);
    }


    @RequestMapping(value = "update")
    @ResponseBody
    public Result updateModel(ActivityModel activityModel) {
        return activityService.updateModel(activityModel);
    }
    @RequestMapping(value = "findByProdId")
    @ResponseBody
    public Result findActivityModelByProdId(ActivityModel activityModel) {
        return activityService.findActivityModelByProdId(activityModel);
    }

    @RequestMapping(value = "addActivity")
    @ResponseBody
    public Result addActivityModel(ActivityModel activityModel) {
        return activityService.addActivityModel(activityModel);
    }
    @RequestMapping(value = "getProductMessage")
    @ResponseBody
    public Result getProductMessage(ProdModel prodModel) {
        return prodService.getProductMessage(prodModel);
    }

    @RequestMapping(value = "deleteProdDiscount")
    @ResponseBody
    public Result deleteProdDiscount(ActivityProdsModel activityProdsModel) {
        return activityService.deleteProdDiscount(activityProdsModel);
    }

    @RequestMapping(value = "selectByDiscountId")
    @ResponseBody
    public Result selectByDiscountId(ActivityModel activityModel) {
        return activityService.selectByDiscountId(activityModel);
    }

    @RequestMapping(value = "activityCheck")
    @ResponseBody
    public Result activityCheck(@RequestBody ActivityCheckModel checkModel){
        return activityService.activityCheck(checkModel);
    }
}
