package controller;

import model.Result;
import model.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.MenuService;

import javax.servlet.http.HttpSession;

/**
 * Created by scar on 15/4/29.
 */
@Controller
@RequestMapping("/menu")
public class Menu {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "list")
    @ResponseBody
    public Result getMenus(HttpSession session) {
        UserModel userModel = (UserModel) session.getAttribute("user");
        return menuService.getMenus(userModel);
    }
}
