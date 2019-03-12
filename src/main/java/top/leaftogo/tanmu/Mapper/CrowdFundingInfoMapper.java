package top.leaftogo.tanmu.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import top.leaftogo.tanmu.Entity.CrowdFundingInfoEntity;

@Mapper
public interface CrowdFundingInfoMapper {


    @Insert("insert into crowd_funding_info (user_id,username,timestamp,title,description,target,person_support_amount,money_gain,like_amount,breed_cycle,pig_type,weight_minimum_promise) values (#{user_id},#{user_name},#{timestamp},#{title},#{description},#{target},#{person_support_amount},#{money_gain},#{like_amount},#{breed_cycle},#{pig_type},#{weight_minimum_promise})")
    @Options(useGeneratedKeys = true,keyProperty ="id",keyColumn = "id")
    void add(CrowdFundingInfoEntity entity);

    @Select("select * from crowd_funding_info where timestamp = #{timestamp} and user_id = #{user_id}")
    CrowdFundingInfoEntity findEntityByTimestampAndByUserId(long timestamp,String user_id);




}
