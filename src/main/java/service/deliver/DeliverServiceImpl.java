package service.deliver;

import DAO.AreaDao;
import DAO.DeliverDao;
import model.Result;
import model.Status;
import model.deliver.DeliverOrderModel;
import model.deliver.ReceiveMilkOrderModel;
import model.deliver.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.DeliverService;
import service.ExcelService;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lining on 15-5-12.
 * 配送管理service
 */
@Service
public class DeliverServiceImpl implements DeliverService {

    @Autowired
    DeliverDao deliverDao;

    @Autowired
    AreaDao areaDao;

    @Autowired
    ExcelService excelService;


    @Override
    public Result getMilkOrderList(ReceiveMilkOrderModel receiveMilkOrderModel) {

        List<ReceiveMilkOrderModel> rms = deliverDao.getMilkOrderList(receiveMilkOrderModel);

        List<ReceiveMilkOrderModel> addMilk = deliverDao.getAddMilk(receiveMilkOrderModel);

        List<ReceiveMilkOrderModel> tasteMilks = deliverDao.getTasteMilk(receiveMilkOrderModel);

        rms.addAll(addMilk);

        List<Table> tables = new ArrayList<Table>();

        List<String> prodNames = new ArrayList<String>();
        List<String> userNames = new ArrayList<String>();

        for(ReceiveMilkOrderModel rm : rms){
            if(!prodNames.contains(rm.getProdName())){
                prodNames.add(rm.getProdName());
            }
            if(!userNames.contains(rm.getUserName())){
                userNames.add(rm.getUserName());
            }
        }
        List<String> headList = new ArrayList<String>();
        headList.addAll(prodNames);
        headList.add("合计");
        headList.add("回瓶数");

        //表头
        Table t_head = new Table();
        t_head.setUserName("送奶员");
        t_head.setHead(headList);
        tables.add(t_head);



        for(String userName : userNames) {
            List<Integer> countProd = new ArrayList<Integer>();
            int count_user = 0;
            int prodPackage = 0;
            for(String prodName : prodNames){
                int num = 0;
                for (ReceiveMilkOrderModel rm : rms) {
                    if (prodName.equals(rm.getProdName()) && userName.equals(rm.getUserName())) {
                        if("1".equals(rm.getProdPackage())){
                            prodPackage += Integer.parseInt(rm.getNumber());
                        }
                        num += Integer.parseInt(rm.getNumber());
                    }
                }
                count_user += num;
                countProd.add(num);
            }
            countProd.add(count_user);
            countProd.add(prodPackage);
            Table t_body = new Table();
            t_body.setUserName(userName);
            t_body.setCountProd(countProd);
            tables.add(t_body);
        }

        //品尝奶行
        Table t_taste = new Table();
        t_taste.setUserName("品尝奶");
        List<Integer> countProd = new ArrayList<Integer>();
        int taste_count = 0;
        int prodPackage = 0;
        for(String prodName : prodNames){
            int tasteNum = 0;
            for(ReceiveMilkOrderModel rm : tasteMilks){
                if(prodName.equals(rm.getProdName())){
                    if("1".equals(rm.getProdPackage())){
                        prodPackage += Integer.parseInt(rm.getNumber());
                    }
                    tasteNum += Integer.parseInt(rm.getNumber());
                }
            }
            taste_count += tasteNum;
            countProd.add(tasteNum);

        }
        countProd.add(taste_count);
        countProd.add(prodPackage);
        t_taste.setCountProd(countProd);
        tables.add(t_taste);

        //表尾合计行
        Table t_tail = new Table();
        t_tail.setUserName("合计");
        List<Integer> colCountProd = new ArrayList<Integer>();
        int j = 0;
        for(String head : headList){
                int colCount = 0;
                for(int i=1;i<tables.size();i++){
                    colCount += tables.get(i).getCountProd().get(j);
                }
                j++;
                colCountProd.add(colCount);
        }
        t_tail.setCountProd(colCountProd);
        tables.add(t_tail);
        //构造Result
        Result result = new Result();
        if (null != tables && tables.size() > 0) {
            result.setData(tables);
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }


    public Result exportReceive(ReceiveMilkOrderModel receiveMilkOrderModel,HttpServletResponse response){

        Result result =  getMilkOrderList(receiveMilkOrderModel);

        List<Table> tables = (List<Table>)result.getData();

        List<String> headList = tables.get(0).getHead();
        headList.add(0,"送奶员");

        String[] headArr = (String[])headList.toArray(new String[headList.size()]);

        Map map = new HashMap<String,String[][]>();


        if(tables.size()>0){
            String [] [] arr = new String[tables.size()-1][headArr.length];
            for(int i=1;i<tables.size();i++){
                for(int j=0;j<headArr.length;j++){
                    if(j==0) {
                        arr[i-1][j] = tables.get(i).getUserName();
                    }else{
                        for(int k=0;k<tables.get(i).getCountProd().size();k++){
                            if(j==k+1){
                                arr[i-1][j]=tables.get(i).getCountProd().get(k)+"";
                            }
                        }
                    }

                }
            }
            map.put("sheet1",arr);
            excelService.getExcel(headArr, map, response);
            result.setStatus(Status.success);

        }else{
            result.setStatus(Status.error);
        }



        return result;
    }


    public Result getDeliverOrder(DeliverOrderModel deliverOrderModel){
        List<DeliverOrderModel> doms = deliverDao.getDeliverOrderInfo(deliverOrderModel);

        List<DeliverOrderModel> domCount = deliverDao.getDeliverOrderCountByProc(deliverOrderModel);
        int count=0;
        if(domCount!=null && domCount.size()>0){
            count = domCount.size();
        }

        //构造Result
        Result result = new Result();
        if (null != doms && doms.size() > 0) {
            result.setCount(count);
            result.setData(doms);
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.noRecord);
        }

        return result;

    }
/*    public Result getChangeOrder(DeliverOrderModel deliverOrderModel){
        //今天的订单
        List<DeliverOrderModel> doms = deliverDao.getChangeOrderInfo(deliverOrderModel);
//        List<DeliverOrderModel> doms = deliverDao.getDeliverOrderInfo(deliverOrderModel);
        List<DeliverOrderModel> domsTempT = new ArrayList<DeliverOrderModel>();
        domsTempT.addAll(doms);
        //昨天的订单
        String yestoday = "";
        if(!StringUtils.isEmpty(deliverOrderModel.getStartDate())){
            yestoday = getBefore(deliverOrderModel.getStartDate());
        }
        deliverOrderModel.setStartDate(yestoday);
        List<DeliverOrderModel> doms2 = deliverDao.getChangeOrderInfo(deliverOrderModel);
//        List<DeliverOrderModel> doms2 = deliverDao.getDeliverOrderInfo(deliverOrderModel);
        List<DeliverOrderModel> domsTempY = new ArrayList<DeliverOrderModel>();
        domsTempY.addAll(doms2);
        List<DeliverOrderModel> newDomList = new ArrayList<DeliverOrderModel>();
        //开始比较今天和昨天的记录

        for(DeliverOrderModel dom:doms){
            for(DeliverOrderModel dom2:doms2){
                if((dom.getUserId().equals(dom2.getUserId()))&& (dom.getCustId().equals(dom2.getCustId())) && (dom.getProdId().equals(dom2.getProdId())) && (dom.getProdType()==dom2.getProdType())){
                    int countT = dom.getNumber();
                    int countY = dom2.getNumber();
                    int statusT = dom.getStatus();
                    int statusY = dom2.getStatus();
                    if(countT==countY){
                        continue;
                    }else{
                        //今天是正常配送
                        if(statusT==1){
                            //昨天也是正常配送结束，今天的重新下的订单
                            if(statusY==1){
                                dom.setChangeType("配送结束后的新订单");
                                dom.setRest(deliverDao.getRestNumber(dom));
                                newDomList.add(dom);
                            //昨天是停奶状态，今天是又对某个产品下单
                            }else if(statusY==2){
                                dom.setChangeType("停奶后正常配送");
                                dom.setRest(deliverDao.getRestNumber(dom));
                                newDomList.add(dom);
                            //昨天是退奶，今天是正常配送（重新下单）
                            }else if(statusY==3){
                                dom.setChangeType("退奶后重新下单");
                                dom.setRest(deliverDao.getRestNumber(dom));
                                newDomList.add(dom);
                            }
                            if(dom.getNumber()>dom2.getNumber()){
                                dom.setChange("增加了");
                                dom.setNumber(dom.getNumber()-dom2.getNumber());
                            }else if(dom.getNumber()<dom2.getNumber()){
                                dom.setChange("减少了");
                                dom.setNumber(dom2.getNumber()-dom.getNumber());
                            }
                        }
                        //状态是停奶时
                        else if(statusT==2){
                            //今天是停奶，昨天是正常，没有这种情况
                            if(statusY==1){

                            //今天是停奶，昨天是停奶（中间经过一次换奶再停奶，会出现这种状况,这种状况的变动类型怎么写？？？）
                            }else if(statusY==2){
                                dom.setChangeType("正常配送");
                                dom.setChange("增加了");
                                dom.setRest(deliverDao.getRestNumber(dom)+deliverDao.getRestNumber(dom2));
                                newDomList.add(dom);
                            //今天是停奶，昨天是退奶，正常情况下应该是不存在的。
                            }else if(statusY==3){
                                dom.setChangeType("正常配送");
                                dom.setChange("增加了");
                                dom.setRest(deliverDao.getRestNumber(dom)+deliverDao.getRestNumber(dom2));
                                newDomList.add(dom);

                            }
                            //今天是停奶，昨天是换奶
                            else if(statusY==4){
                                dom.setChangeType("换奶");
                                dom.setChange("增加了");
                                dom.setRest(deliverDao.getRestNumber(dom));
                                newDomList.add(dom);
                            }


                        }

                        //今天的状态是退奶，想不出别的情况
                        else if(statusT==3){
                            //今天是退奶，昨天是正常，没有这种情况
                            if(statusY==1){

                            //今天是退奶，昨天是停奶（暂时不考虑这种情况）
                            }else if(statusY==2){

                            }
                            //今天是退奶，昨天是退奶（暂时不考虑这种状态）
                            else if(statusY==3){

                            }
                            //今天是退奶，昨天是换奶
                            else if(statusY==4){
                                dom.setChangeType("换奶");
                                dom.setChange("增加了");
                                dom.setRest(deliverDao.getRestNumber(dom));
                                newDomList.add(dom);
                            }
                        }
                        //今天的状态是换奶,此客户之前也订了此品种的奶
                        else if(statusT==4){
                            if(statusY==1){
                                dom.setChangeType("换奶");
                                dom.setChange("增加了");
                                dom.setRest(deliverDao.getRestNumber(dom)+deliverDao.getRestNumber(dom2));
                                newDomList.add(dom);
                            //今天是换奶，昨天是停奶状态，导致今天
                            }else if(statusY==2){
                                dom.setChangeType("换奶");
                                dom.setChange("增加了");
                                dom.setRest(deliverDao.getRestNumber(dom)+deliverDao.getRestNumber(dom2));
                                newDomList.add(dom);
                            //今天是换奶，昨天是退奶
                            }else if(statusY==3){
                                dom.setChangeType("换奶");
                                dom.setChange("增加了");
                                dom.setRest(deliverDao.getRestNumber(dom)+deliverDao.getRestNumber(dom2));
                                newDomList.add(dom);
                            //今天是换奶，昨天也是换奶（可能出现多条记录，暂不管这种情况）
                            }else if(statusY==4){
                                dom.setChangeType("换奶");
                                dom.setChange("增加了");
                                dom.setRest(deliverDao.getRestNumber(dom)+deliverDao.getRestNumber(dom2));
                                newDomList.add(dom);
                            }
                        }

                        *//*
                        * 正常  1
                        * 停   2
                        * 退   3
                        * 换   4
                        *//*
                    }

                }else{

                }
            }

        }




        for(DeliverOrderModel domT:domsTempT){
            for(DeliverOrderModel domY:domsTempY){
                if((domT.getCustId().equals(domY.getCustId())) &&
                   (domT.getUserId().equals(domY.getUserId())) &&
                   (domT.getProdId().equals(domY.getProdId())) &&
                   (domT.getProdType()==domY.getProdType())){
                    doms.remove(domT);
                    break;
                }
            }
        }
        //昨天没有，今天有
        if(doms.size()>0){
           for(DeliverOrderModel domT : doms){
                int status = domT.getStatus();
                //正常配送情况
                if(status==1){
                    domT.setChangeType("开始配送");
                    domT.setChange("增加了");
                //停奶情况，只比较停奶前正常配送开始时间和前一天的情况
                }else if(status==2){
                    domT.setChangeType("开始配送");
                    domT.setChange("增加了");
                }
                //退奶情况 （与停奶情况一样）
                else if(status==3){
                    domT.setChangeType("开始配送");
                    domT.setChange("增加了");
                }
                //换奶情况
                else if(status==4){
                    domT.setChangeType("换奶");
                    domT.setChange("增加了");

                }
               newDomList.add(domT);
               domT.setRest(deliverDao.getRestNumber(domT));
           }
        }
        //昨天有，今天没有
        for(DeliverOrderModel domY:domsTempY){
            for(DeliverOrderModel domT:domsTempT){
                if((domY.getCustId().equals(domT.getCustId())) &&
                   (domY.getUserId().equals(domT.getUserId())) &&
                   (domY.getProdId().equals(domT.getProdId())) &&
                   (domT.getProdType()==domY.getProdType())){
                    doms2.remove(domY);
                    break;
                }
            }
        }


        if(doms2.size()>0){
            for(DeliverOrderModel domY:doms2){
                int status = domY.getStatus();
                domY.setStartDate(yestoday);
                //正常派送完成
                if(status==1){
                    domY.setChangeType("配送结束");
                    domY.setChange("减少了");
                //昨天的状态是停奶，今天没有数据
                }else if(status==2){
                    domY.setChangeType("停奶");
                    domY.setChange("减少了");
                //昨天的状态是退奶，今天没有
                }else if(status==3){
                    domY.setChangeType("退奶");
                    domY.setChange("减少了");
                }
                //换奶
                else if(status==4){
                    domY.setChangeType("换奶");
                    domY.setChange("减少了");

                }
                domY.setRest(deliverDao.getRestNumber(domY));
                newDomList.add(domY);
            }
        }

        //构造Result
        Result result = new Result();
        if (null != newDomList && newDomList.size() > 0) {
            result.setData(newDomList);
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.noRecord);
        }

        return result;

    }*/


