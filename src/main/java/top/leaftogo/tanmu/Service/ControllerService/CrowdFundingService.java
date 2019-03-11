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

    public long raiseCrowdFunding(int user_id,String username,String title,String description,float target){
        long timestamp = System.currentTimeMillis();
        try{
            CrowdFundingInfoEntity entity = new CrowdFundingInfoEntity(user_id,username,timestamp,title,description,target,0, (float) 0,0);
            crowdFundingInfoMapper.add(entity);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return timestamp;
    }

    public CrowdFundingInfoEntity findEntityByTimeStamp(long timestamp){
        CrowdFundingInfoEntity entity = null;
        try{

        }
    }
}
