package service;

import model.Result;
import model.activity.ActivityCheckModel;
import model.activity.ActivityModel;
import model.activity.ActivityProdsModel;

/**
 * Created by ZH on 2015/5/6.
 */
public interface ActivityService {

    Result getInfo(ActivityModel activityModel);

    Result updateModel(ActivityModel activityModel);

    Result findActivityModelByProdId(ActivityModel activityModel);

    Result addActivityModel(ActivityModel activityModel);

    Result editMessage(ActivityModel activityModel);

    Result deleteProdDiscount(ActivityProdsModel activityProdsModel);

    Result selectByDiscountId(ActivityModel activityModel);

    Result activityCheck(ActivityCheckModel checkModel);
}
