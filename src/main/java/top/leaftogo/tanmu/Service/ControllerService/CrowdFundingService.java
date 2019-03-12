package top.leaftogo.tanmu.Service.ControllerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.leaftogo.tanmu.Entity.CrowdFundingInfoEntity;
import top.leaftogo.tanmu.Mapper.CrowdFundingInfoMapper;
import top.leaftogo.tanmu.Mapper.CrowdFundingPicMapper;

@Slf4j
@Service
public class CrowdFundingService {

    @Autowired
    CrowdFundingInfoMapper crowdFundingInfoMapper;
    @Autowired
    CrowdFundingPicMapper crowdFundingPicMapper;

    public long raiseCrowdFunding(int user_id,String username,String title,String description,float target,String breed_cycle,String pig_type,String weight_minimum_promise){
        long timestamp = System.currentTimeMillis();
        try{
            CrowdFundingInfoEntity entity = new CrowdFundingInfoEntity(user_id,username,timestamp,title,description,target,0, (float) 0,0,breed_cycle,pig_type,weight_minimum_promise);
            crowdFundingInfoMapper.add(entity);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return timestamp;
    }

    public CrowdFundingInfoEntity findEntityByTimeStampAndByUserId(long timestamp,String user_id){
        CrowdFundingInfoEntity entity = null;
        try{
            entity = crowdFundingInfoMapper.findEntityByTimestampAndByUserId(timestamp,user_id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询众筹错误:没有指定用户，指定发起时间的众筹");
            return null;
        }
        return entity;
    }
}
