package DAO;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * Created by scar on 15/5/18.
 */
@Repository
public interface Test {
    @Insert("insert into production_info (detiallId,prodId,prodDate,areaId,deliverId,numbers) values(#{detiallId},#{prodId},#{prodDate},#{areaId},#{deliverId},#{numbers})")
    void test(String detiallId, String prodId, String prodDate, String areaId, String deliverId, String numbers);
}
