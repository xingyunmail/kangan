package service.complaints;

import DAO.ComplaintsDao;
import model.Result;
import model.Status;
import model.complaints.ComplaintsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ComplaintsService;

import java.util.List;

/**
 * Created by zzq on 15-5-8.
 */
@Service
public class ComplaintsServiceImpl implements ComplaintsService {

    @Autowired
    private ComplaintsDao complaintsDao;

    @Override
    public Result getCompList(){
        Result result = new Result();
        List<ComplaintsModel> list = complaintsDao.getCompType();
        if(list.size()>0){
            result.setStatus(Status.success);
            result.setData(list);
        }else{
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result getCompById(int type){
        Result result = new Result();
        List<ComplaintsModel> list = complaintsDao.getCompById(type);
        if(list.size()>0){
            result.setStatus(Status.success);
            result.setData(list);
        }else{
            result.setStatus(Status.noRecord);
        }
        return result;

    }
}
