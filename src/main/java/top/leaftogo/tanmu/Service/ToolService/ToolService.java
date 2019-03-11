package top.leaftogo.tanmu.Service.ToolService;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import top.leaftogo.tanmu.Service.RedisService;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ToolService {
    private static final Logger logger = LoggerFactory.getLogger(ToolService.class);
    private static int SEND_REQUEST_TIME_OUT = 30000;
    private static int READ_TIME_OUT = 30000;
    static String localUrl;
    String cookieTemp = "";

    @Autowired
    RedisService redisService;

    public synchronized String getCookieTemp(){
        return cookieTemp;
    }

    public void setCookieTemp(String cookie){
        if(!cookieTemp.equals(cookie)){
            synchronized (cookieTemp){
                if(!cookieTemp.equals(cookie)){
                    cookieTemp = cookie;
                }
            }
        }
    }

    static{
        localUrl = ClassUtils.getDefaultClassLoader().getResource("").getPath();
    }

    public static Pattern num_pattern = Pattern.compile("^[0-9]*$");

    public String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
    public long getTimeStamp(){
        return System.currentTimeMillis();
    }

    public static boolean ifNum(String str){
        if(num_pattern.matcher(str).matches()) return true;
        else return false;
    }
    public String getIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }

        if(ipAddress!=null && ipAddress.length()>15){
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    private String tooString(String url,Map<String,String> map){
        Set<String> set = map.keySet();
        int i=0;
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        builder.append("?");
        for(String key: set){
            i++;
            builder.append(key);
            builder.append("=");
            builder.append(map.get(key));
            if(i != set.size()){
                builder.append("&");
            }
        }
        return builder.toString();
    }

    public String getBasic(String urlStr) {
        int SEND_REQUEST_TIME_OUT = 200000;
        int READ_TIME_OUT = 200000;
        boolean isDoInput = false;
        boolean ifsuccess = true;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(SEND_REQUEST_TIME_OUT);
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() >= 300) {
                ifsuccess = false;
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (ConnectException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (SocketException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (IOException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (Exception e) {
            e.printStackTrace();
            ifsuccess = false;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
        }
        if(!ifsuccess) return "false";
        return resultBuffer.toString();
    }

    public String getBasicData(String urlStr) {
        int SEND_REQUEST_TIME_OUT = 200000;
        int READ_TIME_OUT = 200000;
        boolean isDoInput = false;
        boolean ifsuccess = true;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Connection","keep-alive");
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Host","cache.neea.edu.cn");
            httpURLConnection.setRequestProperty("Referer","http://cet.neea.edu.cn/cet/");
            httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
            httpURLConnection.setConnectTimeout(SEND_REQUEST_TIME_OUT);
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.connect();
            String cookie1 = httpURLConnection.getHeaderField("Set-Cookie");
            if(cookie1 != null){
                redisService.setString("cetsearch:cookie",cookie1,300);
            }
            if (httpURLConnection.getResponseCode() >= 300) {
                ifsuccess = false;
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (ConnectException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (SocketException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (IOException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (Exception e) {
            e.printStackTrace();
            ifsuccess = false;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
        }
        if(!ifsuccess) return "false";
        return resultBuffer.toString();
    }



    public String getBasicDataForSafe(String urlStr,String openid) {
        int SEND_REQUEST_TIME_OUT = 200000;
        int READ_TIME_OUT = 200000;
        boolean isDoInput = false;
        boolean ifsuccess = true;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Connection","keep-alive");
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Host","cache.neea.edu.cn");
            httpURLConnection.setRequestProperty("Referer","http://cet.neea.edu.cn/cet/");
            httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");

            httpURLConnection.setConnectTimeout(SEND_REQUEST_TIME_OUT);
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.connect();
            String cookie1 = httpURLConnection.getHeaderField("Set-Cookie");
            if(cookie1 != null){
                redisService.setString(openid+":cookie",cookie1,300);
            }
            if (httpURLConnection.getResponseCode() >= 300) {
                ifsuccess = false;
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (ConnectException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (SocketException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (IOException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (Exception e) {
            e.printStackTrace();
            ifsuccess = false;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
        }
        if(!ifsuccess) return "false";
        return resultBuffer.toString();
    }

    //将Object类对象转化成json字符串
    //请确保被转换对象所有的类（本身，和本身的各种值）必须为以下几种中的一个：
    //int String ArrayList HashMap Bean（Bean属性的类也必须包含在本行的五种情况之中）
    //非容器类的对象属性如果为空，将会在转化中被忽略
    public String toJSONString(Object object) {
        try{
            StringBuffer buffer = new StringBuffer();
            if(object instanceof ArrayList){
                buffer.append("[");
                ArrayList list = (ArrayList) object;
                for(int i=0;i<list.size();i++){
                    buffer.append(toJSONString(list.get(i)));
                    if(i != list.size()-1) buffer.append(",");
                }
                buffer.append("]");
            }else if(object instanceof String){
                buffer.append("\"");
                buffer.append((String)object);
                buffer.append("\"");
            }else if(object instanceof HashMap){
                HashMap map = (HashMap) object;
                Set set = map.keySet();
                buffer.append("{");
                int i=0;
                for(Object object1 : set){
                    i++;
                    buffer.append(toJSONString(object1));
                    buffer.append(":");
                    buffer.append(toJSONString(map.get(object1)));
                    if(i != set.size()) buffer.append(",");
                }
                buffer.append("}");
            }else if(object instanceof Integer){
                buffer.append("\"");
                buffer.append(String.valueOf((Integer)object));
                buffer.append("\"");
            }else{
                buffer.append("{");
                Field[] fields = object.getClass().getDeclaredFields();
                for(int i=0;i<fields.length;i++){
                    fields[i].setAccessible(true);
                    if(fields[i].get(object) == null) continue; //对象属性为空，忽略转化
                    buffer.append("\"");
                    buffer.append(fields[i].getName());
                    buffer.append("\"");
                    buffer.append(":");
                    fields[i].setAccessible(true);
                    buffer.append(toJSONString(fields[i].get(object)));
                    if(i != fields.length-1) buffer.append(",");
                }
                buffer.append("}");
            }
            return buffer.toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "{\"error\":\"server method error\"}";
        }
    }

    public String toUrl(String urlStr,Map<String,String> map,String method) {
        int SEND_REQUEST_TIME_OUT = 10000;
        int READ_TIME_OUT = 10000;
        boolean isDoInput = false;
        if(map != null){
            urlStr = tooString(urlStr,map);
        }
        boolean ifsuccess = true;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(SEND_REQUEST_TIME_OUT);
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() >= 300) {
                ifsuccess = false;
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (ConnectException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (SocketException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (IOException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (Exception e) {
            e.printStackTrace();
            ifsuccess = false;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
        }
        if(!ifsuccess) return "false";
        return resultBuffer.toString();
    }

    public String bodyWriteRequest(String urlStr,Map<String,String> map,String method) {
        String body = "";
        boolean isDoInput = false;
        if(map != null){
            body = toBody(map);
            isDoInput = true;
        }
        boolean ifsuccess = true;
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(body.length()));
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(SEND_REQUEST_TIME_OUT);
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.connect();
            if(isDoInput){
                outputStream = httpURLConnection.getOutputStream();
                outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(body);
                outputStreamWriter.flush();
            }
            if (httpURLConnection.getResponseCode() >= 300) {
                ifsuccess = false;
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ifsuccess = false;
        } finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
        }
        if(!ifsuccess) return "false";
        return resultBuffer.toString();
    }

    public String postCet(String urlStr,Map<String,String> map,String method,HttpSession session) {
        String body = "";
        boolean isDoInput = false;
        if(map != null){
            body = toBody(map);
            isDoInput = true;
        }
        boolean ifsuccess = true;
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Connection","keep-alive");
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(body.length()));
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            String cookie = redisService.getString(session.getAttribute("openid")+":cookie");
            if(cookie == null){

            }else{
                logger.info(session.getId()+"注入已写入的cookie:"+cookie);
                httpURLConnection.setRequestProperty("Cookie",cookie);
            }
            httpURLConnection.setRequestProperty(":authority","suse.xsdhy.com");
            httpURLConnection.setRequestProperty(":path","/index/cet/getGrades");
            httpURLConnection.setRequestProperty(":scheme","https");
            httpURLConnection.setRequestProperty("origin","https://suse.xsdhy.com");
            httpURLConnection.setRequestProperty("referer","https://suse.xsdhy.com/cet");
            httpURLConnection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
            httpURLConnection.setRequestProperty("x-requested-with","XMLHttpRequest");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(SEND_REQUEST_TIME_OUT);
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.connect();
            if(isDoInput){
                outputStream = httpURLConnection.getOutputStream();
                outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(body);
                outputStreamWriter.flush();
            }
            String cookie1 = httpURLConnection.getHeaderField("Set-Cookie");
            if(cookie1 != null){
                session.setAttribute("cookie","language=1;"+cookie1);
            }
            if (httpURLConnection.getResponseCode() >= 300) {
                ifsuccess = false;
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ifsuccess = false;
        } finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
        }
        if(!ifsuccess) return "false";
        return resultBuffer.toString();
    }

    public String postCetForNoCode(String urlStr,Map<String,String> map,String method) {
        String body = "";
        boolean isDoInput = false;
        if(map != null){
            body = toBody(map);
            isDoInput = true;
        }
        boolean ifsuccess = true;
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Connection","keep-alive");
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(body.length()));
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            String cookie = redisService.getString("cetsearch:cookie");
            if(cookie == null){

            }else{
                httpURLConnection.setRequestProperty("Cookie",cookie);
            }
            httpURLConnection.setRequestProperty("origin","https://suse.xsdhy.com");
            httpURLConnection.setRequestProperty("referer","https://suse.xsdhy.com/cet");
            httpURLConnection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
            httpURLConnection.setRequestProperty("x-requested-with","XMLHttpRequest");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(SEND_REQUEST_TIME_OUT);
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.connect();
            if(isDoInput){
                outputStream = httpURLConnection.getOutputStream();
                outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(body);
                outputStreamWriter.flush();
            }
            if (httpURLConnection.getResponseCode() >= 300) {
                ifsuccess = false;
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ifsuccess = false;
        } finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
        }
        if(!ifsuccess) return "false";
        return resultBuffer.toString();
    }

    public String postCetForSafe(String urlStr,Map<String,String> map,String method,String openid) {
        String body = "";
        boolean isDoInput = false;
        if(map != null){
            body = toBody(map);
            isDoInput = true;
        }
        boolean ifsuccess = true;
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Connection","keep-alive");
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(body.length()));
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            String cookie = redisService.getString(openid+":cookie");
            if(cookie == null){

            }else{
                httpURLConnection.setRequestProperty("Cookie",cookie);
            }
            httpURLConnection.setRequestProperty("origin","https://suse.xsdhy.com");
            httpURLConnection.setRequestProperty("referer","https://suse.xsdhy.com/cet");
            httpURLConnection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
            httpURLConnection.setRequestProperty("x-requested-with","XMLHttpRequest");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(SEND_REQUEST_TIME_OUT);
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.connect();
            if(isDoInput){
                outputStream = httpURLConnection.getOutputStream();
                outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(body);
                outputStreamWriter.flush();
            }
            if (httpURLConnection.getResponseCode() >= 300) {
                ifsuccess = false;
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ifsuccess = false;
        } finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
        }
        if(!ifsuccess) return "false";
        return resultBuffer.toString();
    }

    public String bodyWriteObjectRequest(String urlStr,String data,String method) {
        String body = "";
        boolean isDoInput = false;
        if(data != null){
            body = data;
            isDoInput = true;
        }
        boolean ifsuccess = true;
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(body.length()));
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(SEND_REQUEST_TIME_OUT);
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.connect();
            if(isDoInput){
                outputStream = httpURLConnection.getOutputStream();
                outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(body);
                outputStreamWriter.flush();
            }
            if (httpURLConnection.getResponseCode() >= 300) {
                ifsuccess = false;
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (ConnectException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (SocketException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (IOException e) {
            e.printStackTrace();
            ifsuccess = false;
        } catch (Exception e) {
            e.printStackTrace();
            ifsuccess = false;
        } finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                ifsuccess = false;
            }
        }
        if(!ifsuccess) return "false";
        return resultBuffer.toString();
    }

    public Map<String,String> jsonToMap(String jsonstr){
        Map<String,String> backmap = new HashMap<String,String>();
        JSONObject json = JSONObject.parseObject(jsonstr);
        for(Object k : json.keySet()){
            backmap.put(k.toString(),json.get(k).toString());
        }
        return backmap;
    }

    private String toBody(Map<String,String> map){
        Set<String> set = map.keySet();
        int i=0;
        StringBuilder builder = new StringBuilder();
        for(String key: set){
            i++;
            builder.append(key);
            builder.append("=");
            builder.append(map.get(key));
            if(i != set.size()){
                builder.append("&");
            }
        }
        return builder.toString();
    }

    public String printURL(HttpServletRequest request){
        String url = request.getRequestURI()+"&"+request.getQueryString();
        System.out.println("URL打印        "+url);
        return url;
    }

    public void downImages(String imageUrl,String saveUrl,String fileName,String cookie){
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept","*/*");
            connection.setRequestProperty("Accept-Encoding","gzip, deflate");
            connection.setRequestProperty("Accept-Language","en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7");
            connection.setRequestProperty("Connection","keep-alive");
            if(cookie == null || cookie.length() == 0){

            }else{
                connection.setRequestProperty("Cookie",cookie);
            }
            connection.setRequestProperty("Host","cache.neea.edu.cn");
            connection.setRequestProperty("Referer","http://cet.neea.edu.cn/cet/");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
            InputStream is = connection.getInputStream();
            // 创建文件
            File file = new File(saveUrl+"\\"+fileName);
            FileOutputStream out = new FileOutputStream(file);
            int i = 0;
            while((i = is.read()) != -1){
                out.write(i);
            }
            is.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getImages(String imageUrl,String id){
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            // 创建文件
            File file = File.createTempFile(id,"png");
            FileOutputStream out = new FileOutputStream(file);
            int i = 0;
            while((i = is.read()) != -1){
                out.write(i);
            }
            is.close();
            out.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void uploadQianURL(String fileUrl,String id) {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            DataInputStream in = new DataInputStream(connection.getInputStream());
            DataOutputStream out = new DataOutputStream(new FileOutputStream(localUrl+"static"+File.separator+"tempImage"+ File.separator+id+".png"));
            byte[] buffer = new byte[20480];
            int count = 0;
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            out.close();
            in.close();
            connection.disconnect();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public String jsonWriteRequest(String urlstr,String json,String encodingType){
        try {
            json = new String(json.getBytes(encodingType),encodingType);
        } catch (UnsupportedEncodingException e) {
            logger.info("编码转换失败");
            return null;
        }
        try {
            //创建连接
            URL url = new URL(urlstr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            // POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(json.getBytes("GBK"));
            out.flush();
            out.close();
            // 读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer buffer = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = URLDecoder.decode(lines, "utf-8");
                buffer.append(lines);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File downFile(String imageUrl){
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept","*/*");
            connection.setRequestProperty("Accept-Encoding","gzip, deflate");
            connection.setRequestProperty("Accept-Language","en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7");
            connection.setRequestProperty("Connection","keep-alive");
            connection.setRequestProperty("Host","cache.neea.edu.cn");
            connection.setRequestProperty("Referer","http://cet.neea.edu.cn/cet/");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
            InputStream is = connection.getInputStream();
            // 创建文件
            File file = File.createTempFile("temp",null);
            FileOutputStream out = new FileOutputStream(file);
            int i = 0;
            while((i = is.read()) != -1){
                out.write(i);
            }
            is.close();
            out.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
