package controller;

import model.Result;
import model.activity.ActivityModel;
import model.price.PriceModel;
import model.product.ProdModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.PriceService;
import service.ProdService;

/**
 * Created by ZH on 2015/5/15.
 */
@Controller
@RequestMapping("/special")
public class Special {
    @Autowired
    private PriceService priceService;
    @Autowired
    private ProdService prodService;

    @RequestMapping("list")
    public ModelAndView getList() {
        return new ModelAndView("special/list");
    }

    @RequestMapping("getList")
    @ResponseBody
    public Result getInfo(PriceModel priceModel) {
        return priceService.getList(priceModel);
    }
    @RequestMapping("getprodPriceList")
    @ResponseBody
    public Result getprodPriceList(ProdModel prodModel) {
        return prodService.getProdPriceInfo(prodModel);
    }

    @RequestMapping("batchInsert")
    @ResponseBody
    public Result batchInsert(@RequestBody PriceModel priceModel) {
        return priceService.saveList(priceModel);
    }


}
