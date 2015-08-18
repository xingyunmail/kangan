package DAO;

import DAO.carReport.CarReportDaoImpl;
import model.carReport.CarReportModel;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhaijinxu on 15-5-19.
 */
@Repository
public interface CarReportDao {

    @SelectProvider(type = CarReportDaoImpl.class, method = "getData")
    public List<CarReportModel> getDate(CarReportModel carReportModel);


}
