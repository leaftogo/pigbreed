package top.leaftogo.tanmu.MessageQueue;

import com.alibaba.fastjson.JSONObject;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class Subscriber {
    private String name;
    private String messageType;

    public Subscriber(String name, String messageType) {
        this.name = name;
        this.messageType = messageType;
    }



    public String getName() {
        return name;
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

    public void onMessage(String message){
        System.out.println("发送弹幕");
        String[] array = message.split("!");
        Map<String,String> map = new HashMap<>();
        map.put("color",array[1]);
        map.put("content",array[5]);
        String json = JSONObject.toJSONString(map);
        session.getAsyncRemote().sendText(json);
    }
}
