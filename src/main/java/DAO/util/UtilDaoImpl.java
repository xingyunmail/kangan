package DAO.util;

import model.dictionary.DictModel;

/**
 * Created by sunhao on 15/5/4.
 */
public class UtilDaoImpl {

    public String getDictInfo(DictModel dictModel)
    {
        String sql = "select itemKey,itemValue,itemDiscrible,valid from dic_item where 1=1 ";
        String sqlWhere = "";
        if(null != dictModel.getItemKey() && "" != dictModel.getItemKey())
        {
            sqlWhere = " and itemKey = #{itemKey}";
        }
        if(null != dictModel.getValid() && "" != dictModel.getValid())
        {
            sqlWhere = " and valid = #{valid}";
        }


        sql = sql+sqlWhere;

        return sql;
    }
}
