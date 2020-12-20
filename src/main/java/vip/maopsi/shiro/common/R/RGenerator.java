package vip.maopsi.shiro.common.R;

public class RGenerator {
    public static ResponseEntity resSuccess(){
        return new ResponseEntity().setCode(DefinedCode.SUCCESS.getCode())
                                    .setMsg(DefinedCode.SUCCESS.getMsg());
    }

    /**
     * 第一个 表示是泛型
     * 第二个 表示返回的是T类型的数据
     * 第三个 限制参数类型为T
     * @param data
     * @param <T>
     * @return ResponseEntity<T>
     */
    public static <T> ResponseEntity<T> resSuccessData(T data){
        return new ResponseEntity().setCode(DefinedCode.SUCCESS.getCode())
                .setMsg(DefinedCode.SUCCESS.getMsg())
                .setData(data);
    }

    public static ResponseEntity resFail(){
        return new ResponseEntity().setCode(DefinedCode.ERROR.getCode())
                .setMsg(DefinedCode.ERROR.getMsg());
    }

    public static ResponseEntity resFailData(String msg){
        return new ResponseEntity().setCode(DefinedCode.ERROR.getCode())
                .setMsg(msg);
    }
}