    public Result getChangeOrder(DeliverOrderModel deliverOrderModel){

        List<DeliverOrderModel> domListT = deliverDao.getChangeOrderInfo(deliverOrderModel);
        List<DeliverOrderModel> domTempT = new ArrayList<DeliverOrderModel>();
        domTempT.addAll(domListT);

        String today = deliverOrderModel.getStartDate();
        String yestoday = getBefore(today);
        deliverOrderModel.setStartDate(yestoday);
        List<DeliverOrderModel> domListY = deliverDao.getChangeOrderInfo(deliverOrderModel);
        List<DeliverOrderModel> domTempY = new ArrayList<DeliverOrderModel>();
        domTempY.addAll(domListY);
        List<DeliverOrderModel> newDomList = new ArrayList<DeliverOrderModel>();
        List<DeliverOrderModel> sameList = new ArrayList<DeliverOrderModel>();
        for(DeliverOrderModel domT:domListT){
            for(DeliverOrderModel domY:domListY){
                if(domT.getId()==domY.getId()){

                    /*
                    &&
                    (domT.getUserId().equals(domY.getUserId())) &&
                    (domT.getCustId().equals(domY.getCustId())) &&
                    (domT.getProdId().equals(domY.getProdId())) &&
                    (domT.getProdType() == domY.getProdType())
                    */



//                    sameList.add(domT);
                    domTempT.remove(domT);
                    domTempY.remove(domY);

                    int countT = domT.getNumber();
                    int countY = domY.getNumber();
                    int statusT = domT.getStatus();
                    int statusY = domY.getStatus();
                    if(countT == countY){
                        continue;
                    }
                }

            }
        }
        //今天有，昨天没有
        if(domTempT.size()>0){
            for(DeliverOrderModel domT:domTempT){
                int status = domT.getStatus();
                int deliverRules = domT.getDeliverRules();
                //正常配送
                if(status==1){
                    if(deliverRules==1){
                        domT.setChangeType("新订单");
                        domT.setChange("增加了");
                        domT.setRest(deliverDao.getRestNumber(domT));
                    }else{
                        if(deliverRules==2){
                            domT.setChangeType("工作日送");
                        }else if(deliverRules==3){
                            domT.setChangeType("周末送");
                        }else if(deliverRules==4){
                            domT.setChangeType("隔天送");
                        }
                        domT.setChange("增加了");
                        domT.setRest(deliverDao.getRestNumber(domT));
                    }
                //停奶
                }else if(status==2){
                    if(deliverRules==1){
                        domT.setChangeType("停奶");
                        domT.setChange("增加了");
                        domT.setRest(deliverDao.getRestNumber(domT));
                    }else{
                        if(deliverRules==2){
                            domT.setChangeType("工作日送");
                        }else if(deliverRules==3){
                            domT.setChangeType("周末送");
                        }else if(deliverRules==4){
                            domT.setChangeType("隔天送");
                        }
                        domT.setChange("增加了");
                        domT.setRest(deliverDao.getRestNumber(domT));
                    }
                //退奶
                }else if(status==3){
                    if(deliverRules==1){
                        domT.setChangeType("新订单");
                        domT.setChange("增加了");
                        domT.setRest(deliverDao.getRestNumber(domT));
                    }else{
                        if(deliverRules==2){
                            domT.setChangeType("工作日送");
                        }else if(deliverRules==3){
                            domT.setChangeType("周末送");
                        }else if(deliverRules==4){
                            domT.setChangeType("隔天送");
                        }
                        domT.setChange("增加了");
                        domT.setRest(deliverDao.getRestNumber(domT));
                    }
                //换奶
                }else if(status==4){
                    if(deliverRules==1){
                        domT.setChangeType("新订单");
                        domT.setChange("增加了");
                        domT.setRest(deliverDao.getRestNumber(domT));
                    }else{
                        if(deliverRules==2){
                            domT.setChangeType("工作日送");
                        }else if(deliverRules==3){
                            domT.setChangeType("周末送");
                        }else if(deliverRules==4){
                            domT.setChangeType("隔天送");
                        }
                        domT.setChange("增加了");
                        domT.setRest(deliverDao.getRestNumber(domT));
                    }
                //配送完成
                }else if(status==5){
                    if(deliverRules==1){
                        domT.setChangeType("新订单");
                        domT.setChange("增加了");
                        domT.setRest(deliverDao.getRestNumber(domT));
                    }else{
                        if(deliverRules==2){
                            domT.setChangeType("工作日送");
                        }else if(deliverRules==3){
                            domT.setChangeType("周末送");
                        }else if(deliverRules==4){
                            domT.setChangeType("隔天送");
                        }
                        domT.setChange("增加了");
                        domT.setRest(deliverDao.getRestNumber(domT));
                    }
                }
            }
        }

        if(domTempY.size()>0){
            for(DeliverOrderModel domY:domTempY){
                int status = domY.getStatus();
                int deliverRules = domY.getDeliverRules();
                //正常配送
                if(status==1){
                    if(deliverRules==1){
                        domY.setChangeType("停奶");
                        domY.setChange("减少了");
                        domY.setRest(deliverDao.getRestNumber(domY));
                    }else{
                        if(deliverRules==2){
                            domY.setChangeType("工作日送");
                        }else if(deliverRules==3){
                            domY.setChangeType("周末送");
                        }else if(deliverRules==4){
                            domY.setChangeType("隔天送");
                        }
                        domY.setChange("减少了");
                        domY.setRest(deliverDao.getRestNumber(domY));

                    }
                //停奶
                }else if(status==2){
                    domY.setChangeType("停奶");
                    domY.setChange("减少了");
                    domY.setRest(deliverDao.getRestNumber(domY));
                //退奶
                }else if(status==3){
                    if(deliverRules==1){
                        domY.setChangeType("退奶");
                        domY.setChange("减少了");
                        domY.setRest(deliverDao.getRestNumber(domY));
                    }else{
                        if(deliverRules==2){
                            domY.setChangeType("工作日送");
                        }else if(deliverRules==3){
                            domY.setChangeType("周末送");
                        }else if(deliverRules==4){
                            domY.setChangeType("隔天送");
                        }
                        domY.setChange("减少了");
                        domY.setRest(deliverDao.getRestNumber(domY));
                    }
                //换奶
                }else if(status==4){
                    if(deliverRules==1){
                        domY.setChangeType("换奶");
                        domY.setChange("减少了");
                        domY.setRest(deliverDao.getRestNumber(domY));
                    }
                    if(deliverRules==2){
                        domY.setChangeType("工作日送");
                    }else if(deliverRules==3){
                        domY.setChangeType("周末送");
                    }else if(deliverRules==4){
                        domY.setChangeType("隔天送");
                    }
                    domY.setChange("减少了");
                    domY.setRest(deliverDao.getRestNumber(domY));

                }else if(status==5){
                    if(deliverRules==1){
                        domY.setChangeType("配送结束");
                        domY.setChange("减少了");
                        domY.setRest(deliverDao.getRestNumber(domY));
                    }else{
                        if(deliverRules==2){
                            domY.setChangeType("工作日送");
                        }else if(deliverRules==3){
                            domY.setChangeType("周末送");
                        }else if(deliverRules==4){
                            domY.setChangeType("隔天送");
                        }
                        domY.setChange("减少了");
                        domY.setRest(deliverDao.getRestNumber(domY));
                    }
                }
            }
        }

        newDomList.addAll(domTempT);
        newDomList.addAll(domTempY);

        //构造Result
        Result result = new Result();
        if (null != newDomList && newDomList.size() > 0) {
            result.setData(newDomList);
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.noRecord);
        }


