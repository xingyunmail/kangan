//package controller;
//
//import model.Result;
//import model.area.AreaModel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import service.AreaService;
//
///**
// * Created by zzq on 15-5-7.
// */
//@Controller
//@RequestMapping("/area")
//public class Area {
//
//    @Autowired
//    private AreaService areaService;
//
//    @RequestMapping(value = "getAreaList")
//    @ResponseBody
//    public Result getArea(){
//        return areaService.getAreaList();
//    }
//
//    @RequestMapping(value = "getArea")
//    @ResponseBody
//    public Result getAreaModel(AreaModel areaModel){
//        return areaService.getArea(areaModel);
//    }
//}
