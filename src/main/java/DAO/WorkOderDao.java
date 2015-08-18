package DAO;

import DAO.workorder.WorkOderDaoImpl;
import model.workorder.WorkOrderModel;
import model.workorder.WorkOrdersModel;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zzq on 15-5-7.
 */
@Repository
public interface WorkOderDao {

    @SelectProvider(type = WorkOderDaoImpl.class, method = "add")
    public void add(WorkOrderModel workOrderModel);

    @SelectProvider(type = WorkOderDaoImpl.class, method = "getList")
    public List<WorkOrderModel> getList(WorkOrdersModel workOrdersModel);

    @SelectProvider(type = WorkOderDaoImpl.class, method = "getLIstCount")
    public int getListCount(WorkOrdersModel workOrdersModel);
    @SelectProvider(type = WorkOderDaoImpl.class, method = "update")
    public void update(WorkOrderModel workOrderModel);
}
