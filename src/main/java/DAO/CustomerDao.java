package DAO;

import model.customer.CustomerModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

}
