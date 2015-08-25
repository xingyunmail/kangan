package DAO;

import DAO.customer.CustomerDaoImpl;
import model.customer.CustomerModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by scar on 15/5/4.
 */
@Repository
public interface CustomerDao {

    @Select("select * from customer_info where custId=#{custId}")
    public List<CustomerModel> getInfo(CustomerModel customerModel);

    @Insert("insert into customer_info(custId,custName,phone,address,status) values(#{custId},#{custName},#{phone},#{address},#{status})")
    public int insertCustomer(CustomerModel customerModel);

    @Select("SELECT ifnull( MAX( lpad((SUBSTRING(a.custId, 7, 10) + 1), 4, 0 )), '0001' ) newId FROM customer_info a WHERE a.custId LIKE '${userId}____'")

    public String getNewCustomerId(@Param("userId") String userId);


    @Delete("delete from customer_info where custId=#{custId}")
    public String deleteCustomer(@Param("custId") String custId);

    @Update("update customer_info set custName=#{custName},phone=#{phone},address=#{address},status=#{status} where custId=#{custId}")
    public String UpdateCustomer(CustomerModel customerModel);

    @SelectProvider(type=CustomerDaoImpl.class,method="getCustomerList")
    public List<CustomerModel> getCustomerList(CustomerModel customerModel);

    @SelectProvider(type=CustomerDaoImpl.class,method="getCustomerCount")
    public List<CustomerModel> getCustomerCount(CustomerModel customerModel);

}
