package model.workorder;

import java.util.List;

/**
 * Created by zzq on 15-5-8.
 */
public class WorkOrdersModel {
    private String beginTime;
    private String endTime;
    private String  area;
    private String stats;
    private int type;
    private int excel;

    private int startNum;
    private int limit;

    private List<WorkOrderModel> list;

    public List<WorkOrderModel> getList() {
        return list;
    }

    public void setList(List<WorkOrderModel> list) {
        this.list = list;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getExcel() {
        return excel;
    }

    public void setExcel(int excel) {
        this.excel = excel;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
