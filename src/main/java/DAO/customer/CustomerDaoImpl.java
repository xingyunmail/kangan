package DAO.customer;

import model.customer.CustomerModel;
import org.springframework.util.StringUtils;

/**
 * Created by ZH on 2015/8/19.
 */
public class CustomerDaoImpl {
    public String getCustomerList(CustomerModel customerModel){
        String sql = "select * from customer_info where 1=1 ";

        if(!StringUtils.isEmpty(customerModel.getCustId())){
            sql += " and custId = "+customerModel.getCustId();
        }
        if (customerModel.getLimit() > 0) {
            sql += " limit " + (customerModel.getStartNum() - 1) * customerModel.getLimit() + "," + customerModel.getLimit();
        }

        return sql;
    }
    public String getCustomerCount(CustomerModel customerModel){
        String sql = "select count(*) from customer_info where 1=1 ";

        if(!StringUtils.isEmpty(customerModel.getCustId())){
            sql += " and custId = "+customerModel.getCustId();
        }

        return sql;
    }
}
