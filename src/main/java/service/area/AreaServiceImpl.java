package service.area;

import DAO.AreaDao;
import model.Result;
import model.Status;
import model.area.AreaModel;
import model.carReport.LineModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.AreaService;

import java.util.List;

/**
 * Created by zzq on 15-5-7.
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public Result getAreaList(AreaModel areaModel) {
        Result result = new Result();
        List<AreaModel> list = areaDao.getAreaList(areaModel);
        if (list.size() > 0) {
            int total = areaDao.getAreaListCount(areaModel);
            result.setStatus(Status.success);
            result.setData(list);
            result.setCount(total);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result getArea(AreaModel areaModel) {
        Result result = new Result();
        AreaModel area = areaDao.getArea(areaModel);
        if (area != null) {
            result.setStatus(Status.success);
            result.setData(area);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result addArea(AreaModel areaModel) {

        Result result = new Result();
        //自动ID
        int areaId = areaDao.getLastAreaId(areaModel);

        areaModel.setAreaId("0" + (areaId + 1) + "");
        int num = areaDao.addArea(areaModel);
        if (num > 0) {
            result.setStatus(Status.success);
            result.setData(num);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result deleteArea(AreaModel areaModel) {
        Result result = new Result();

        int num = areaDao.deleteArea(areaModel);
        if (num > 0) {
            result.setStatus(Status.success);
            result.setData(num);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result updateArea(AreaModel areaModel) {
        Result result = new Result();
        int num = areaDao.updateArea(areaModel);
        if (num > 0) {
            result.setStatus(Status.success);
            result.setData(num);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result getLineList(AreaModel areaModel) {
        Result result = new Result();
        List<LineModel> list = areaDao.getLineList(areaModel);
        if (list.size() > 0) {
            result.setStatus(Status.success);
            result.setData(list);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }
}
