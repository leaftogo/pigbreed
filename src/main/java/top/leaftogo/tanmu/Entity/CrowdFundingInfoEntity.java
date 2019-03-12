package top.leaftogo.tanmu.Entity;

import lombok.Data;

@Data
public class CrowdFundingInfoEntity {
    public int id;
    public int user_id;
    public String username;
    public long timestamp;
    public String title;
    public String description;
    public float target;
    public int person_support_amount;
    public float money_gain;
    public int like_amount;
    public String breed_cycle;
    public String pig_type;
    public String weight_minimum_promise;

    public CrowdFundingInfoEntity(int id, int user_id, String username, long timestamp, String title, String description, float target, int person_support_amount, float money_gain, int like_amount, String breed_cycle, String pig_type, String weight_minimum_promise) {
        this.id = id;
        this.user_id = user_id;
        this.username = username;
        this.timestamp = timestamp;
        this.title = title;
        this.description = description;
        this.target = target;
        this.person_support_amount = person_support_amount;
        this.money_gain = money_gain;
        this.like_amount = like_amount;
        this.breed_cycle = breed_cycle;
        this.pig_type = pig_type;
        this.weight_minimum_promise = weight_minimum_promise;
    }

    public CrowdFundingInfoEntity(int user_id, String username, long timestamp, String title, String description, float target, int person_support_amount, float money_gain, int like_amount, String breed_cycle, String pig_type, String weight_minimum_promise) {
        this.user_id = user_id;
        this.username = username;
        this.timestamp = timestamp;
        this.title = title;
        this.description = description;
        this.target = target;
        this.person_support_amount = person_support_amount;
        this.money_gain = money_gain;
        this.like_amount = like_amount;
        this.breed_cycle = breed_cycle;
        this.pig_type = pig_type;
        this.weight_minimum_promise = weight_minimum_promise;
    }

    public CrowdFundingInfoEntity() {
    }
}
