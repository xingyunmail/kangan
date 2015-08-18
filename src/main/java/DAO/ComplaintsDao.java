package DAO;

import DAO.complaints.ComplaintsDaoIMpl;
import model.complaints.ComplaintsModel;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zzq on 15-5-8.
 */
@Repository
public interface ComplaintsDao {
    @Select("select * from complaints_info GROUP BY type")
    public List<ComplaintsModel> getCompType();
    @SelectProvider(type = ComplaintsDaoIMpl.class, method = "getCompById")
    public List<ComplaintsModel> getCompById(int type);
}
