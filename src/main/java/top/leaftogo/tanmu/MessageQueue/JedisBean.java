package top.leaftogo.tanmu.MessageQueue;

import redis.clients.jedis.Jedis;

public class JedisBean {
    private boolean ifuse;
    private Jedis jedis;

    public JedisBean(Jedis jedis) {
        this.ifuse = false;
        this.jedis = jedis;
    }

    public boolean getIfuse() {
        return ifuse;
    }

    public void setIfuse(boolean ifuse) {
        this.ifuse = ifuse;
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }
}
