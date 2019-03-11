package top.leaftogo.tanmu.Entity;

import lombok.Data;

@Data
public class UserPayEntity {
    public int id;
    public int user_id;
    public long timestamp;
    public float money;
    public int crowd_funding_id;
    public String type;

    public UserPayEntity(int id, int user_id, long timestamp, float money, int crowd_funding_id, String type) {
        this.id = id;
        this.user_id = user_id;
        this.timestamp = timestamp;
        this.money = money;
        this.crowd_funding_id = crowd_funding_id;
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getCrowd_funding_id() {
        return crowd_funding_id;
    }

    public void setCrowd_funding_id(int crowd_funding_id) {
        this.crowd_funding_id = crowd_funding_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserPayEntity() {
    }
}
