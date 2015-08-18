package controller;

import model.Result;
import model.Status;
import model.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by scar on 15/5/5.
 */
@Controller
@RequestMapping("/user")
public class User {

    @Autowired
    private UserService userService;

    @RequestMapping("info")
    @ResponseBody
    public Result info(UserModel userModel) {
        return userService.info(userModel);
    }

    @RequestMapping("list")
    @ResponseBody
    public Result list(UserModel userModel){
        return userService.list(userModel);
    }

    @RequestMapping("updatestatus")
    @ResponseBody
    public Result update(UserModel userModel){
        return userService.updatestatus(userModel);
    }

    @RequestMapping("infoid")
    @ResponseBody
    public Result infoid(UserModel userModel){
        return userService.infoid(userModel);
    }

    @RequestMapping("updateinfo")
    @ResponseBody
    public Result updateinfo(UserModel userModel){
        return userService.updateinfo(userModel);
    }

    @RequestMapping("add")
    @ResponseBody
    public Result add(UserModel userModel){
        return userService.add(userModel);
    }

    @RequestMapping("/login")
    @ResponseBody
    public Result login(UserModel userModel,HttpSession session){

        Result result = userService.login(userModel);
        if (result.getStatus().equals(Status.success)){
            session.setAttribute("user",result.getData());
        }
        return result;
    }

    @RequestMapping("getinfo")
    @ResponseBody
    public Result getinfo(HttpSession session){

        Result result = new Result();
        UserModel userModel = (UserModel) session.getAttribute("user");

        if (userModel != null){
            result.setStatus(Status.success);
            result.setData(userModel);
        }else {
            result.setStatus(Status.noRecord);
        }

        return result;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public Result logout(HttpSession session){

        Result result = new Result();
        try {
            session.invalidate();
            result.setStatus(Status.success);
        }catch (Exception e){
            result.setStatus(Status.error);
        }

        return result;
    }

    @RequestMapping("/unamecheck")
    @ResponseBody
    public Result unamecheck(UserModel userModel){
        return userService.unamecheck(userModel);
    }

    @RequestMapping("/topsw")
    public ModelAndView topsw(){
        return new ModelAndView("user/updatepsw");
    }

    @RequestMapping("/checkpsw")
    @ResponseBody
    public Result checkpsw(UserModel userModel,HttpSession httpSession){
        UserModel user = (UserModel) httpSession.getAttribute("user");
        return userService.checkpsw(userModel,user);
    }

    @RequestMapping("/updatepsw")
    @ResponseBody
    public Result updatepsw(UserModel userModel,HttpSession httpSession){
        UserModel user = (UserModel) httpSession.getAttribute("user");
//        try {
//            userService.updatepsw(userModel,user);
//        }catch (Exception e){
//            System.out.println(e);
//        }
        return userService.updatepsw(userModel,user);
    }


    @RequestMapping("/getAllDeliver")
    @ResponseBody
    public Result getAllDeliver()
    {
        return userService.getAllDelivers();
    }
}
