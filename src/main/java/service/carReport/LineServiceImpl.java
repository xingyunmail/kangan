package service.carReport;

import DAO.LineDao;
import model.Result;
import model.Status;
import model.carReport.LineModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.LineService;

import java.util.List;

/**
 * Created by zhaijinxu on 15-5-19.
 */
@Service
public class LineServiceImpl implements LineService {

    @Autowired
    private LineDao lineDao ;

    @Override
    public Result getLineList() {
        Result result = new Result();
        List<LineModel> list = lineDao.getLineList();
        if(list.size()>0){
            result.setStatus(Status.success);
            result.setData(list);
        }else{
            result.setStatus(Status.noRecord);
        }
        return result;
    }
}
