package service;

import model.Result;
import model.competence.ButtonInfoModel;
import model.competence.CompetenceGroupModel;
import model.competence.CompetenceGtoinfoModel;
import model.competence.CompetenceInfoModel;

/**
 * Created by yu on 15-5-21.
 */
public interface CompetenceService {

    public Result groupList();

    public Result infolist();

    public Result addGroup(CompetenceGtoinfoModel competenceGtoinfoModel);

    public Result groupinfolist(CompetenceGroupModel competenceGroupModel);

    public Result infogrouplist(CompetenceInfoModel competenceInfoModel);

    public Result addcompetence(CompetenceInfoModel competenceInfoModel);

    public Result getbutton(ButtonInfoModel buttonInfoModel);

    public Result buttonlist(int groupId);

    public Result updategroup(CompetenceGtoinfoModel competenceGtoinfoModel);

    public Result getinfobyid(CompetenceGtoinfoModel competenceGtoinfoModel);
}
