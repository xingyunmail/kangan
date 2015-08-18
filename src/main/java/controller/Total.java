package controller;

import model.Result;
import model.total.TotalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.TotalService;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhaijinxu on 15-5-14.
 */
@Controller
@RequestMapping("/total")
public class Total {

    @Autowired
    private TotalService totalService ;

    @RequestMapping("toList")
    public ModelAndView getList(Total totalModel){
        return new ModelAndView("total/list");
    }
    @RequestMapping(value="list")
    @ResponseBody
    public Result getData(TotalModel totalModel){
        return totalService.getData(totalModel);
    }

    @RequestMapping(value = "getExcel")
    @ResponseBody
    public Result getExcel(TotalModel totalModel,HttpServletResponse response){
        return totalService.getExcel(totalModel,response);
    }

}
