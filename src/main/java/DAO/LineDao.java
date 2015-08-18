package DAO;

import model.area.AreaModel;
import model.carReport.LineModel;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhaijinxu on 15-5-19.
 */
@Repository
public interface LineDao {

    @Select("select * from line_info")
    public List<LineModel> getLineList();

    @Select("select * from line_info where lineId=#{lineId}")
    public LineModel getLineListById(LineModel lineModel);
}
