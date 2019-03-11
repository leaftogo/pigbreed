package top.leaftogo.tanmu.Service.WeiXinService;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.leaftogo.tanmu.Service.ToolService.ToolService;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class IdentityService {

    @Autowired
    ToolService toolService;

    @Value("${weixin.appID}")
    private String appID;

    @Value("${weixin.appsecret}")
    private String appsecret;

    @Value("${weixin.url.baseurl}")
    private String baseUrl;

    @Value("${weixin.url.picture_download}")

    private String pictureDownloadUrl;

    public String getAppID() {
        return appID;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getPictureDownloadUrl() {
        return pictureDownloadUrl;
    }
}
