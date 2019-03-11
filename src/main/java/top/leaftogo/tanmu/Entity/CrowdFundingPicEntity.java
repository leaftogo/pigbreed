package top.leaftogo.tanmu.Entity;

import lombok.Data;

@Data
public class CrowdFundingPicEntity {
    public int id;
    public int crowd_funding_id;
    public String pic_path;
    public String type;

    public CrowdFundingPicEntity(int id, int crowd_funding_id, String pic_path, String type) {
        this.id = id;
        this.crowd_funding_id = crowd_funding_id;
        this.pic_path = pic_path;
        this.type = type;
    }

    public CrowdFundingPicEntity(int crowd_funding_id, String pic_path, String type) {
        this.crowd_funding_id = crowd_funding_id;
        this.pic_path = pic_path;
        this.type = type;
    }

    public CrowdFundingPicEntity() {
    }
}
