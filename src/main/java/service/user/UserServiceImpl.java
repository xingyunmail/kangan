package service.user;

import DAO.UserDao;
import model.Result;
import model.Status;
import model.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserService;

import java.util.List;

/**
 * Created by scar on 15/5/5.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Result info(UserModel userModel) {

        Result result = new Result();
        List<UserModel> userModelList = userDao.info(userModel);
        if (userModelList.size() == 1) {
            result.setStatus(Status.success);
            result.setData(userModelList.get(0));
        } else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result list(UserModel userModel) {

        Result result = new Result();
        List<UserModel> userModelList = userDao.list(userModel);
        int count = userDao.listcount(userModel);
        if (userModelList.size() > 0){
            result.setStatus(Status.success);
            result.setData(userModelList);
            result.setCount(count);
        }else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result updatestatus(UserModel userModel) {

        Result result = new Result();
        int status = userDao.updatestatus(userModel);
        result.setStatus((status > 0) ? Status.success : Status.error);
        return result;
    }

    @Override
    public Result infoid(UserModel userModel) {

        Result result = new Result();
        UserModel userModelNew = userDao.infoid(userModel);
        if (userModelNew != null){
            result.setData(userModelNew);
            result.setStatus(Status.success);
        }else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result updateinfo(UserModel userModel) {

        Result result = new Result();
        int status = userDao.updateinfo(userModel);
        result.setStatus((status > 0) ? Status.success : Status.error);
        return result;
    }

    @Override
    public Result add(UserModel userModel) {

        Result result = new Result();
        int status = userDao.add(userModel);
        result.setStatus((status > 0) ? Status.success : Status.error);
        return result;
    }

    @Override
    public Result login(UserModel userModel) {

        Result result = new Result();

        UserModel user = userDao.login(userModel);
        if (user != null){
            result.setStatus(Status.success);
            result.setData(user);
        }else {
            result.setStatus(Status.error);
        }

        return result;
    }

    @Override
    public Result unamecheck(UserModel userModel) {

        Result result = new Result();
        List<UserModel> userlist = userDao.userCheck(userModel);

        if (userlist.size() > 0){
            result.setStatus(Status.success);
        }else {
            result.setStatus(Status.noRecord);
        }
        return result;
    }

    @Override
    public Result updatepsw(UserModel userModel,UserModel user) {
        Result result = new Result();
        int status = userDao.updatepsw(userModel.getPassword(),user.getUserId());
        result.setStatus((status > 0) ? Status.success : Status.error);
        return result;
    }

    @Override
    public Result checkpsw(UserModel userModel, UserModel user) {
        Result result = new Result();

        if (user != null){
            if (user.getPassword().equals(userModel.getPassword())){
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
    public Result getAllDelivers()
    {
        Result result = new Result();

        List<UserModel> names = userDao.getAllDelivers();

        result.setData(names);

        return result;
    }
}
