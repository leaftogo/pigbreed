package top.leaftogo.tanmu.MessageQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MessageQueue {
    private static MessageQueue ourInstance = new MessageQueue();

    public static MessageQueue getInstance() {
        return ourInstance;
    }

    private MessageQueue() {
    }

    RedisTemplate<String,String> redisTemplate;

    Vector<Listener> listeners;
    Hashtable<String,List<Customer>> customers;
    Hashtable<String,HashSet<Subscriber>> subscribers;
    Hashtable<String,AtomicInteger> messageAmountTable;
    AtomicInteger listenerAmount;
    long timeout;
    LinkedList<String> keyList;
    JedisPool jedisPool;

    protected void init(){
        if(this.customers == null){
            synchronized (this){
                if(this.customers == null){
                    listeners = new Vector<>();
                    customers = new Hashtable<>();
                    subscribers = new Hashtable<>();
                    messageAmountTable = new Hashtable<>();
                    listenerAmount = new AtomicInteger();
                    listenerAmount.set(0);
                    timeout = 7200;
                    keyList = new LinkedList<>();
                    jedisPool = JedisPool.getInstance();
                    addListener();
                }
            }
        }
    }

    protected void addListener(){
        Listener listener = new Listener(this);
        listener.start();
        listeners.add(listener);
        listenerAmount.getAndIncrement();
    }

    public void addCustomer(Customer customer){
        init();
        String messageType = customer.getMessageType();
        if(customers.get(messageType) == null){
            List<Customer> list = new ArrayList<>();
            list.add(customer);
            synchronized (customers){
                if(customers.get(messageType) == null){
                    customers.put(messageType,list);
                }else{
                    customers.get(messageType).add(customer);
                }
            }
        }else{
            customers.get(messageType).add(customer);
        }
    }

    public void addSubscriber(Subscriber subscriber){
        init();
        String messageType = subscriber.getMessageType();
        if(subscribers.get(messageType) == null){
            HashSet<Subscriber> set = new HashSet<>();
            set.add(subscriber);
            synchronized (subscribers){
                if(subscribers.get(messageType) == null){
                    subscribers.put(messageType,set);
                }else{
                    subscribers.get(messageType).add(subscriber);
                }
            }
        }else{
            subscribers.get(messageType).add(subscriber);
        }
    }

    public void addMessage(String messageType,String value){
        init();
        if(messageAmountTable.get(messageType) == null){
            AtomicInteger messageAmount = new AtomicInteger();
            messageAmount.set(0);
            synchronized (messageAmountTable){
                if(messageAmountTable.get(messageType) == null){
                    messageAmountTable.put(messageType,messageAmount);
                }else{
                    messageAmountTable.get(messageType).getAndIncrement();
                }
            }
        }
        String key = messageType+":"+messageAmountTable.get(messageType).getAndIncrement();
        setString(key,value,timeout);
        keyList.addLast(key);
    }

    protected synchronized String getKey(){
        init();
        if(keyList.size() == 0) return null;
        String key = keyList.getFirst();
        String messageType = key.split(":")[0];
        List<Customer> list = customers.get(messageType);
        if(list != null && list.size() > 0){
            System.out.println("拿到key");
            keyList.removeFirst();
            return key;
        }
        HashSet<Subscriber> set = subscribers.get(messageType);
        if(set != null && set.size() > 0){
            System.out.println("拿到key");
            keyList.removeFirst();
            return key;
        }
        return null;
    }

    public void distribute(){
        init();
        System.out.println(keyList.size()+"keylist.size");
        String key = getKey();
        if(key == null) return;
        System.out.println("处理key");
        String content= getString(key);
        String messageType = key.split(":")[0];
        List<Customer> list = customers.get(messageType);
        if(list != null && list.size() > 0){
            synchronized (list){
                if(list != null && list.size() > 0){
                    if(list.size() == 1) list.get(0).onMessage(content);
                    else{
                        list.get(1+(int)(Math.random()*list.size())).onMessage(content);
                    }
                }else{
                    addMessage(messageType,content);
                }
            }
        }
        HashSet<Subscriber> set = subscribers.get(messageType);
        if(set != null && set.size() > 0){
            synchronized (set){
                if(set != null && set.size() > 0){
                    for(Subscriber subscriber : set){
                        subscriber.onMessage(content);
                    }
                }else{
                    addMessage(messageType,content);
                }
            }
        }
    }

    public boolean removeCustomer(String name,String messageType){
        init();
        if(customers.get(messageType) == null) return false;
        List<Customer> list = customers.get(messageType);
        synchronized (list){
            int length = list.size();
            for(int i=0;i<length;i++){
                if(list.get(i).getName().equals(name)){
                    list.remove(i);
                    break;
                }
            }
        }
        return false;
    }

    public boolean removeSubscriber(String name,String messageType){
        init();
        if(subscribers.get(messageType) == null) return false;
        HashSet<Subscriber> set = subscribers.get(messageType);
        synchronized (set){
            for(Subscriber subscriber : set){
                if(subscriber.getName().equals(name)){
                    set.remove(subscriber);
                    return true;
                }
            }
        }
        return false;
    }

    protected void setString(String key, String value, long timeout) {
        try {
            Jedis jedis = jedisPool.getJedis();
            jedis.set(key,value);
            jedis.expire(key, (int) timeout);
            jedisPool.returnJedis(jedis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ;
    }

    protected String getString(String key) {
        String value = null;
        try {
            Jedis jedis = jedisPool.getJedis();
            if(!jedis.exists(key));
            else{
                value = jedis.get(key);
                jedisPool.returnJedis(jedis);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }

    protected boolean remove(String key) {
        try {
            Jedis jedis = jedisPool.getJedis();
            if(!jedis.exists(key)) return false;
            jedis.del(key);
            jedisPool.returnJedis(jedis);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
