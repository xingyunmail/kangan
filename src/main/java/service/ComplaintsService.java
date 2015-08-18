package service;

import model.Result;

/**
 * Created by zzq on 15-5-8.
 */
public interface ComplaintsService {
    public Result getCompList();

    public Result getCompById(int type);
}
