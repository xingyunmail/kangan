package DAO;

import DAO.Area.AreaImpl;
import model.area.AreaModel;
import model.carReport.LineModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaDao {
    @SelectProvider(type = AreaImpl.class, method = "getAreaList")
    public List<AreaModel> getAreaList(AreaModel areaModel);
    @SelectProvider(type = AreaImpl.class, method = "getAreaListCount")
    public int getAreaListCount(AreaModel areaModel);

    @Select("select * from area_info where areaId=#{areaId}")
    public AreaModel getArea(AreaModel areaModel);

    @Insert("insert into area_info (areaId,areaName,discrible,lineid) values(#{areaId},#{areaName},#{discrible},#{lineid})")
    public int addArea(AreaModel areaModel);

    @Update("update area_info set areaName=#{areaName},discrible=#{discrible},lineid=#{lineid} where areaId=#{areaId}")
    public int updateArea(AreaModel areaModel);

    @Delete("delete from area_info where areaId=#{areaId}")
    public int deleteArea(AreaModel areaModel);

    @Select("select lineId,lineName from line_info")
    public List<LineModel> getLineList(AreaModel areaModel);

    @Select("select areaId from area_info where lineid =#{lineid} order by areaId desc  limit 1")
    public int getLastAreaId(AreaModel areaModel);
}
