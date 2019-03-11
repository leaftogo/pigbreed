package top.leaftogo.tanmu.Entity;

import lombok.Data;

@Data
public class UserPayEntity {
    public int id;
    public int user_id;
    public long timestamp;
    public float money;
    public int crowd_funding_id;

    public UserPayEntity(int id, int user_id, long timestamp, float money, int crowd_funding_id) {
        this.id = id;
        this.user_id = user_id;
        this.timestamp = timestamp;
        this.money = money;
        this.crowd_funding_id = crowd_funding_id;
    }

    public UserPayEntity(int user_id, long timestamp, float money, int crowd_funding_id) {
        this.user_id = user_id;
        this.timestamp = timestamp;
        this.money = money;
        this.crowd_funding_id = crowd_funding_id;
    }

    public UserPayEntity() {
    }
}
