package model.competence;

/**
 * Created by yu on 15-5-22.
 */
public class CompetenceGtoinfoModel {

    private int id;
    private int groupId;
    private int infoId;
    private String createtime;
    private int valid;

    private String name;
    private String infoIdArray;

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfoIdArray() {
        return infoIdArray;
    }

    public void setInfoIdArray(String infoIdArray) {
        this.infoIdArray = infoIdArray;
    }
}
