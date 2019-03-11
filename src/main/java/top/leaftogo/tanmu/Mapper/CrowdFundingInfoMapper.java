package top.leaftogo.tanmu.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import top.leaftogo.tanmu.Entity.CrowdFundingInfoEntity;

@Mapper
public interface CrowdFundingInfoMapper {


    @Insert("insert into crowd_funding_info (user_id,username,timestamp,title,description,target,person_support_amount,money_gain) values (#{user_id},#{user_name},#{timestamp},#{title},#{description},#{target},#{person_support_amount},#{money_gain})")
    @Options(useGeneratedKeys = true,keyProperty ="id",keyColumn = "id")
    void add(CrowdFundingInfoEntity entity);



}
