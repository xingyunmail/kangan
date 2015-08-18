package DAO;

import DAO.total.TotalDaoImpl;
import model.total.TotalModel;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhaijinxu on 15-5-14.
 */
@Repository
public interface TotalDao {

    @SelectProvider(type = TotalDaoImpl.class,method = "getDate")
    List<TotalModel> getData(TotalModel totalModel);
}


