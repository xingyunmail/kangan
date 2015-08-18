package service.competence;

import DAO.CompetenceDao;
import model.Result;
import model.Status;
import model.competence.ButtonInfoModel;
import model.competence.CompetenceGroupModel;
import model.competence.CompetenceGtoinfoModel;
import model.competence.CompetenceInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.CompetenceService;

import java.util.List;

/**
 * Created by yu on 15-5-21.
 */
@Service
public class CompetenceServiceImpl implements CompetenceService {

    @Autowired
    private CompetenceDao competenceDao;

    @Override
    public Result groupList() {

        Result result = new Result();
        List<CompetenceGroupModel> competenceGroupModels = competenceDao.groupList();
        if (competenceGroupModels.size() > 0){
            result.setStatus(Status.success);
            result.setData(competenceGroupModels);
        }else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result infolist() {

        Result result = new Result();
        List<CompetenceInfoModel> competenceInfoModels = competenceDao.infolist();
        if (competenceInfoModels.size() > 0){
            result.setStatus(Status.success);
            result.setData(competenceInfoModels);
        }else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    @Transactional
    public Result addGroup(CompetenceGtoinfoModel cGtoinfoModel) {

        Result result = new Result();
        CompetenceGroupModel groupModel = new CompetenceGroupModel();
        groupModel.setName(cGtoinfoModel.getName());
        competenceDao.addGroup(groupModel);

        if (groupModel.getId() > 0){
            int gid = groupModel.getId();
            String[] infoArray = cGtoinfoModel.getInfoIdArray().split(",");
            CompetenceGtoinfoModel gtoinfoModel = new CompetenceGtoinfoModel();
            gtoinfoModel.setGroupId(gid);
            int j = 0;
            for (int i = 0;i < infoArray.length;i++){
                gtoinfoModel.setInfoId(Integer.parseInt(infoArray[i]));
                competenceDao.groupToInfo(gtoinfoModel);
                j = j + 1;
            }
            if (j == infoArray.length){
                result.setStatus(Status.success);
            }else {
                result.setStatus(Status.error);
            }
        }else {
            result.setStatus(Status.error);
        }

        return result;
    }

    @Override
    public Result groupinfolist(CompetenceGroupModel competenceGroupModel) {

        Result result = new Result();

        List<CompetenceGroupModel> competenceGroupModels = competenceDao.groupinfolist(competenceGroupModel);
        int count = competenceDao.groupinfolistcount();

        if (competenceGroupModels.size() > 0){
            result.setData(competenceGroupModels);
            result.setCount(count);
            result.setStatus(Status.success);
        }else {
            result.setStatus(Status.noRecord);
        }

        return result;
    }

    @Override
    public Result infogrouplist(CompetenceInfoModel competenceInfoModel) {

        Result result = new Result();

        List<CompetenceInfoModel> competenceInfoModels = competenceDao.infogrouplist(competenceInfoModel);
        int count = competenceDao.infogrouplistcount();

        if (competenceInfoModels.size() > 0){
            result.setStatus(Status.success);
            result.setData(competenceInfoModels);
            result.setCount(count);
        }else {
            result.setStatus(Status.noRecord);
        }

        return result;
    }

    @Override
    @Transactional
    public Result addcompetence(CompetenceInfoModel competenceInfoModel) {
        Result result = new Result();

        ButtonInfoModel buttonInfoModel = competenceDao.buttoninfo(competenceInfoModel);

        if (buttonInfoModel != null){
            competenceInfoModel.setName(buttonInfoModel.getName());
            competenceInfoModel.setCompetenceId(buttonInfoModel.getCompetenceId());
            competenceInfoModel.setMenuId(buttonInfoModel.getMenuId());
            competenceDao.addInfo(competenceInfoModel);

            if (competenceInfoModel.getId() > 0){
                int infoid = competenceInfoModel.getId();
                String[] groupArray = competenceInfoModel.getGroupArray().split(",");
                CompetenceGtoinfoModel gtoinfoModel = new CompetenceGtoinfoModel();
                gtoinfoModel.setInfoId(infoid);
                int j = 0;
                for (int i = 0;i < groupArray.length;i++){
                    gtoinfoModel.setGroupId(Integer.parseInt(groupArray[i]));
                    competenceDao.groupToInfo(gtoinfoModel);
                    j = j + 1;
                }
                if (j == groupArray.length){
                    result.setStatus(Status.success);
                }else {
                    result.setStatus(Status.error);
                }
            }else {
                result.setStatus(Status.error);
            }
        }else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result getbutton(ButtonInfoModel buttonInfoModel) {

        Result result = new Result();
        List<ButtonInfoModel> buttonInfoModels = competenceDao.getbutton(buttonInfoModel);
        if (buttonInfoModels.size() > 0){
            result.setStatus(Status.success);
            result.setData(buttonInfoModels);
        }else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result buttonlist(int groupId) {

        Result result = new Result();
        List<CompetenceInfoModel> competenceInfoModels = competenceDao.buttonlist(groupId);
        if (competenceInfoModels.size() > 0){
            result.setStatus(Status.success);
            result.setData(competenceInfoModels);
        }else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    @Transactional
    public Result updategroup(CompetenceGtoinfoModel cGtoinfoModel) {

        Result result = new Result();

        int gid = cGtoinfoModel.getGroupId();

        if (gid > 0){
            competenceDao.deleteinfo(gid);
            String[] infoArray = cGtoinfoModel.getInfoIdArray().split(",");
            CompetenceGtoinfoModel gtoinfoModel = new CompetenceGtoinfoModel();
            gtoinfoModel.setGroupId(gid);
            int j = 0;
            for (int i = 0;i < infoArray.length;i++){
                gtoinfoModel.setInfoId(Integer.parseInt(infoArray[i]));
                competenceDao.groupToInfo(gtoinfoModel);
                j = j + 1;
            }
            if (j == infoArray.length){
                result.setStatus(Status.success);
            }else {
                result.setStatus(Status.error);
            }
        }else {
            result.setStatus(Status.error);
        }

        return result;
    }

    @Override
    public Result getinfobyid(CompetenceGtoinfoModel competenceGtoinfoModel) {

        Result result = new Result();
        List<CompetenceGtoinfoModel> competenceGtoinfoModels = competenceDao.getinfobyid(competenceGtoinfoModel);
        if (competenceGtoinfoModels.size() > 0){
            result.setStatus(Status.success);
            result.setData(competenceGtoinfoModels);
        }else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

}
