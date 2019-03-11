package top.leaftogo.tanmu.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import top.leaftogo.tanmu.Entity.UserPayEntity;

@Mapper
public interface UserPayMapper {

    @Insert("insert into user_pay (user_id,timestamp,money,crowd_funding_id) values (#{user_id},#{timestamp},#{money},#{crowd_funding_id})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void add(UserPayEntity entity);

}
