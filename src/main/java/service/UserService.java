package service;

import model.Result;
import model.user.UserModel;

import java.util.List;

/**
 * Created by scar on 15/5/5.
 */

public interface UserService {

    Result info(UserModel userModel);

    public Result list(UserModel userModel);

    public Result updatestatus(UserModel userModel);

    public Result infoid(UserModel userModel);

    public Result updateinfo(UserModel userModel);

    public Result add(UserModel userModel);

    public Result login(UserModel userModel);

    public Result unamecheck(UserModel userModel);

    public Result updatepsw(UserModel userModel,UserModel user);

    public Result checkpsw(UserModel userModel,UserModel user);

    public Result getAllDelivers();
}
