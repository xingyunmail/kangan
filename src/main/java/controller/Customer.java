package controller;

import model.Result;
import model.customer.CustomerModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.CustomerService;

/**
 * Created by scar on 15/5/4.
 */
@Controller
@RequestMapping("/customer")
public class Customer {

    @Autowired
    private CustomerService customerService;

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
    @RequestMapping("UpdateCustomer")
    @ResponseBody
    public Result UpdateCustomer(CustomerModel customerModel) {
        return customerService.UpdateCustomer(customerModel);
    }


}
