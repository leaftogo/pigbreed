package top.leaftogo.tanmu.Service.ControllerService;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.leaftogo.tanmu.Entity.UserInfoEntity;
import top.leaftogo.tanmu.Mapper.UserInfoMapper;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserInfoMapper userInfoMapper;

    public boolean register(String openid,String username,String user_pic_url){
        if(hasOpenid(openid)){
            return true;
        }else{
            userInfoMapper.add(new UserInfoEntity(openid,username,user_pic_url,"",0));
            return false;
        }
    }

    public boolean hasOpenid(String openid){
        List<UserInfoEntity> list = userInfoMapper.findEntityByOpenid(openid);
        if(list == null || list.size() == 0) return false;
        else return true;
    }


}
