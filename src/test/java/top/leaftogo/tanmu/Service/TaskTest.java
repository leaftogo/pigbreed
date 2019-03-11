package top.leaftogo.tanmu.Service;

import org.junit.Test;

public class TaskTest {
//    @Test
//    public void find() throws Exception {
//        System.out.println("{\"log_id\": 2859877376857650882, \"direction\": 1, \"words_result_num\": 13, \"words_result\": [{\"words\": \"全国大学英语四级笔试准考证\"}, {\"words\": \"准考证号:508160172103425\"}, {\"words\": \"姓名:柳晓诚\"}, {\"words\": \"院系年级:通信与信息工程学院17级\"}, {\"words\": \"班级:01141704\"}, {\"words\": \"专业:通信与信息类\"}, {\"words\": \"证件号:369905120017\"}, {\"words\": \"考点:重庆邮电学\"}, {\"words\": \"考场位置:邮电大学(三教学楼3206B)\"}, {\"words\": \"考场编号:034\"}, {\"words\": \"座位号:25\"}, {\"words\": \"学号:2017210241\"}, {\"words\": \"考试时间:2017年12月16日上午9:00-11:20\"}]}".length());
////        获得验证码url   http://cache.neea.edu.cn/Imgs.do?c=CET&ik=111111111111111&t=0.9591226182164225
////        cookie  http://cet.neea.edu.cn/cet/
//
//
//        ToolService tool = new ToolService();
//        String cookie = "UM_distinctid=16768b28bb7411-062dbe1604bbef-6313363-1fa400-16768b28bb84de; language=1; BIGipServercache.neea.edu.cn_pool=2527119370.39455.0000; Hm_lvt_dc1d69ab90346d48ee02f18510292577=1543995463,1544064684,1544068804,1544075059; Hm_lpvt_dc1d69ab90346d48ee02f18510292577=1544075066";
//        while(true){
//            String back = tool.getBasicData("http://cache.neea.edu.cn/Imgs.do?c=CET&ik=111111111111111&t=0.9591226182164225",cookie);
//            System.out.println(back);
//            String url = back.substring(13,77);
//            String filename = String.valueOf(System.currentTimeMillis())+".png";
//            tool.downImages(url,"D:\\picture\\yanzhengma\\neea\\origin",filename,cookie);
////        }//爬四六级验证码，每次开始前请更新cookie
//        int i = 10;
//        for(int a = 0;a<i;a++){
//            SubThread subThread = new SubThread();
//            subThread.start();
//        }
//        Thread.currentThread().sleep(1000000);
//        while(true){
//
//        }
//    }

    @Test
    public void show(){
//        String base_url = "D:\\Study\\Code\\Python\\demo1\\src\\sample\\test";
////        String base_url1 = "D:\\Study\\Code\\Python\\demo1\\src\\sample\\train";
////        FileToolService tool = new FileToolService();
////        tool.judge(base_url);
////        tool.judge(base_url1);

        //js签名请求使用httpclient测试
//        HttpClientRequestService httpClientRequestService = new HttpClientRequestService();
//        String js_ticket_url = "https://wx.idsbllp.cn/MagicLoop/index.php?s=/addon/Api/Api/apiJsTicket";
//        Map map = new HashMap<>();
//        map.put("secret","9d774b27890d2b92ca294352199ecfb6d205312a");
//        map.put("timestamp","1506591622668");
//        map.put("string","1oyanGzoe0y1zG6p");
//        map.put("nonceStr","Bu2hMIxtG7E6B9D5");
//        String back = httpClientRequestService.post(js_ticket_url,map,"UTF-8");
//        System.out.println(back);
    }
//    @Test
//    public void test() throws InterruptedException {
//        String str = "{\"test\":[{\"test\":\"sdf\"},{\"test\":\"sdf\"},{\"test\":\"sdf\"}]}";
//        JSONDetector jsonDetector = new JSONDetector();
//        JSONObject jsonObject = JSONObject.parseObject("");
//        long start = System.currentTimeMillis();
//        if(jsonDetector.parse1(str)){
//            System.out.println("true");
//            long cost = System.currentTimeMillis()-start;
//            System.out.println(cost);
//        }else{
//            System.out.println("false");
//        }
////        String str = "\"";
////        System.out.println(str);

//        for(int i=0;i<10;i++){
//            Test1 test1 = new Test1();
//            test1.start();
//        }
//        Thread.currentThread().sleep(10000);

//        String base64 = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/4RIiRXhpZgAATU0AKgAAAAgACwEPAAIAAAAHA";
//        if(base64 == null){
//
//        }
//        String img_type = base64.substring(base64.indexOf("/")+1,base64.indexOf(";"));
//        if(img_type == null || img_type.length() == 0){
//            System.out.println("errrrrrr");
//        }
//        if(img_type.equals("png") || img_type.equals("jpg") || img_type.equals("bmp")){
//            System.out.println("ok"+img_type);
//        }else{
//            System.out.println("error"+img_type);
//        }
//
//        OpenidService openidService = new OpenidService();
//        openidService.checkOpenid("ouRCyjikk2BE-oxS4S6kfdbIIOqs");
//    }

//    @Test
//    public void test(){
//        CetSearchService cetSearchService = new CetSearchService();
//        ToolService toolService = new ToolService();
//        MarkBean markBean = cetSearchService.searchNoPicCode(new CardBean("508160182106723","匡俊霖"),"");
//        if(markBean == null) {// 默认为请求出问题了
//            System.out.println("error");
//        }else{
//            toolService.toJSONString(markBean);
//        }
//    }

}