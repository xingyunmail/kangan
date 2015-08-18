package controller;

import model.Result;
import model.carReport.CarReportModel;
import model.total.TotalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.CarReportService;
import service.LineService;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhaijinxu on 15-5-19.
 */
@Controller
@RequestMapping("/carReport")
public class CarReport {

    @Autowired
    private CarReportService carReportService ;

    @Autowired
    private LineService lineService ;

    @RequestMapping("toList")
    public ModelAndView getList(){
        return new ModelAndView("carReport/list");
    }

    @RequestMapping(value="getLineList")
    @ResponseBody
    public Result getLineList(){
        return lineService.getLineList();
    }

    @RequestMapping(value="getAllDate")
    @ResponseBody
    public Result getAllData(CarReportModel carReportModel){
        return carReportService.getAllData(carReportModel);
    }

    @RequestMapping(value="getExcel")
    @ResponseBody
    public Result getExcel(CarReportModel carReportModel,HttpServletResponse response){
        return carReportService.getExcel(carReportModel,response);
    }
}
