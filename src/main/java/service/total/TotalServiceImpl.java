package service.total;

import DAO.TotalDao;
import model.Result;
import model.Status;
import model.total.TotalModel;
import model.workorder.WorkOrderModel;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ExcelService;
import service.TotalService;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhaijinxu on 15-5-14.
 */
@Service
public class TotalServiceImpl implements TotalService {

    @Autowired
    private TotalDao totalDao;

    @Autowired
    private ExcelService excelService;

    @Override
    public Result getData(TotalModel totalMOdel) {
        Result result = new Result();
        List<TotalModel> totalModelList = totalDao.getData(totalMOdel);
        if (totalModelList.size() > 0) {
            result.setData(totalModelList);
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result ;
    }

    @Override
    public Result getExcel(TotalModel totalModel, HttpServletResponse response) {
        Result result = new Result();
        List<TotalModel> list = totalDao.getData(totalModel);
        String [] headArr = new String[2];
        headArr[0]="名称";
        headArr[1]="数量";
        if(list.size()>0){
            String [] [] arr = new String[list.size()][2];
            int j = 0;
            for(int i=0;i<list.size();i++){
                if(list.get(i).getTotal()==0){
                    j++;
                    continue;
                }
                arr[i-j][0] = list.get(i).getProdName();
                arr[i-j][1] = list.get(i).getTotal()+"";
                System.out.println("arr[i - j][0]======"+arr[i - j][0]);
                System.out.println("arr[i - j][1]======"+arr[i - j][1]);
            }
            Map<String,String [] []> map = new HashMap<String, String[][]>();
            System.out.println(map.toString());
            map.put("sheet1",arr);
            excelService.getExcel(headArr,map,response);
            result.setStatus(Status.success);

        }else{
            result.setStatus(Status.noRecord);
        }
        return result;
    }
}




