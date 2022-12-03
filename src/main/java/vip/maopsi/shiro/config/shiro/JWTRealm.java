package vip.maopsi.shiro.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import vip.maopsi.shiro.common.jwt.JWTUtil;

import java.util.HashMap;
import java.util.Map;

public class JWTRealm extends AuthorizingRealm {

    //配置该Realm只支持jwttoken
    @Override
    public Class getAuthenticationTokenClass() {
        return JWTToken.class;
    }

    @Override // 授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //拿到subject主体
        Map<String, Object> principal = (Map<String, Object>) principalCollection.getPrimaryPrincipal();
        //拿到授权信息，对象中包含用户和角色权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info中的角色可以在controller中使用@RequiresRoles({"user"})这样的注解判断权限
        info.addRole((String) principal.get("role"));
        return info;
    }

    @Override  //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) authenticationToken;
        //这个token就是我们自己的从header中获取的token
        String token = (String) jwtToken.getPrincipal();
        //验证token有效性
        Map<String, Object> map = new HashMap<>();
        JWTUtil.verifyToken(token);
        //解析token 放到 返回里面
        map = JWTUtil.parseToken(token);
        return new SimpleAuthenticationInfo(map, true, this.getName());
    }
}
