package service;

import model.Result;
import model.workorder.WorkOrderModel;
import model.workorder.WorkOrdersModel;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zzq on 15-5-7.
 */
public interface WorkOrderService {
    public Result getList(WorkOrdersModel workOrdersModel);

    public Result add(WorkOrderModel workOrderModel);

    public Result update(WorkOrderModel workOrderModel);

    public Result getExcel(WorkOrdersModel workOrderModel,HttpServletResponse response);
}
