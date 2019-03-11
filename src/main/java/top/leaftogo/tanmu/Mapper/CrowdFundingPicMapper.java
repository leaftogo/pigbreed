package top.leaftogo.tanmu.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import top.leaftogo.tanmu.Entity.CrowdFundingPicEntity;

@Mapper
public interface CrowdFundingPicMapper {

    @Insert("insert into crowd_funding_pic (crowd_funding_id,pic_path,type) values (#{crowd_funding_id},#{pic_path},#{type})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void add(CrowdFundingPicEntity entity);





}
