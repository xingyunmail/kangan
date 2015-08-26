package service.Activity;

import DAO.ActivityDao;
import DAO.ActivityProdsDao;
import model.Result;
import model.Status;
import model.activity.ActivityCheckModel;
import model.activity.ActivityModel;
import model.activity.ActivityProdsModel;
import model.activity.ActivityVO;
import model.finance.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ActivityService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZH on 2015/5/6.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    public ActivityDao activityDao;
    @Autowired
    public ActivityProdsDao activityProdsDao;

    @Override
    public Result getInfo(ActivityModel activityModel) {
        Result result = new Result();

        List<ActivityModel> activityModelList = activityDao.getList(activityModel);

        if (activityModelList.size() > 0) {
            int count = activityDao.getListCount(activityModel);
            result.setData(activityModelList);
            result.setStatus(Status.success);
            result.setCount(count);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result updateModel(ActivityModel activityModel) {
        Result result = new Result();
        int updateRecode = activityDao.updateModel(activityModel);
        if (updateRecode > 0) {
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.error);
        }
        return result;
    }

    @Override
    public Result findActivityModelByProdId(ActivityModel activityModel) {
        Result result = new Result();
        ActivityModel activityModelMessage = activityDao.findActivityModelByProdId(activityModel);
        if (activityModelMessage != null) {

            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.noRecord);
        }
        result.setData(activityModelMessage);
        return result;
    }

    @Override
    public Result addActivityModel(ActivityModel activityModel) {
        Result result = new Result();
        //step1 活动表
        //活动创建时间 使用mysql now();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
//        String insertDate = sdf.format(new Date());
//        activityModel.setInsertDate(insertDate);
        //如果活动ID 为空，新增信息 否则 保存信息
        if (activityModel.getEditdiscountId().equals("") || activityModel.getEditdiscountId() == null) {
            int discountId_key = activityDao.addActivityModel(activityModel);
            if(discountId_key>0){
                result.setStatus(Status.success);
            }else{
                result.setStatus(Status.error);
            }

            //根据赠品信息查询出是否是 赠品 还是增奶，（priceId =1赠品 2增奶）20150528  20：21

            //step2 优惠信息表
            //存商品   商品  赠品
            if (!"".equals(activityModel.getBatchprodId())) {
                String[] batchProdId = activityModel.getBatchprodId().split(",");
                String[] batchPricId = activityModel.getPriceId().split(",");

                for (int i = 0; i < batchProdId.length; i++) {
                    ActivityProdsModel activityProdsModel = new ActivityProdsModel();
                    activityProdsModel.setDiscountId(activityModel.getDiscountId() + "");//活动ID
                    activityProdsModel.setProdId(batchProdId[i]);//产品ID
                    activityProdsModel.setIsGift("0");
                    activityProdsModel.setQuantity("0");
                    activityProdsModel.setPriceId(batchPricId[i]);//正常商品价格
                    activityProdsDao.addActivityProdsModel(activityProdsModel);
                }
            }
            if (!activityModel.getBatchGiftProdId().equals("")) {
                String[] batchGiftProdId = activityModel.getBatchGiftProdId().split(",");
                String[] quantity = activityModel.getQuantity().split(",");
                String[] priceIdGift = activityModel.getPriceIdGift().split(",");
                for (int i = 0; i < batchGiftProdId.length; i++) {

                    String pid = batchGiftProdId[i].toString();
                    String gid = quantity[i].toString();
                    ActivityProdsModel activityProdsModelGift = new ActivityProdsModel();
                    activityProdsModelGift.setDiscountId(activityModel.getDiscountId() + "");//活动ID
                    activityProdsModelGift.setProdId(pid);//产品ID
                    activityProdsModelGift.setQuantity(gid);    //数量
                    activityProdsModelGift.setIsGift("1");    //是否是赠品 1 是 0 不是',
                    activityProdsModelGift.setPriceId(priceIdGift[i]); //赠品价格ID
                    activityProdsDao.addActivityProdsModel(activityProdsModelGift);
                }
            }
        } else {//更新
            int updateNum = activityDao.updateModelInfo(activityModel);
            if(updateNum >0){
                result.setStatus(Status.success);
            }else{
                result.setStatus(Status.error);
            }
            if (!"".equals(activityModel.getBatchprodId())) {
                String[] batchProdId = activityModel.getBatchprodId().split(",");
                String[] batchPricId = activityModel.getPriceId().split(",");
                for (int i = 0; i < batchProdId.length; i++) {
                    ActivityProdsModel activityProdsModel = new ActivityProdsModel();
                    activityProdsModel.setDiscountId(activityModel.getEditdiscountId());//活动ID
                    activityProdsModel.setProdId(batchProdId[i]);//产品ID
                    activityProdsModel.setIsGift("0");
                    activityProdsModel.setQuantity("0");
                    activityProdsModel.setPriceId(batchPricId[i]);
                    int count = activityProdsDao.updateByDiscountId(activityProdsModel);
                    if (count == 0) {
                        activityProdsDao.addActivityProdsModel(activityProdsModel);
                    }
                }
            }
            if (!activityModel.getBatchGiftProdId().equals("")) {
                String[] batchGiftProdId = activityModel.getBatchGiftProdId().split(",");
                String[] quantity = activityModel.getQuantity().split(",");
                String[] priceIdGift = activityModel.getPriceIdGift().split(",");
                for (int i = 0; i < batchGiftProdId.length; i++) {

                    String pid = batchGiftProdId[i].toString();
                    String gid = quantity[i].toString();
                    ActivityProdsModel activityProdsModelGift = new ActivityProdsModel();
                    activityProdsModelGift.setDiscountId(activityModel.getEditdiscountId());//活动ID
                    activityProdsModelGift.setProdId(pid);      //产品ID
                    activityProdsModelGift.setQuantity(gid);   //数量
                    activityProdsModelGift.setIsGift("1");    //是否是赠品 1 是 0 不是',
                    activityProdsModelGift.setPriceId(priceIdGift[i]);  //赠品价格ID
                    int count = activityProdsDao.updateByDiscountId(activityProdsModelGift);
                    if (count == 0) {
                        activityProdsDao.addActivityProdsModel(activityProdsModelGift);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Result editMessage(ActivityModel activityModel) {
        Result result = new Result();
        List<ActivityVO> activityVO = activityDao.editActivityMessage(activityModel);
        if (activityVO.size() == 0) {
            result.setStatus(Status.noRecord);
        } else {
            result.setStatus(Status.success);
        }
        result.setData(activityVO);
        return result;
    }
    /**
     * 提供订单页面方法
     * @param activityProdsModel
     * @return
     */
    @Override
    public Result deleteProdDiscount(ActivityProdsModel activityProdsModel) {
        Result result = new Result();
        int count = activityProdsDao.deleteProdDiscount(activityProdsModel);
        System.out.println("delete " + count);
        if (count == 0){
            result.setStatus(Status.noRecord);
        }else{
            result.setStatus(Status.success);
        }
        return result;
    }

    @Override
    public Result selectByDiscountId(ActivityModel activityModel) {
        Result result = new Result();

        //提供给订单 1、查询有效期内的所有活动 2、查询活动商品，赠品信息
        if(null!=activityModel.getDiscountStatus() &&""!=activityModel.getDiscountStatus() && activityModel.getDiscountStatus().equals("all")){
            //获得系统当前的日期与查询出的开始日期与结束日期 比对，当前时间是否在活动有效期内
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String current = simpleDateFormat.format(new Date());
            activityModel.setBeginDate(current);
            List<ActivityModel> allDiscount = activityDao.getList(activityModel);

            if(allDiscount.size()>0){
                result.setStatus(Status.success);
            }else{
                result.setStatus(Status.noRecord);
            }

            result.setData(allDiscount);
        }else{//2、查询活动商品，赠品信息
            //活动信息
            ActivityModel discountinfo = activityProdsDao.selectByDiscountId(activityModel);
            //0 商品信息 1 赠品信息
            List<ActivityProdsModel> prod_list = new ArrayList<ActivityProdsModel>();
            List<ActivityProdsModel> gift_list = new ArrayList<ActivityProdsModel>();

            List<ActivityProdsModel> activityProdsModelList = activityProdsDao.selectByDiscountIdProdinfo(activityModel);
            for (int i=0;i<activityProdsModelList.size();i++){
                if(activityProdsModelList.get(i).getIsGift().equals("0")){
                    prod_list.add(activityProdsModelList.get(i));
                }else if(activityProdsModelList.get(i).getIsGift().equals("1")){
                    gift_list.add(activityProdsModelList.get(i));
                }
            }
            if(activityProdsModelList.size()>0){
                discountinfo.setProdList(prod_list);
                discountinfo.setGiftList(gift_list);
            }
            if(discountinfo!=null){
                result.setStatus(Status.success);
            }else{
                result.setStatus(Status.noRecord);
            }
            result.setData(discountinfo);

        }

        return result;
    }

    @Override
    public Result activityCheck(ActivityCheckModel checkModel) {

        Result result = new Result();

        ActivityModel activityModel = activityDao.getActivityInfo(checkModel);
        List<ProductModel> activityProdsModels = activityProdsDao.getActivityProds(checkModel);

        double moneys = 0;
        int products = 0;
        List<ProductModel> productModels = new ArrayList<ProductModel>();
        for (ProductModel p:checkModel.getProductModels()){
            for (ProductModel prodsModel:activityProdsModels){
                if (p.getProdId().equals(prodsModel.getProdId())){
                    productModels.add(p);
                }
            }
        }

        int multiple = 1;
        int addStatus = 0;
        if (productModels.size() > 0){
            if (activityModel.getIsAdd().equals("1")){
                if (activityModel.getDiscountType().equals("1")){
                    for (ProductModel pd:productModels){
                        products = products + pd.getNumber();
                    }
                    multiple = products/Integer.parseInt(activityModel.getDiscountInfo());
                }else if (activityModel.getDiscountType().equals("2")){
                    for (ProductModel pd:productModels){
                        moneys = moneys + pd.getPrice();
                    }
                    multiple = (int)moneys/Integer.parseInt(activityModel.getDiscountInfo());
                }
            }else if (activityModel.getIsAdd().equals("0")){

                if (activityModel.getDiscountType().equals("1")){
                    for (ProductModel pd:productModels){
                        products = products + pd.getNumber();
                    }
                    multiple = products/Integer.parseInt(activityModel.getDiscountInfo());
                }else if (activityModel.getDiscountType().equals("2")){
                    for (ProductModel pd:productModels){
                        moneys = moneys + pd.getPrice();
                    }
                    multiple = (int)moneys/Integer.parseInt(activityModel.getDiscountInfo());
                }

                if (multiple > 1){
                    addStatus = 1;
                    multiple = 1;
                }
            }
        }

        List<ProductModel> giftProducts = activityProdsDao.getGiftProduct(checkModel);
        ActivityCheckModel activityCheckModel = new ActivityCheckModel();
        activityCheckModel.setMultiple(multiple);
        activityCheckModel.setProductModels(giftProducts);
        activityCheckModel.setGiftNum(activityModel.getGiftNum());

        if (moneys > 0 || products > 0 || addStatus == 1){
            result.setStatus(Status.success);
            result.setData(activityCheckModel);
        }else {
            result.setStatus(Status.error);
        }
        return result;
    }

}
