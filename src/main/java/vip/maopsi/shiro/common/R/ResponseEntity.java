package vip.maopsi.shiro.common.R;

import com.alibaba.fastjson.JSON;

public class ResponseEntity<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public ResponseEntity setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseEntity setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseEntity setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
