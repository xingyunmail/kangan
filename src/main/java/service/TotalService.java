package service;

import model.Result;
import model.total.TotalModel;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhaijinxu on 15-5-14.
 */
@Service
public interface TotalService {

    Result getData(TotalModel totalNodel);

    Result getExcel(TotalModel totalModel,HttpServletResponse response);
}
