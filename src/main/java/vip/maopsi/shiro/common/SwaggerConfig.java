package vip.maopsi.shiro.common;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2 // 启用 Swagger2
public class SwaggerConfig {

    @Value("V1.0")
    private String systemPublish;

    /**
     * swagger2的配置文件
     * @return Docket
     */
    @Bean
    public Docket hotelApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("酒店管理系统API")
                .pathMapping("/")
                .select()
                    .apis(RequestHandlerSelectors.basePackage("maosi"))
                    //不监控 测试api
                    .paths(Predicates.not(PathSelectors.regex("/Ex.*")))
                    //过滤的接口
                    .paths(PathSelectors.regex("/.*"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .apiInfo(hotelApiInfo());
    }

    /**
     * 设置学生api信息
     * @return Api构建
     */
    private ApiInfo hotelApiInfo() {
        return new ApiInfoBuilder()
                // 设置页面标题
                .title("酒店管理系统")
                // 定义版本号
                .version(systemPublish)
                // 设置联系人
                .contact(
                        new Contact("魏鹏  ->个人主页", "http://www.maosi.vip", "seemingly98@qq.com"))
                // 描述
                .description("酒店管理系统-API")
                .build();
    }

    private List<ApiKey> securitySchemes()
    {
        List<ApiKey> apiKeyList = new ArrayList<ApiKey>();
        apiKeyList.add(new ApiKey("Authorization", "auth", "header"));
        return apiKeyList;
    }

    /**
     * 安全上下文
     */
    private List<SecurityContext> securityContexts()
    {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    /**
     * 默认的安全上引用
     */
    private List<SecurityReference> defaultAuth()
    {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

//    /**
//     * swagger2的配置文件
//     * 设置教师Api组
//     * @return Docket
//     */
//    @Bean
//    public Docket teacherApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("TeacherAPI")
//                .pathMapping("/")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com"))
//                //不监控 测试api
//                .paths(Predicates.not(PathSelectors.regex("/Ex.*")))
//                //过滤的接口
//                .paths(PathSelectors.regex("/.*"))
//                .build()
//                .apiInfo(studentApiInfo());
//    }
//
//    /**
//     * 设置老师api信息
//     * @return Api构建
//     */
//    private ApiInfo teacherApiInfo() {
//        return new ApiInfoBuilder()
//                // 设置页面标题
//                .title("学生就业信息管理系统")
//                // 定义版本号
//                .version(systemPublish)
//                // 设置联系人
//                .contact(
//                        new Contact("Liyang  ->GitHub", "https://github.com/MyGentleLife", "1519365158@qq.com"))
//                // 描述
//                .description("学生个人信息管理-教师API")
//                .build();
//    }

}
