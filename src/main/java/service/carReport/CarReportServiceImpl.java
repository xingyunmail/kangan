package service.carReport;

import DAO.CarReportDao;
import DAO.LineDao;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import controller.CarReport;
import model.Result;
import model.Status;
import model.carReport.CarReportModel;
import model.carReport.LineModel;
import model.total.TotalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.CarReportService;
import service.ExcelService;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhaijinxu on 15-5-19.
 */
@Service
public class CarReportServiceImpl implements CarReportService {

    @Autowired
    private CarReportDao carReportDao;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private LineDao lineDao ;

    @Override
    public Result getAllData(CarReportModel carReportModel) {
        Result result = new Result();
        List<CarReportModel> list = carReportDao.getDate(carReportModel);
        if (list.size() > 0) {
            result.setStatus(Status.success);
            result.setData(list);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result getExcel(CarReportModel carReportModel, HttpServletResponse response) {
        Result result = new Result();
        List<CarReportModel> list = carReportDao.getDate(carReportModel);

        if (list.size() > 0) {
            String[] areaName = new String[list.size()];
            for (int i = 0; i < areaName.length; i++) {
                areaName[i] = list.get(i).getAreaName();
            }
            String headArrs[] = repeat(areaName);
            String headArr[] = new String[headArrs.length+3];
            headArr[0]="品种";
            headArr[headArr.length-2]="合计";
            headArr[headArr.length-1]="漏袋";
            for(int i = 1 ;i<headArr.length-2 ;i++){
                headArr[i] = headArrs[i-1];
            }
            String[] prodNames = new String[list.size()];
            for (int i = 0; i < prodNames.length; i++) {
                prodNames[i] = list.get(i).getProdName();
            }
            String prodNamer[] = repeat(prodNames);
            String prodName[] = new String[prodNamer.length+1];
            prodName[prodName.length-1]="合计";
            for (int i = 0; i <prodName.length-1 ; i++) {
                prodName[i]=prodNamer[i];
            }
            String[][] arr = new String[prodName.length][headArr.length];
            for (int j = 0; j < prodName.length; j++) {
                int total = 0 ;
                for (int k = 0; k < headArr.length; k++) {
                    for(int i = 0 ; i<list.size() ; i++){
                        if(list.get(i).getProdName().equals(prodName[j])&&list.get(i).getAreaName().equals(headArr[k])){
                            arr[j][k]=list.get(i).getTotal()+"";
                            total+=list.get(i).getTotal();
                            break;
                        }else {
                            if(k==0){
                                arr[j][k] = prodName[j];
                            }else if(k==headArr.length-2){
                                arr[j][headArr.length-2]=total+"";
                            }else if(k==headArr.length-1){
                                arr[j][headArr.length-1]= Math.round(Integer.parseInt(arr[j][headArr.length-2])*0.002)+"";
                            }else {
                                arr[j][k]=0+"";
                            }

                        }
                    }
                }
            }
            for (int k = 1; k < headArr.length; k++) {
                int total = 0 ;
                for (int j = 0; j < prodName.length; j++) {
                    total+=Integer.parseInt(arr[j][k]);
                    if(j==prodName.length-1){
                        arr[prodName.length-1][k]=total+"";
                        break;
                    }
                }
            }

            Map<String, String[][]> map = new HashMap<String, String[][]>();
            LineModel lineModels = new LineModel() ;
            lineModels.setLineId(carReportModel.getLineid());
            LineModel lineModel = lineDao.getLineListById(lineModels) ;
            map.put(lineModel.getLineName(),arr);
            excelService.getExcel(headArr, map, response);
            result.setStatus(Status.success);

        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }


    public String[] repeat(String[] arr) {

        List<String> list = new java.util.ArrayList<String>();
        for (int i = 0; i < arr.length; i++) {
            if (!list.contains(arr[i])) {//如果list数组不包括num[i]中的值的话，就返回true。
                list.add(arr[i]); //在list数组中加入num[i]的值。已经过滤过。
            }
        }
        return list.toArray(new String[0]);
    }
}
