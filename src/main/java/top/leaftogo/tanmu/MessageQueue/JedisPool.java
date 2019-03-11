package top.leaftogo.tanmu.MessageQueue;

import redis.clients.jedis.Jedis;

import java.util.Vector;

public class JedisPool {
    private static JedisPool ourInstance = new JedisPool();

    public static JedisPool getInstance() {
        return ourInstance;
    }

    private Vector<JedisBean> jedizz;

    private JedisPool() {
        init();
    }

    private void init() {
        jedizz = new Vector<>();
    }

    private int maxAmount = 10;
    private int minAmount = 2;
    private int perAdd = 1;
    private String ip = "localhost";
    private int port = 6379;
    private String password = "cqupt-free";

    private synchronized boolean addJedis(){
        if(jedizz.size() > maxAmount-minAmount) return false;
        else{
            for(int i=0;i<perAdd;i++){
                Jedis jedis = new Jedis(ip,port);
                if(password != null) jedis.auth(password);
                jedis.connect();
                JedisBean jedisBean = new JedisBean(jedis);
                jedizz.add(jedisBean);
            }
        }
        return true;
    }

    private Jedis findFreeJedis(){
        if(jedizz.size() == 0) return null;
        for(JedisBean jedisBean : jedizz){
            if(!jedisBean.getIfuse()){
                synchronized (jedisBean){
                    if(!jedisBean.getIfuse()){
                        jedisBean.setIfuse(true);
                        return jedisBean.getJedis();
                    }
                }
            }
        }
        return null;
    }

    public Jedis getJedis() throws InterruptedException {
        Jedis jedis = findFreeJedis();
        if(jedis != null) return jedis;
        if(addJedis()) {
            jedis = findFreeJedis();
            if(jedis != null) return jedis;
        }
        while(jedis == null){
            Thread.currentThread().sleep(100);
            jedis = findFreeJedis();
        }
        return jedis;
    }

    public boolean returnJedis(Jedis jedis){
        for(JedisBean jedisBean : jedizz){
            if(jedisBean.getJedis() == jedis){
                jedisBean.setIfuse(false);
                return true;
            }
        }
        return false;
    }


}
