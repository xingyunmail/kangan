package service;

import model.Result;
import model.user.UserModel;

/**
 * Created by scar on 15/4/29.
 */
public interface MenuService {
    Result getMenus(UserModel userModel);
}
