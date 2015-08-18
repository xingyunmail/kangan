package controller;

import model.Result;
import model.customer.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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



}
