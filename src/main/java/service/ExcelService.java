package service;

import model.Result;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by zzq on 15-5-22.
 */
public interface ExcelService {
    public Result getExcel(String [] headArr,Map<String,String [] []> map,HttpServletResponse response);
}