        return result;

    }

    public Result numberCount(DeliverOrderModel deliverOrderModel){

        List<DeliverOrderModel> doms = deliverDao.getDeliverOrderCountByProc(deliverOrderModel);
        int numberCount = 0;
        for(DeliverOrderModel dom : doms){
            numberCount += dom.getNumber();
        }
        Result result = new Result();
        if(numberCount!=0){
            DeliverOrderModel dom = new DeliverOrderModel();
            dom.setNumber(numberCount);
            result.setData(dom);
            result.setStatus(Status.success);
        }else{
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    public Result exportChange(DeliverOrderModel deliverOrderModel,HttpServletResponse response){

        Result result =  getChangeOrder(deliverOrderModel);

        String[] header = {"区域","送奶员","客户姓名","客户手机","地址","商品","变动名称","变动","数量","余量"};

        Map map = new HashMap<String,String[][]>();
        List<DeliverOrderModel> domList = (List<DeliverOrderModel>)result.getData();

        if(domList.size()>0){
            String[][] arr = new String[domList.size()][header.length];
            for(int i=0;i<domList.size();i++){
                for(int j=0;j<header.length;j++){
                    if(j==0){
                        arr[i][j] = domList.get(i).getAreaName();
                    }else if(j==1){
                        arr[i][j] = domList.get(i).getUserName();
                    }else if(j==2){
                        arr[i][j] = domList.get(i).getCustName();
                    }else if(j==3){
                        arr[i][j] = domList.get(i).getPhone();
                    }else if(j==4){
                        arr[i][j] = domList.get(i).getAddress();
                    }else if(j==5){
                        arr[i][j] = domList.get(i).getProdName();
                    }else if(j==6){
                        arr[i][j] = domList.get(i).getChangeType();
                    }else if(j==7){
                        arr[i][j] = domList.get(i).getChange();
                    }else if(j==8){
                        arr[i][j] = String.valueOf(domList.get(i).getNumber());
                    }else if(j==9){
                        arr[i][j] = String.valueOf(domList.get(i).getRest());
                    }

                }

            }
            map.put("sheet1",arr);
            excelService.getExcel(header, map, response);
            result.setStatus(Status.success);

        }else{
            result.setStatus(Status.error);
        }



        return result;
    }

    public Result exportDeliver(DeliverOrderModel deliverOrderModel,HttpServletResponse response){

        Result result =  getDeliverOrder(deliverOrderModel);

        String[] header = {"客户编号","送奶员","客户姓名","联系电话","地址","商品名称","数量"};

        Map map = new HashMap<String,String[][]>();
        List<DeliverOrderModel> domList = (List<DeliverOrderModel>)result.getData();

        if(domList.size()>0){
            String[][] arr = new String[domList.size()][header.length];
            for(int i=0;i<domList.size();i++){
                for(int j=0;j<header.length;j++){
                    if(j==0){
                        arr[i][j] = domList.get(i).getCustName();
                    }else if(j==1){
                        arr[i][j] = domList.get(i).getUserName();
                    }else if(j==2){
                        arr[i][j] = domList.get(i).getCustName();
                    }else if(j==3){
                        arr[i][j] = domList.get(i).getPhone();
                    }else if(j==4){
                        arr[i][j] = domList.get(i).getAddress();
                    }else if(j==5){
                        arr[i][j] = domList.get(i).getProdName();
                    }else if(j==6){
                        arr[i][j] = String.valueOf(domList.get(i).getNumber());
                    }
                }
            }
            map.put("sheet1",arr);
            excelService.getExcel(header, map, response);
            result.setStatus(Status.success);

        }else{
            result.setStatus(Status.error);
        }



        return result;
    }




    public Result getTasteMilk(ReceiveMilkOrderModel receiveMilkOrderModel){

        List<ReceiveMilkOrderModel> tasteMilks = deliverDao.getTasteMilk(receiveMilkOrderModel);

        Result result = new Result();
        if (null != tasteMilks && tasteMilks.size() > 0) {
            result.setData(tasteMilks);
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    private static String getBefore(String today){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = format.parse(today);
            Calendar   cal   =   Calendar.getInstance();
            cal.setTime(date);
            int day = cal.get(Calendar.DATE);
            cal.set(Calendar.DATE,day-1);
            String yestoday = format.format(cal.getTime());
            return yestoday;
        }catch(Exception e){
            e.printStackTrace();
        }

        return "";


    }



}
