package top.leaftogo.tanmu.domain.ResponseBean;

import lombok.Data;


@Data

public class DataBean {
    public int status;
    public String info;
    public Object data;

    public DataBean(int status, String info, Object data) {
        this.status = status;
        this.info = info;
        this.data = data;
    }
}
