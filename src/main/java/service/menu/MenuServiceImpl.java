package service.menu;

import DAO.MenuDao;
import model.Result;
import model.Status;
import model.menu.MenuModel;
import model.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.MenuService;

import java.util.List;

/**
 * Created by scar on 15/4/29.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public Result getMenus(UserModel userModel) {
        /*Result result = new Result();
        List<MenuModel> menuModelList = menuDao.getMenus();
        if (menuModelList.size() > 0) {
            result.setStatus(Status.success);
        } else {
            result.setStatus(Status.noRecord);
        }
        result.setData(menuModelList);
        return result;*/


        Result result = new Result();

        List<MenuModel> menuModelsTwo = menuDao.getMenusTwo(userModel);
        List<MenuModel> menuModelsOne = menuDao.getMenusOne(userModel);

        for (MenuModel menuModel:menuModelsTwo){
            menuModelsOne.add(menuModel);
        }

        if (menuModelsOne.size() > 0){
            result.setData(menuModelsOne);
            result.setStatus(Status.success);
        }else {
            result.setStatus(Status.noRecord);
        }

        return result;
    }
}
