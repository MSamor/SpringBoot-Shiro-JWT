package vip.maopsi.shiro.common.R;

public enum DefinedCode {
    SUCCESS(200,"请求成功！"),
    ERROR(400,"请求失败"),
    AUTHERROR(401,"授权失败"),
    TIMEOUT(404,"没有找到资源"),
    SERVERERROR(500,"服务器内部错误");

    private int code;
    private String msg;

    DefinedCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
