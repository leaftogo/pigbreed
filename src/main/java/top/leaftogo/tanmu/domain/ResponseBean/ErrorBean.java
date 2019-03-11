package top.leaftogo.tanmu.domain.ResponseBean;

import lombok.Data;

@Data
public class ErrorBean {
    public int error_code;
    public String error_message;

    public ErrorBean(int error_code, String error_message) {
        this.error_code = error_code;
        this.error_message = error_message;
    }
}
