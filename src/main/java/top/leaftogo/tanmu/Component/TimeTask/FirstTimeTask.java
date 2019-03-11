package top.leaftogo.tanmu.Component.TimeTask;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.leaftogo.tanmu.Service.ControllerService.UserService;
@Slf4j
@Component
public class FirstTimeTask {

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(FirstTimeTask.class);

    //每天4点（24小时）执行
    @Scheduled(cron = "0 0 4 * * ? ")
    public void authRefresh() {
        try{

        }catch (Exception e){
            e.printStackTrace();
        }

    }





}
