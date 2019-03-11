package top.leaftogo.tanmu.Entity;

import lombok.Data;

@Data
public class UserLikeEntity {
    public int id;
    public int user_give_id;
    public int user_receive_id;
    public long timestamp;

    public UserLikeEntity(int id, int user_give_id, int user_receive_id, long timestamp) {
        this.id = id;
        this.user_give_id = user_give_id;
        this.user_receive_id = user_receive_id;
        this.timestamp = timestamp;
    }

    public UserLikeEntity(int user_give_id, int user_receive_id, long timestamp) {
        this.user_give_id = user_give_id;
        this.user_receive_id = user_receive_id;
        this.timestamp = timestamp;
    }

    public UserLikeEntity() {
    }
}
