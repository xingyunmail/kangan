package service;

import model.Result;
import model.area.AreaModel;

/**
 * Created by zzq on 15-5-7.
 */
public interface AreaService {
    public Result getAreaList(AreaModel areaModel);

    public Result getArea(AreaModel areaModel);

    public Result addArea(AreaModel areaModel);

    public Result deleteArea(AreaModel areaModel);

    public Result updateArea(AreaModel areaModel);
    public Result getLineList(AreaModel areaModel);

}
