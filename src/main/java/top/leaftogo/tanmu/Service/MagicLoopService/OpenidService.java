//package top.leaftogo.tanmu.Service.MagicLoopService;
//
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import top.leaftogo.tanmu.Service.ToolService.HttpClientRequestService;
//import top.leaftogo.tanmu.Service.ToolService.ToolService;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//@Slf4j
//public class OpenidService {
//
//    @Autowired
//    ToolService toolService;
//    @Autowired
//    HttpClientRequestService httpClientRequestService;
//
//    @Value("${magicloop.api.openid_check.url}")
//    private String openidcheck_url;
//
//    @Value("${magicloop.api.openid_redirect.url}")
//    public String openidRedirect_url;
//
//
//    public boolean checkOpenid(String openid){
//        Map<String,String> map = new HashMap<>();
//        map.put("openid",openid);
//        String back = httpClientRequestService.post(openidcheck_url,map,"utf8");
//        if(back == null){
//            log.error("openid验证请求返回为空");
//            return false;
//        }
//        JSONObject jsonObject = null;
//        try{
//            jsonObject = JSONObject.parseObject(back);
//        }catch (Exception e){
//            log.error("json格式转化出错,带转换的json为:"+back+"   openid:"+openid);
//            e.printStackTrace();
//            return false;
//        }
//        try{
//            if(jsonObject.containsKey("status")){
//                int status = jsonObject.getInteger("status");
//                if(status == 200) return true;
//                else if(status == -1){
//                    log.error("openid验证请求返回json，key为status的值为-1，back:"+back);
//                    return false;
//                }
//                else{
//                    log.error("向magicloop请求openid验证失败:返回json参数值与正常值不匹配,默认此openid为非法id，返回false,back:"+back);
//                    return false;
//                }
//            }else{
//                log.error("向magicloop请求openid验证失败：返回json格式异常,返回json为:"+back);
//                return false;
//            }
//        }catch (Exception e){
//            log.error("向magicloop请求openid验证失败:json解析出错,返回json为:"+back);
//            e.printStackTrace();
//            return false;
//        }
//    }
//}
