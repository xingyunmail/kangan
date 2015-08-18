package controller;

import model.Result;
import model.Status;
import model.competence.ButtonInfoModel;
import model.competence.CompetenceGroupModel;
import model.competence.CompetenceGtoinfoModel;
import model.competence.CompetenceInfoModel;
import model.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.CompetenceService;

import javax.servlet.http.HttpSession;

/**
 * Created by yu on 15-5-18.
 */
@Controller
@RequestMapping("/competence")
public class Competence {

    @Autowired
    private CompetenceService competenceService;


    @RequestMapping("/user")
    public ModelAndView userList() {
        return new ModelAndView("competence/user");
    }

    @RequestMapping("/grouplist")
    @ResponseBody
    public Result groupList(){
        return competenceService.groupList();
    }

    @RequestMapping("/group")
    public ModelAndView group(){
        return new ModelAndView("competence/grouplist");
    }

    @RequestMapping("/infolist")
    @ResponseBody
    public Result infolist(){
        return competenceService.infolist();
    }

    @RequestMapping("/addgroup")
    @ResponseBody
    public Result addGroup(CompetenceGtoinfoModel cGtoinfoModel){

        Result result = new Result();

        try {
            result = competenceService.addGroup(cGtoinfoModel);
        }catch (Exception e){
            result.setStatus(Status.error);
        }
        return result;
    }

    @RequestMapping("/addinfo")
    public ModelAndView addinfo(){
        return new ModelAndView("competence/addinfo");
    }

    //获取权限组,用户组,权限
    @RequestMapping("/groupinfolist")
    @ResponseBody
    public Result groupinfolist(CompetenceGroupModel competenceGroupModel){
        return competenceService.groupinfolist(competenceGroupModel);
    }

    //获取权限,权限组
    @RequestMapping("/infogrouplist")
    @ResponseBody
    public Result infogrouplist(CompetenceInfoModel competenceInfoModel){
        return competenceService.infogrouplist(competenceInfoModel);
    }

    //添加权限
    @RequestMapping("/addcompetence")
    @ResponseBody
    public Result addcompetence(CompetenceInfoModel competenceInfoModel){
        return competenceService.addcompetence(competenceInfoModel);
    }

    //根据competenceId获取权限名称
    @RequestMapping("/button")
    @ResponseBody
    public Result getbutton(ButtonInfoModel buttonInfoModel){
        return competenceService.getbutton(buttonInfoModel);
    }

    //获取按钮权限列表
    @RequestMapping("/buttonlist")
    @ResponseBody
    public Result buttonlist(HttpSession session){
        UserModel userModel = (UserModel) session.getAttribute("user");
        int groupId = 0;
        if (userModel != null){
            groupId = userModel.getGroupId();
        }
        return competenceService.buttonlist(groupId);
    }

    //更新权限组所含权限
    @RequestMapping("/updategroup")
    @ResponseBody
    public Result updategroup(CompetenceGtoinfoModel competenceGtoinfoModel){
        return competenceService.updategroup(competenceGtoinfoModel);
    }

    //test
    @RequestMapping("getinfobyid")
    @ResponseBody
    public Result getinfobyid(CompetenceGtoinfoModel competenceGtoinfoModel){
        return competenceService.getinfobyid(competenceGtoinfoModel);
    }

}
