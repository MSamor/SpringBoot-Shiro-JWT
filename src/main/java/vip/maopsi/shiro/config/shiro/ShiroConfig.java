package vip.maopsi.shiro.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    @Autowired
    private JWTRealm jWTRealm;

    /**
     * 装配自定义Reaml
     *
     * @return
     */
    @Bean
    public JWTRealm shiroRealm() {
        return new JWTRealm();
    }

    /**
     * 配置安全管理器Realm
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager SecurityManager = new DefaultWebSecurityManager();
        //注入realm操作
        SecurityManager.setRealm(jWTRealm);
        return SecurityManager;
    }

    /**
     * 配置拦截器
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //注入安全管理器
        bean.setSecurityManager(securityManager());
        //未认证的接口
        bean.setLoginUrl("/unauth");
        //设置自定义过滤器
        LinkedHashMap<String, Filter> filter = new LinkedHashMap<>();
        filter.put("jwtFilter", new JWTFilter());
        bean.setFilters(filter);
        //配置拦截地址
        LinkedHashMap<String, String> chain = new LinkedHashMap<>();
        //第一个参数是路径，第二个是拦截器名称，可以为shiro自带也可以自定义
        //关闭shiro的默认session调用  noSessionCreation 关闭会话信息
        chain.put("/api/auth/**", "noSessionCreation,jwtFilter");
        bean.setFilterChainDefinitionMap(chain);
        return bean;
    }

    /**
     * 启动注解支持
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }

    /**
     * 启用shiro内部bean生命周期管理
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 开启aop对shiro的bean的动态代理
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        return creator;
    }
}
