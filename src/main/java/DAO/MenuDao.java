package DAO;


import model.menu.MenuModel;
import model.user.UserModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by scar on 15/4/29.
 */
@Repository
public interface MenuDao {

    @Select("select * from menu_info where valid = 1")
    public List<MenuModel> getMenus();

    @Select("SELECT * from menu_info WHERE valid = 1 and id IN (SELECT c.menuId from competence_group a,competence_gtoinfo b,competence_info c WHERE a.id = b.groupId AND b.infoId = c.id AND a.id = #{groupId} GROUP BY c.menuId)")
    public List<MenuModel> getMenusTwo(UserModel userModel);

    @Select("SELECT * from menu_info WHERE id IN (SELECT parentId from menu_info WHERE valid = 1 and id IN (SELECT c.menuId from competence_group a,competence_gtoinfo b,competence_info c WHERE a.id = b.groupId AND b.infoId = c.id AND a.id = #{groupId} GROUP BY c.menuId) GROUP BY parentId)")
    public List<MenuModel> getMenusOne(UserModel userModel);
}
