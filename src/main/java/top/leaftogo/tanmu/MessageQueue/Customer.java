package top.leaftogo.tanmu.MessageQueue;

import com.alibaba.fastjson.JSONObject;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class Customer {
    private String name;
    private String messageType;

    public Customer(String name, String messageType) {
        this.name = name;
        this.messageType = messageType;
    }

    public String getMessageType() {
        return messageType;
    }

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void onMessage(String message) {
        String[] array = message.split("!");
        Map<String,String> map = new HashMap<>();
        map.put("danmu_id", array[0]);
        map.put("color", array[1]);
        map.put("showid", array[2]);
        map.put("timestamp", array[3]);
        map.put("pic",array[7]);
        map.put("content", array[5]);
        map.put("wordFilterJudge", array[6]);
        map.put("openid",array[8]);
        String json = JSONObject.toJSONString(map);
        session.getAsyncRemote().sendText(json);
    }

    public String getName() {
        return name;
    }
}
