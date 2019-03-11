package top.leaftogo.tanmu.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import top.leaftogo.tanmu.Entity.UserLikeEntity;

@Mapper
public interface UserLikeMapper {

    @Insert("insert into user_like (user_give_id,user_receive_id,timestamp) values (#{user_give_id},#{user_receive_id},#{timestamp})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void add(UserLikeEntity entity);
}
