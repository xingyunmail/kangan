package service;

import model.Result;
import model.customer.CustomerModel;

/**
 * Created by scar on 15/5/4.
 */
public interface CustomerService {

    Result getInfo(CustomerModel customerModel);
    Result getCustomerList(CustomerModel customerModel);
    Result deleteCustomer(String custId);
    Result UpdateCustomer(CustomerModel customerModel);
    Result insertCustomer(CustomerModel customerModel);

}
