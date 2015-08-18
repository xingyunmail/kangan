package DAO.competence;

import model.competence.ButtonInfoModel;
import model.competence.CompetenceGroupModel;
import model.competence.CompetenceInfoModel;

/**
 * Created by yu on 15-5-25.
 */
public class CompetenceDaoImpl {

    public String groupinfolist(CompetenceGroupModel competenceGroupModel){
        String sql = "select * from competence_group where valid = 1 ";
        if (competenceGroupModel.getLimit() > 0) {
            sql += " limit " + (competenceGroupModel.getStartNum() - 1) * competenceGroupModel.getLimit() + "," + competenceGroupModel.getLimit();
        }
        return sql;
    }

    public String buttonlist(ButtonInfoModel buttonInfoModel){
        String sql = "select * from button_info where valid = 1 and competenceId like '%"+ buttonInfoModel.getCompetenceId() +"%'";
        return sql;
    }

    public String groupinfolistcount(){
        String sql = "select count(*) from competence_group where valid = 1";

        return sql;
    }

    public String infogrouplist(CompetenceInfoModel competenceInfoModel){
        String sql = "SELECT *,GROUP_CONCAT(b.name) as groupName from competence_info a,competence_group b,competence_gtoinfo c WHERE a.id = c.infoId AND b.id = c.groupId AND a.valid = 1 AND b.valid = 1 AND c.valid = 1 GROUP BY a.id ";

        if (competenceInfoModel.getLimit() > 0) {
            sql += " limit " + (competenceInfoModel.getStartNum() - 1) * competenceInfoModel.getLimit() + "," + competenceInfoModel.getLimit();
        }

        return sql;
    }

    public String infogrouplistcount(){

        String sql = "SELECT count(*) from competence_info WHERE valid = 1; ";
        return sql;
    }
}
