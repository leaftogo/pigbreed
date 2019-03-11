package top.leaftogo.tanmu.Component.TimeTask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.leaftogo.tanmu.Service.ControllerService.UserService;
import top.leaftogo.tanmu.Service.ToolService.FileToolService;

@Slf4j
@Component
public class QuestionTimeTask {

    @Autowired
    FileToolService fileToolService;
    @Autowired
    UserService userService;


//服务启动时运行，设置10天的间隔日期
    @Scheduled(fixedDelay = 1000*60*60*24*10)
    public void questionMateListRefresh() {

    }

    @Scheduled(fixedDelay = 1000*60*60*24)
    public void setYml(){

    }

}
