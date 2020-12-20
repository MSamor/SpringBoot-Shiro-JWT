package vip.maopsi.shiro.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class JWTToken implements AuthenticationToken {
    private String jwtToken;

    public JWTToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return jwtToken;
    }

    @Override //账号密码凭证
    public Object getCredentials() {
        return true;
    }
}
