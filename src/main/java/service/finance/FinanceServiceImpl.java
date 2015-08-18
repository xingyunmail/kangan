package service.finance;

import DAO.FinanceDao;
import model.Result;
import model.Status;
import model.finance.ExchangeCostModel;
import model.finance.FinanceModel;
import model.product.ProdModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.FinanceService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scar on 15/5/20.
 */
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceDao financeDao;


    @Override
    public Result getNewList(FinanceModel financeModel) {
        Result result = new Result();
        List<FinanceModel> financeModels = financeDao.getNewList(financeModel);
        int counts = financeDao.getNewListCounts(financeModel);

        if (financeModels.size() > 0) {
            result.setStatus(Status.success);
            result.setData(financeModels);
            result.setCount(counts);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result getNewInfo(FinanceModel financeModel) {
        Result result = new Result();
        List<FinanceModel> financeModels = financeDao.getNewInfo(financeModel);

        if (financeModels.size() > 0) {
            result.setStatus(Status.success);
            result.setData(financeModels);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }
    @Override
    public Result getChangeList(ExchangeCostModel exchangeCostModel){
        Result result = new Result();
        List<ExchangeCostModel> financeModels = financeDao.getChangeList(exchangeCostModel);

        if (financeModels.size() > 0) {
            result.setStatus(Status.success);
            result.setData(financeModels);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;

    }
    @Override
    public Result getChangeInfo(ExchangeCostModel exchangeCostModel){
        Result result = new Result();
        List<ExchangeCostModel> singleOrderFinanceModels = financeDao.getOrderChangeInfoByCust(exchangeCostModel);
        List<ExchangeCostModel> financeModels = new ArrayList<ExchangeCostModel>();

        for(ExchangeCostModel ecm : singleOrderFinanceModels){
            if(exchangeCostModel.getIsLessZero()==1){
                ecm.setIsLessZero(1);
            }
            financeModels = financeDao.getChangeInfo(ecm);
            ecm.setExchangeCostModels(financeModels);
            for(ExchangeCostModel e:financeModels){
                e.setOldProds(financeDao.getChangeOldProdInfo(e));
                e.setNewProds(financeDao.getChangeNewProdInfo(e));
            }

        }

        if (singleOrderFinanceModels.size() > 0) {
            result.setStatus(Status.success);
            result.setData(singleOrderFinanceModels);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;

    }
}
