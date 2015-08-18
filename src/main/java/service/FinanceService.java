package service;

import model.Result;
import model.finance.ExchangeCostModel;
import model.finance.FinanceModel;

import java.util.List;

/**
 * Created by scar on 15/5/20.
 */
public interface FinanceService {


    /**
     * 获取预收奶款单列表
     *
     * @param financeModel
     * @return
     */
    public Result getNewList(FinanceModel financeModel);

    /**
     * 获取预收奶款单详情
     *
     * @param financeModel
     * @return
     */
    public Result getNewInfo(FinanceModel financeModel);


    public Result getChangeList(ExchangeCostModel exchangeCostModel);


    public Result getChangeInfo(ExchangeCostModel exchangeCostModel);

}
