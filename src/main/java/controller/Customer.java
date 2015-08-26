package controller;

import model.Result;
import model.customer.CustomerModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.CustomerService;

/**
 * Created by scar on 15/5/4.
 */
@Controller
@RequestMapping("/customer")
public class Customer {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("toadd")
    public ModelAndView toadd() {
        return new ModelAndView("base/customer/add");
    }

    @RequestMapping("toList")
    public ModelAndView toList() {
        return new ModelAndView("base/customer/list");
    }

    @RequestMapping("edit")
    public ModelAndView edit(@RequestParam("custId") String custId,Model model) {
        model.addAttribute("custId",custId);
        return new ModelAndView("base/customer/add");
    }


    @RequestMapping("info")
    @ResponseBody
    public Result getInfo(CustomerModel customerModel) {
        return customerService.getInfo(customerModel);
    }
    @RequestMapping("getCustomerList")
    @ResponseBody
    public Result getCustomerList(CustomerModel customerModel) {
        return customerService.getCustomerList(customerModel);
    }
    @RequestMapping("deleteCustomer")
    @ResponseBody
    public Result deleteCustomer(@Param("custId")String custId) {
        return customerService.deleteCustomer(custId);
    }
    @RequestMapping("insertCustomer")
    @ResponseBody
    public Result insertCustomer(CustomerModel customerModel) {
        return customerService.insertCustomer(customerModel);
    }
    @RequestMapping("updateCustomer")
    @ResponseBody
    public Result UpdateCustomer(CustomerModel customerModel) {
        return customerService.UpdateCustomer(customerModel);
    }


}
