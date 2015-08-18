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
}
