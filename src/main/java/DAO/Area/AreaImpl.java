package DAO.Area;


import model.area.AreaModel;
import org.springframework.util.StringUtils;

/**
 * Created by ZH on 2015/8/18.
 */
public class AreaImpl {
    public String getAreaList(AreaModel areaModel) {
        String sql = " select a.*,l.lineName lineName from area_info a  left join line_info l on a.lineid=l.lineId where 1=1 ";

        if(!StringUtils.isEmpty(areaModel.getAreaName())){
            sql += " and a.areaName like '%"+areaModel.getAreaName()+"%'";
        }
        if(!StringUtils.isEmpty(areaModel.getLineid())){
            sql += " and l.lineId = "+areaModel.getLineid();
        }
        if (areaModel.getLimit() > 0) {
            sql += " limit " + (areaModel.getStartNum() - 1) * areaModel.getLimit() + "," + areaModel.getLimit();
        }
        return sql;
    }
    public String getAreaListCount(AreaModel areaModel) {
        String sql = " select count(*) from area_info a left join line_info l on a.lineid=l.lineId  where 1=1";

        if(!StringUtils.isEmpty(areaModel.getAreaName())){
            sql += " and a.areaName like '%"+areaModel.getAreaName()+"%'";
        }
        if(!StringUtils.isEmpty(areaModel.getLineid())){
            sql += " and l.lineId = "+areaModel.getLineid();
        }
        return sql;
    }
}
