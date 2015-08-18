package service;

import model.Result;
import model.carReport.CarReportModel;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhaijinxu on 15-5-19.
 */
public interface CarReportService {

    public Result getAllData(CarReportModel carReportModel);

    public Result getExcel(CarReportModel carReportModel, HttpServletResponse response);

}
