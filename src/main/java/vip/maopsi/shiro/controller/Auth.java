package vip.maopsi.shiro.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.maopsi.shiro.common.R.RGenerator;
import vip.maopsi.shiro.common.R.ResponseEntity;
import vip.maopsi.shiro.common.jwt.JWTUtil;

import java.util.HashMap;

//shiro测试
@RestController
public class Auth {
    @GetMapping("/api/auth/bbb")
    public ResponseEntity test(){
        return RGenerator.resSuccess();
    }

    @RequiresRoles({"user"})
    @GetMapping("/api/auth/ccc")
    public ResponseEntity test2(){
        return RGenerator.resSuccess();
    }

    @GetMapping("/unauth")
    public ResponseEntity unauth(){
        return RGenerator.resFail();
    }

    @GetMapping("/login")
    public ResponseEntity login(){
        //验证账号密码  默认通过
        //生成token
        HashMap<String, Object> map = new HashMap<>();
        map.put("role","admin");
        String token = JWTUtil.createToken(map);
        //返回token
        return RGenerator.resSuccessData(token);
    }

    @RequiresRoles({"admin"})
    @GetMapping("/api/auth/aaa")
    public ResponseEntity testadmin(){
        return RGenerator.resSuccess();
    }
}
