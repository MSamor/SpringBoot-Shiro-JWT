package vip.maopsi.shiro.common.R;

public class RGenerator {
    public static <T> ResponseEntity<T> resSuccess(){
        return new ResponseEntity<T>().setCode(DefinedCode.SUCCESS.getCode())
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
        return new ResponseEntity<T>().setCode(DefinedCode.SUCCESS.getCode())
                .setMsg(DefinedCode.SUCCESS.getMsg())
                .setData(data);
    }

    public static <T> ResponseEntity<T> resFail(){
        return new ResponseEntity<T>().setCode(DefinedCode.ERROR.getCode())
                .setMsg(DefinedCode.ERROR.getMsg());
    }

    public static <T> ResponseEntity<T> resFailData(String msg){
        return new ResponseEntity<T>().setCode(DefinedCode.ERROR.getCode())
                .setMsg(msg);
    }
}
