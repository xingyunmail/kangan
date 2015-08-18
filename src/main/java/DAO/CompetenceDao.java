package DAO;

import DAO.competence.CompetenceDaoImpl;
import model.competence.ButtonInfoModel;
import model.competence.CompetenceGroupModel;
import model.competence.CompetenceGtoinfoModel;
import model.competence.CompetenceInfoModel;
import model.user.UserModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yu on 15-5-21.
 */
@Repository
public interface CompetenceDao {

    @Select("select * from competence_group where valid = 1")
    public List<CompetenceGroupModel> groupList();

    @Select("select * from competence_info where valid = 1")
    public List<CompetenceInfoModel> infolist();

    @Insert("insert into competence_group(name) values(#{name})")
    @SelectKey(statement = "select LAST_INSERT_ID() as id",keyProperty="id",resultType = Integer.class, before = false)
    public int addGroup(CompetenceGroupModel competenceGroupModel);

    @Insert("insert into competence_gtoinfo(groupId,infoId) values(#{groupId},#{infoId})")
    public int groupToInfo(CompetenceGtoinfoModel competenceGtoinfoModel);

    @SelectProvider(type = CompetenceDaoImpl.class, method = "groupinfolist")
    @Results(value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "competenceInfoModels",column = "id",javaType = List.class,many = @Many(select = "getinfolist")),
            @Result(property = "userModels",column = "id",javaType = List.class,many = @Many(select = "getuserlist"))
    })
    public List<CompetenceGroupModel> groupinfolist(CompetenceGroupModel competenceGroupModel);

    @Select("SELECT * from competence_info a, competence_gtoinfo b where a.valid = 1 and b.valid = 1 and a.id = b.infoId AND b.groupId = #{id}")
    public List<CompetenceInfoModel> getinfolist(@Param("id") int id);

    @Select("select * from user_info where groupId = #{id}")
    public List<UserModel> getuserlist(@Param("id") int id);

    @SelectProvider(type = CompetenceDaoImpl.class, method = "groupinfolistcount")
    public int groupinfolistcount();

    @SelectProvider(type = CompetenceDaoImpl.class, method = "infogrouplist")
    public List<CompetenceInfoModel> infogrouplist(CompetenceInfoModel competenceInfoModel);

    @SelectProvider(type = CompetenceDaoImpl.class, method = "infogrouplistcount")
    public int infogrouplistcount();

    @Insert("insert into competence_info(competenceId,name,menuId) values(#{competenceId},#{name},#{menuId})")
    @SelectKey(statement = "select LAST_INSERT_ID() as id",keyProperty="id",resultType = Integer.class, before = false)
    public int addInfo(CompetenceInfoModel competenceInfoModel);

    @SelectProvider(type = CompetenceDaoImpl.class, method = "buttonlist")
    public List<ButtonInfoModel> getbutton(ButtonInfoModel buttonInfoModel);

    @Select("select * from button_info where id = #{buttonId}")
    public ButtonInfoModel buttoninfo(CompetenceInfoModel competenceInfoModel);

    @Select("SELECT c.id as id,c.competenceId as competenceId from competence_group a,competence_gtoinfo b,competence_info c WHERE a.valid = 1 AND b.valid = 1 AND c.valid = 1 AND a.id = b.groupId AND b.infoId = c.id AND a.id = #{groupId}")
    public List<CompetenceInfoModel> buttonlist(@Param("groupId") int groupId);

    @Delete("update competence_gtoinfo set valid = 0 where groupId = #{groupId}")
    public int deleteinfo(@Param("groupId") int groupId);

    @Select("select * from competence_gtoinfo where groupId = #{groupId} and valid = 1")
    public List<CompetenceGtoinfoModel> getinfobyid(CompetenceGtoinfoModel competenceGtoinfoModel);

}
