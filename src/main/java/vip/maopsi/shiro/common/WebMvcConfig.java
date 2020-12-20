package vip.maopsi.shiro.common;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vip.maopsi.shiro.common.R.DefinedCode;
import vip.maopsi.shiro.common.R.ResponseEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
                ResponseEntity result = new ResponseEntity();
                //业务失败的异常
                if (e instanceof HttpMessageNotReadableException) {
                    result.setCode(DefinedCode.ERROR.getCode()).setMsg(e.getMessage());
                } else if (e instanceof NoHandlerFoundException) {
                    result.setCode(DefinedCode.TIMEOUT.getCode()).setMsg("接口 [" + request.getRequestURI() + "] 不存在");
                } else if (e instanceof ServletException) {
                    result.setCode(DefinedCode.ERROR.getCode()).setMsg(e.getMessage());
                } else {
                    result.setCode(DefinedCode.SERVERERROR.getCode()).setMsg("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员"+e.getMessage());
                    String message;
                    if (handler instanceof HandlerMethod) {
                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                        message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                                request.getRequestURI(),
                                handlerMethod.getBean().getClass().getName(),
                                handlerMethod.getMethod().getName(),
                                e.getMessage());
                    } else {
                        message = e.getMessage();
                    }
                }
                responseResult(response, result);
                return new ModelAndView();
            }

        });
    }

    //拦截器返回体
    private void responseResult(HttpServletResponse response, ResponseEntity result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            ex.getMessage();
        }
    }


}
