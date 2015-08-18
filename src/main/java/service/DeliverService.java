package service;


import model.Result;
import model.deliver.DeliverOrderModel;
import model.deliver.ReceiveMilkOrderModel;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by lining on 15-5-12.
 */
public interface DeliverService {


    Result getMilkOrderList(ReceiveMilkOrderModel receiveMilkOrderModel);

    Result getDeliverOrder(DeliverOrderModel deliverOrderModel);

    Result getChangeOrder(DeliverOrderModel deliverOrderModel);

    Result getTasteMilk(ReceiveMilkOrderModel receiveMilkOrderModel);

    Result exportReceive(ReceiveMilkOrderModel receiveMilkOrderModel,HttpServletResponse response);

    Result exportChange(DeliverOrderModel deliverOrderModel,HttpServletResponse response);

    Result exportDeliver(DeliverOrderModel deliverOrderModel,HttpServletResponse response);

    Result numberCount(DeliverOrderModel deliverOrderModel);


}
