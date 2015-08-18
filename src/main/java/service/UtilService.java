package service;

import model.Result;
import model.milkbox.MilkBox;

/**
 * Created by sunhao on 15/5/4.
 */
public interface UtilService {

    Result getDicts(String itemKey);

    //安装奶箱
    Result installMilkBox(MilkBox milkBox);

    //退回奶箱
    Result returnMilkBox(MilkBox milkBox);

}
