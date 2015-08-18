package DAO.complaints;

/**
 * Created by zzq on 15-5-8.
 */
public class ComplaintsDaoIMpl {
    public String getCompById(int type){
        String strSql="select * from complaints_info  where type = "+type;
        return strSql;
    }
}
