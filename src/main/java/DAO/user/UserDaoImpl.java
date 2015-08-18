package DAO.user;

import model.user.UserModel;

/**
 * Created by scar on 15/5/14.
 */
public class UserDaoImpl {

    public String info(UserModel userModel) {
        String sql = "select a.*,b.areaName from user_info a,area_info b where a.areaid=b.areaid  and a.status = 1 ";
        if (userModel.getAreaId() != null && userModel.getAreaId().length() > 0) {
            sql += " and a.areaid=#{areaId}";
        }
        if (userModel.getUserId() != null && userModel.getUserId().length() > 0) {
            sql += " and a.userid=#{userId}";
        }
        return sql;
    }

    public String list(UserModel userModel){
        String sql = "SELECT *,b.name as groupName,b.id as groupId FROM user_info a LEFT JOIN competence_group b ON a.groupId = b.id where 1=1 ";
        if (userModel.getUserName() != null && !userModel.getUserName().equals("")){
            sql += " AND a.userName LIKE '%"+userModel.getUserName()+"%' ";
        }
        if (userModel.getGroupName() != null && !userModel.getGroupName().equals("")){
            sql += " AND b.name LIKE '%"+userModel.getGroupName()+"%' ";
        }

        if (userModel.getLimit() > 0) {
            sql += " limit " + (userModel.getStartNum() - 1) * userModel.getLimit() + "," + userModel.getLimit();
        }

        return sql;
    }

    public String listcount(UserModel userModel){
        String sql = "SELECT count(*) FROM user_info a LEFT JOIN competence_group b ON a.groupId = b.id where 1=1 ";
        if (userModel.getUserName() != null && !userModel.getUserName().equals("")){
            sql += " AND a.userName LIKE '%"+userModel.getUserName()+"%' ";
        }
        if (userModel.getGroupName() != null && !userModel.getGroupName().equals("")){
            sql += " AND b.name LIKE '%"+userModel.getGroupName()+"%' ";
        }

        return sql;
    }

}
