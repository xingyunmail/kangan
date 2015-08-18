package controller;

import model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ComplaintsService;

/**
 * Created by zzq on 15-5-8.
 */
@Controller
@RequestMapping("/complaints")
public class Complaints {
    @Autowired
    private ComplaintsService complaintsService;

    @RequestMapping(value = "getCompType")
    @ResponseBody
    public Result getCompList(){
        return complaintsService.getCompList();
    }

    @RequestMapping(value = "getCompType/{type}")
    @ResponseBody
    public Result getCompList(@PathVariable int type){
        return complaintsService.getCompById(type);
    }


}
