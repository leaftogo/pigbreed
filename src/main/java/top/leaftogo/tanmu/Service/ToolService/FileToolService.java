package top.leaftogo.tanmu.Service.ToolService;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class FileToolService {
    private static final Logger logger = LoggerFactory.getLogger(FileToolService.class);
    public void traverseFolder2(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    public void judge(String bathUrl){
        File file = new File(bathUrl);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    String name = file2.getName();
                    String[] array = name.split("_");
                    if(array[0].length() != 4){
                        file2.delete();
                        System.out.println("发现文件名前缀不合法，删除");
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }


    public void show(){

    }

    public void rename(File file,String data){

    }


    public File getOne(String baseUrl){
        File file = new File(baseUrl);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return null;
            } else {
                return files[0];
            }
        } else {
            System.out.println("文件不存在!");
            return null;
        }
    }

    public String getOneUrl(String baseUrl){
        File file = new File(baseUrl);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return null;
            } else {
                String filePath = files[0].getName();
                return filePath;
            }
        } else {
            System.out.println("文件不存在!");
            return null;
        }
    }

    public void start(String baseUrl,String tartget_url){
        File file = new File(baseUrl);
        int allNum = 0;
        int rightNum = 0;
        if (file.exists()) {
            long start = System.currentTimeMillis();
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return ;
            } else {
                for(File file1: files){
                    allNum++;
                    String name = file1.getName();
                    String[] array = name.split("_");
                    String data = array[0];
                    JSONObject jsonObject = JSONObject.parseObject(testOcrLocal(file1,tartget_url));
                    if(jsonObject.containsKey("value")){
                        String end = jsonObject.getString("value");
                        if(data.equals(end)){
                            rightNum++;
                        }
                    }
                }
            }
            long end = System.currentTimeMillis();
            long all = end-start;
            float successRate = Float.valueOf(rightNum)/Float.valueOf(allNum);
        } else {
            System.out.println("文件不存在!");
            return;
        }
    }

    public String testOcrLocal(File file,String url){
        String back = uploadFile(file,url);
        return back;
    }

    public void change(String baseUrl,String picName,String data,String nextUrl) throws FileNotFoundException {
        File file = new File(baseUrl+File.separator+picName);
        savePic(new FileInputStream(file),data+"_"+picName,nextUrl);
        file.delete();
    }




    private void savePic(InputStream inputStream, String fileName,String saveUrl) {
        OutputStream os = null;
        try {
            int len;
            File tempFile = new File(saveUrl);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            while ((len = inputStream.read()) != -1) {
                os.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String uploadFile(File file,String targetUrl) {
        try {
            // 换行符
            final String newLine = "\r\n";
            final String boundaryPrefix = "--";
            // 定义数据分隔线
            String BOUNDARY = "========7d4a6d158c9";
            // 服务器的域名
            URL url = new URL(targetUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求头参数
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());

            // 上传文件
            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            // 文件参数,photo参数名可以随意修改
            sb.append("Content-Disposition: form-data;name=\"image_file\";filename=\"" + file.getName()
                    + "\"" + newLine);
            sb.append("Content-Type:application/octet-stream");
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);

            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());

            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(new FileInputStream(
                    file));
            byte[] bufferOut = new byte[1024];
            int bytes = 0;
            // 每次读1KB数据,并且将文件数据写入到输出流中
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 最后添加换行
            out.write(newLine.getBytes());
            in.close();

            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                    .getBytes();
            // 写上结尾标识
            out.write(end_data);
            out.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line = null;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
            return "false";
        }
    }

    public long startSendLocal(String baseUrl,String target_url){
        File file = new File(baseUrl);
        int allNum = 0;
        int rightNum = 0;
        if (file.exists()) {
            long start = System.currentTimeMillis();
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return 0;
            } else {
                for(File file1: files){
                    allNum++;
                    String name = file1.getName();
                    String[] array = name.split("_");
                    String data = array[0];
                    JSONObject jsonObject = JSONObject.parseObject(testOcrLocal(file1,target_url));
                    if(jsonObject.containsKey("value")){
                        String end = jsonObject.getString("value");
                        if(data.equals(end)){
                            rightNum++;
                        }
                    }
                }
            }
            long end = System.currentTimeMillis();
            long all = end-start;
            float successRate = Float.valueOf(rightNum)/Float.valueOf(allNum);
            logger.info("时长"+all+"   图片数"+allNum+" 成功数"+rightNum+"  成功率"+successRate);
            return all;
        } else {
            System.out.println("文件不存在!");
            return 0;
        }
    }

}
