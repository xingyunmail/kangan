package service.customer;

import DAO.CustomerDao;
import model.Result;
import model.Status;
import model.customer.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.CustomerService;

import java.util.List;

/**
 * Created by scar on 15/5/4.
 */
@Service
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private CustomerDao customerDao;


    @Override
    public Result getInfo(CustomerModel customerModel) {
        Result result = new Result();

        List<CustomerModel> customerModelList = customerDao.getInfo(customerModel);
        if (customerModelList.size() == 1) {
            result.setStatus(Status.success);
            result.setData(customerModelList.get(0));
        } else {
            result.setStatus(Status.error);
        }
        return result;
    }

    @Override
    public Result getCustomerList(CustomerModel customerModel) {
        Result result = new Result();

        List<CustomerModel> customerModelList = customerDao.getCustomerList(customerModel);
        if (customerModelList.size() > 0) {
            result.setStatus(Status.success);
            result.setData(customerModelList);
        } else {
            result.setStatus(Status.error);
        }
        return result;
    }

    @Override
    public Result deleteCustomer(String custId) {
        Result result = new Result();
        String isuc = customerDao.deleteCustomer(custId);
            result.setStatus(Status.success);
            result.setData(isuc);
           return result;
    }

    @Override
    public Result UpdateCustomer(CustomerModel customerModel) {
        Result result = new Result();
        String isuc = customerDao.UpdateCustomer(customerModel);
        result.setStatus(Status.success);
        result.setData(isuc);
        return result;
    }

    @Override
    public Result insertCustomer(CustomerModel customerModel) {
        Result result = new Result();
        int isuc = customerDao.insertCustomer(customerModel);
        result.setStatus(Status.success);
        result.setData(isuc);
        return result;
    }

}
