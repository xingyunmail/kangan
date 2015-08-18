package DAO;

import DAO.user.UserDaoImpl;
import model.user.UserModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by scar on 15/5/6.
 */
@Repository
public interface UserDao {

    @SelectProvider(type = UserDaoImpl.class, method = "info")
    List<UserModel> info(UserModel userModel);

    @SelectProvider(type = UserDaoImpl.class, method = "list")
    public List<UserModel> list(UserModel userModel);

    @SelectProvider(type = UserDaoImpl.class, method = "listcount")
    public int listcount(UserModel userModel);

    @Update("update user_info set status = #{status} where userId = #{userId}")
    public int updatestatus(UserModel userModel);

    @Select("SELECT *,b.name as groupName,b.id as groupId FROM user_info a LEFT JOIN competence_group b ON a.groupId = b.id WHERE userId = #{userId}")
    public UserModel infoid(UserModel userModel);

    @Update("UPDATE user_info set userName = #{userName},password = #{password},groupId = #{groupId} WHERE userId = #{userId}")
    public int updateinfo(UserModel userModel);

    @Insert("insert into user_info(userId,userName,password,groupId) values(#{userId},#{userName},#{password},#{groupId})")
    public int add(UserModel userModel);

    @Select("select * from user_info where userId = #{userId} and password = #{password} and status = 1")
    public UserModel login(UserModel userModel);

    @Select("select CONCAT(user_info.userName,'-',user_info.userId) as userName from user_info where status = 1")
    public List<UserModel> userCheck(UserModel userModel);

    @Update("update user_info set password = #{password} where userId = #{userId}")
    public int updatepsw(@Param("password") String password, @Param("userId") String userId);

    /**
     * 用于页面提示
     * create by sunhao 2015-07-20
     *     @Select("select CONCAT(user_info.userName,'-',user_info.userId) as userName from user_info where status = 1 and (userName like concat('%',#{query},'%') or userId like concat('%',#{query},'%') )")
     *     public List<UserModel> getAllDelivers(@Param("query") String query);
     * @return
     */
    @Select("select CONCAT(user_info.userName,'-',user_info.userId) as userName from user_info where status = 1")
    public List<UserModel> getAllDelivers();
}

