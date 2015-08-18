package controller;

import model.Result;
import model.finance.ExchangeCostModel;
import model.finance.FinanceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.FinanceService;

/**
 * Created by scar on 15/5/20.
 */
@Controller
@RequestMapping("/finance")
public class Finance {


    @Autowired
    private FinanceService financeService;

    /**
     * 跳转预收功能奶款单列表
     *
     * @return
     */
    @RequestMapping("new")
    public String toNew() {
        return "finance/new";
    }

    /**
     * 获取预收奶款单列表
     *
     * @param financeModel
     * @return
     */
    @RequestMapping(value = "newList", method = RequestMethod.POST)
    @ResponseBody
    public Result getNewList(@RequestBody FinanceModel financeModel) {
        return financeService.getNewList(financeModel);
    }

    /**
     * 获取预收奶款单详情
     *
     * @param financeModel
     * @return
     */
    @RequestMapping(value = "newInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result getNewInfo(@RequestBody FinanceModel financeModel) {
        return financeService.getNewInfo(financeModel);
    }

    @RequestMapping("changeMilkCostList")
    public ModelAndView getChangeMilkCostList() {
        return new ModelAndView("/finance/changeMilkCostList");
    }

    @RequestMapping("getChangeList")
    @ResponseBody
    public Result getChangeList(ExchangeCostModel exchangeCostModel){

        Result result = financeService.getChangeList(exchangeCostModel);

        return result;

    }

    @RequestMapping("getChangeInfo")
    @ResponseBody
    public Result getChangeInfo(ExchangeCostModel exchangeCostModel){

        Result result = financeService.getChangeInfo(exchangeCostModel);

        return result;

    }

}
