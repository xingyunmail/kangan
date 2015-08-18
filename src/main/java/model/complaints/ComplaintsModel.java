package model.complaints;

import java.util.List;

/**
 * Created by zzq on 15-5-8.
 */
public class ComplaintsModel {
    private int id;
    private int type;
    private String name;
    private String compName;

    private List<ComplaintsModel> complaintsModelList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public List<ComplaintsModel> getComplaintsModelList() {
        return complaintsModelList;
    }

    public void setComplaintsModelList(List<ComplaintsModel> complaintsModelList) {
        this.complaintsModelList = complaintsModelList;
    }
}
