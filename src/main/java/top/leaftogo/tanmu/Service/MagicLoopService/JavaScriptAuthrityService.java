//package top.leaftogo.tanmu.Service.MagicLoopService;
//
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import top.leaftogo.tanmu.Service.ToolService.HttpClientRequestService;
//import top.leaftogo.tanmu.Service.ToolService.ToolService;
//
//import java.util.*;
//
//@Slf4j
//@Service
//public class JavaScriptAuthrityService {
//
//    @Value("${project.url}")
//    private String project_url;
//
//    @Value("${project.name}")
//    private String project_name;
//
//    @Value("${magicloop.api.js_ticket.url}")
//    private String js_ticket_url;
//
//    @Value("${magicloop.api.js_ticket.params.secret_key}")
//    private String params_secret_key;
//
//    @Value("${magicloop.api.js_ticket.params.secret_value}")
//    private String params_secret_value;
//
//    @Value("${magicloop.api.js_ticket.params.timestamp_key}")
//    private String params_timestamp_key;
//
//    @Value("${magicloop.api.js_ticket.params.timestamp_value}")
//    private String params_timestamp_value;
//
//    @Value("${magicloop.api.js_ticket.params.string_key}")
//    private String params_string_key;
//
//    @Value("${magicloop.api.js_ticket.params.string_value}")
//    private String params_string_value;
//
//    @Value("${magicloop.api.js_ticket.params.nonceStr_key}")
//    private String params_nonceStr_key;
//
//    @Value("${magicloop.api.js_ticket.params.nonceStr_value}")
//    private String params_nonceStr_value;
//
//    private String fullUrl;
//
//    public Map<String,String> map = null;
//
//    private String ticketUrl;
//    @Autowired
//    ToolService toolService;
//    @Autowired
//    HttpClientRequestService httpClientRequestService;
//
//    public void init(){
//        synchronized (this){
//            fullUrl = project_url+"/"+project_name;
//            if(map == null){
//                map = new HashMap<>();
//                map.put(params_secret_key,params_secret_value);
//                map.put(params_timestamp_key,params_timestamp_value);
//                map.put(params_string_key,params_string_value);
//                map.put(params_nonceStr_key,params_nonceStr_value);
//                log.info("magicLoop请求:js_ticket:初始化，设置接口默认参数，当前参数如下:"+toolService.toJSONString(map));
//            }
//        }
//    }
//
//    public Map<String,String> getJsSign(String page_url){
//        String ticket = getJsApiTicket();
//        if(ticket == null || ticket.length() == 0){
//            return null;
//        }else{
//            return signJavaScript(ticket,page_url);
//        }
//    }
//
//    public String getJsApiTicket(){
//        String back = httpClientRequestService.post(js_ticket_url,map,"UTF-8");
//        if(back.equals("false")){
//            back = httpClientRequestService.post(js_ticket_url,map,"UTF-8");
//            if(back.equals("false")){
//                log.error("js签名请求失败:"+back);
//                return null;
//            }
//        }
//        if(back.length() > 600){
//            log.error("js签名请求失败:"+back);
//            return null;
//        }
//        JSONObject jsonObject = JSONObject.parseObject(back);
//        JSONObject jsonObject1 = null;
//        if(jsonObject.containsKey("data")){
//            jsonObject1 = jsonObject.getJSONObject("data");
//        }else{
//            log.error("js签名请求失败:"+back);
//            return null;
//        }
//        if(jsonObject1.containsKey("ticket")) return jsonObject1.getString("ticket");
//        else{
//            log.error("js签名请求失败:"+back);
//            return null;
//        }
//    }
//
//    //page_url形式："/page/page"
//    //以"/"开始
//    private Map<String,String> signJavaScript(String jsapi_ticket, String page_url){
//        SortedMap<String,String> parameters = new TreeMap<String,String>();
//        String noncestr = toolService.getRandomString();
//        String timestamp = String.valueOf(System.currentTimeMillis()/1000);
//        parameters.put("jsapi_ticket",jsapi_ticket);
//        parameters.put("noncestr",noncestr);
//        parameters.put("timestamp",timestamp);
//        parameters.put("url",page_url);
//        StringBuilder builder = new StringBuilder();
//        Set set = parameters.entrySet();
//        Iterator it = set.iterator();
//        while(it.hasNext()){
//            Map.Entry entry = (Map.Entry) it.next();
//            String key = (String) entry.getKey();
//            String value = (String) entry.getValue();
//            builder.append(key);
//            builder.append("=");
//            builder.append(value);
//            builder.append("&");
//        }
//        String str = builder.toString();
//        str = str.substring(0,str.length()-1);
//        String signature = DigestUtils.sha1Hex(str);
//        Map<String,String> map = new HashMap<>();
//        map.put("timestamp",timestamp);
//        map.put("nonceStr",noncestr);
//        map.put("signature",signature);
//        return map;
//    }
//}
