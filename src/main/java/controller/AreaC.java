package controller;

import model.Result;
import model.area.AreaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.AreaService;

/**
 * Created by ZH on 2015/8/14.
 */
@Controller
@RequestMapping("/area")
public class AreaC {
    @Autowired
    private AreaService areaService;

    @RequestMapping("toadd")
    public ModelAndView toadd() {
        return new ModelAndView("base/area/add");
    }

    @RequestMapping("toList")
    public ModelAndView toList() {
        return new ModelAndView("base/area/list");
    }

    @RequestMapping("edit")
    public ModelAndView edit(@RequestParam("areaId") String areaId,@RequestParam("lineId") String lineId,Model model) {
        model.addAttribute("areaId",areaId);
        return new ModelAndView("base/area/add");
    }


    @RequestMapping(value = "addArea")
    @ResponseBody
    public Result addArea(AreaModel areaModel) {
        return areaService.addArea(areaModel);
    }
    @RequestMapping(value = "getAreaList")
    @ResponseBody
    public Result getAreaList(AreaModel areaModel) {
        return areaService.getAreaList(areaModel);
    }

    @RequestMapping(value = "editArea")
    @ResponseBody
    public Result getAreaById(AreaModel areaModel) {
        return areaService.getArea(areaModel);
    }
    @RequestMapping(value = "deleteArea")
    @ResponseBody
    public Result deleteArea(AreaModel areaModel) {
        return areaService.deleteArea(areaModel);
    }
    @RequestMapping(value = "updateArea")
    @ResponseBody
    public Result updateArea(AreaModel areaModel) {
        return areaService.updateArea(areaModel);
    }

    @RequestMapping(value = "getLineList")
    @ResponseBody
    public Result getLineList(AreaModel areaModel) {
        return areaService.getLineList(areaModel);
    }
}
