package top.leaftogo.tanmu.Service.ToolService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class HttpClientRequestService {

    public String post(String url,Map<String,String> map,String encoding){
        try{
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if(map!=null){
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String body = null;
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, encoding);
            }
            EntityUtils.consume(entity);
            //释放链接
            response.close();
            return body;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String postcet(String url,Map<String,String> map,String encoding,String cookie){
        try{
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if(map!=null){
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
            httpPost.setHeader("Connection","keep-alive");
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
            httpPost.setHeader("Host","cache.neea.edu.cn");
            httpPost.setHeader("Origin","http://cet.neea.edu.cn");
            httpPost.setHeader("Referer","http://cet.neea.edu.cn/cet");
            if(cookie != null){
                httpPost.setHeader("Cookie",cookie);
            }
//            httpPost.setHeader("Cookie","UM_distinctid=168d736942d1c2-099eaaf32701b2-57b143a-144000-168d7369431529; language=1; Hm_lvt_dc1d69ab90346d48ee02f18510292577=1550990355,1551159622,1551175697,1551190432; Hm_lpvt_dc1d69ab90346d48ee02f18510292577=1551190435; BIGipServercache.neea.edu.cn_pool=3567306762.39455.0000");
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String body = null;
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, encoding);
            }
            EntityUtils.consume(entity);
            //释放链接
            response.close();
            return body;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getCet(String url,HttpClient client) {
        GetMethod getMethod = new GetMethod(url);
        getMethod.addRequestHeader("Content-Type","application/x-www-form-urlencoded");
        getMethod.addRequestHeader("Referer","http://cet.neea.edu.cn/cet/");
        int code = 0;
        try {
            Cookie[] cookies = client.getState().getCookies();
            String temp = "";
            for(Cookie cookie : cookies){
                temp += cookie.toString()+";";
            }
            if(temp.length()>0){
                getMethod.setRequestHeader("Cookie",temp);
            }
            code = client.executeMethod(getMethod);
            if (code == 200) {
                String res = getMethod.getResponseBodyAsString();
                return res;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String postCet(String url,Map<String,String> map,HttpClient client){
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Content-Type","application/x-www-form-urlencoded");
        postMethod.addRequestHeader("Host","cache.neea.edu.cn");
        postMethod.addRequestHeader("Origin","http://cet.neea.edu.cn");
        postMethod.addRequestHeader("Referer","http://cet.neea.edu.cn/cet/");
        postMethod.addRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
        if(map != null){
            Set<String> set = map.keySet();
            for(String key : set){
                postMethod.addParameter(key,map.get(key));
            }
        }
        try {
            Cookie[] cookies = client.getState().getCookies();
            String temp = "";
            for(Cookie cookie : cookies){
                temp += cookie.toString()+";";
            }
            if(temp.length()>0){
                postMethod.setRequestHeader("Cookie",temp);
            }
            int code = client.executeMethod(postMethod);
            if (code == 200) {
                String res = postMethod.getResponseBodyAsString();
                return res;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }
}
