package model.competence;

import model.user.UserModel;

import java.util.List;

/**
 * Created by yu on 15-5-21.
 */
public class CompetenceGroupModel {

    private int id;
    private String name;
    private String createtime;
    private int valid;

    private List<CompetenceInfoModel> competenceInfoModels;
    private List<UserModel> userModels;

    private int startNum;
    private int limit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public List<CompetenceInfoModel> getCompetenceInfoModels() {
        return competenceInfoModels;
    }

    public void setCompetenceInfoModels(List<CompetenceInfoModel> competenceInfoModels) {
        this.competenceInfoModels = competenceInfoModels;
    }

    public List<UserModel> getUserModels() {
        return userModels;
    }

    public void setUserModels(List<UserModel> userModels) {
        this.userModels = userModels;
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
