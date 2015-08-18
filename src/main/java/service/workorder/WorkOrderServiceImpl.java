package service.workorder;

import DAO.WorkOderDao;
import model.Result;
import model.Status;
import model.workorder.WorkOrderModel;
import model.workorder.WorkOrdersModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ExcelService;
import service.WorkOrderService;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzq on 15-5-7.
 */
@Service
public class WorkOrderServiceImpl implements WorkOrderService {

    @Autowired
    private WorkOderDao workOderDao;

    @Autowired
    private ExcelService excelService;

    @Override
    public Result getList(WorkOrdersModel workOrdersModel){
        Result result = new Result();
        List<WorkOrderModel> list = workOderDao.getList(workOrdersModel);
        int count = workOderDao.getListCount(workOrdersModel);
        if(list.size()>0){
            workOrdersModel.setList(list);
            result.setStatus(Status.success);
            result.setCount(count);
            result.setData(list);
        }else{
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result getExcel(WorkOrdersModel workOrdersModel,HttpServletResponse response){
        Result result = new Result();
        List<WorkOrderModel> list = workOderDao.getList(workOrdersModel);
        String [] headArr = new String[7];
        headArr[0]="工单号";
        headArr[1]="提交时间";
        headArr[2]="客户姓名";
        headArr[3]="客户手机";
        headArr[4]="客户地址";
        headArr[5]="记录";
        headArr[6]="状态";
        if(list.size()>0){
                String [] [] arr = new String[list.size()][7];
                for(int i=0;i<list.size();i++){
                    arr[i][0] = list.get(i).getWorkNum();
                    arr[i][1] = list.get(i).getCreateTime();
                    arr[i][2] = list.get(i).getCustomerName();
                    arr[i][3] = list.get(i).getCustomerPhone();
                    arr[i][4] = list.get(i).getAddress();
                    arr[i][5] = list.get(i).getRemark();
                    arr[i][6] = list.get(i).getStatus()+"";
                }
                Map<String,String [] []> map = new HashMap<String, String[][]>();
                map.put("sheet1",arr);
                map.put("sheet3",arr);
                excelService.getExcel(headArr, map, response);
                result.setStatus(Status.success);

        }else{
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result add(WorkOrderModel workOrderModel){
        Result result = new Result();
        try {
            workOderDao.add(workOrderModel);
            result.setStatus(Status.success);
        }catch (Exception e){
            result.setStatus(Status.error);
        }
        return result;
    }

    @Override
    public Result update(WorkOrderModel workOrderModel){
        Result result = new Result();
        try {
            workOderDao.update(workOrderModel);
            result.setStatus(Status.success);
        }catch (Exception e){
            result.setStatus(Status.error);
        }
        return result;
    }
}
