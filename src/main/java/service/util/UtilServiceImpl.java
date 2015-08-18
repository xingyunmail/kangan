package service.util;

/**
 * Created by sunhao on 15/5/4.
 */

import DAO.UtilDao;
import model.Result;
import model.Status;
import model.dictionary.DictModel;
import model.milkbox.MilkBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.UtilService;

import java.util.List;

@Service
public class UtilServiceImpl implements UtilService{

    @Autowired
    private UtilDao utilDao;


    public UtilServiceImpl() {
        super();
    }

    @Override
    public Result getDicts(String itemkey)
    {
        DictModel dictModel = new DictModel();
        dictModel.setItemKey(itemkey);

        Result result = new Result();
        List<DictModel> dictList = utilDao.getDictInfo(dictModel);

        if(dictList.size() > 0)
        {
            result.setStatus(Status.success);
        }
        else
        {
            result.setStatus(Status.noRecord);
        }

        result.setData(dictList);

        return result;
    }

    @Override
    public Result installMilkBox(MilkBox milkBox) {
        Result result = new Result();

        int affects = utilDao.insertMilkBox(milkBox);

        if(0 != affects)
        {
            result.setStatus(Status.success);
        }
        else
        {
            result.setStatus(Status.error);
        }

        result.setData(affects);

        return result;
    }

    @Override
    @Transactional
    public Result returnMilkBox(MilkBox milkBox)
    {
        Result result = new Result();
        int affects=0;

        String [] boxids = milkBox.getBoxId().split("\\r?\\n");



        for(String boxid:boxids)
        {
            MilkBox box = new MilkBox();
            box.setCustomeId(milkBox.getCustomeId());
            box.setBoxId(boxid);
            affects += utilDao.returnMilkBox(box);
        }

        if(affects == boxids.length)
        {
            result.setStatus(Status.success);
        }
        else
        {
            result.setStatus(Status.error);
        }

        result.setData(affects);

        return result;

    }


}
