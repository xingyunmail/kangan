package model.area;



import java.util.List;

/**
 * Created by zzq on 15-5-7.
 */
public class AreaModel {
    private String areaId;
    private String areaName;
    private String discrible;
    private String lineid;

    private List<AreaModel> areaList ;

    private String lineName;
    private int startNum;
    private int limit;

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
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

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDiscrible() {
        return discrible;
    }

    public void setDiscrible(String discrible) {
        this.discrible = discrible;
    }

    public String getLineid() {
        return lineid;
    }

    public void setLineid(String lineid) {
        this.lineid = lineid;
    }

    public List<AreaModel> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaModel> areaList) {
        this.areaList = areaList;
    }
}
