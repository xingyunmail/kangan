package controller;

import model.user.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by scar on 15/4/29.
 */
@Controller
@RequestMapping("/")
public class Main {

    @RequestMapping(value = "login")
    public ModelAndView toLogin() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/")
    public ModelAndView main(HttpSession session) {
        UserModel userModel = (UserModel) session.getAttribute("user");
        if (userModel != null){
            return new ModelAndView("main");
        }else {
            return new ModelAndView("login");
        }
    }
}
