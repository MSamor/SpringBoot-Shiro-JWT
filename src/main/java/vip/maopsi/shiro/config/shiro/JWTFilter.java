package vip.maopsi.shiro.config.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import vip.maopsi.shiro.common.R.DefinedCode;
import vip.maopsi.shiro.common.R.ResponseEntity;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends AccessControlFilter {
    /**
     * 请求到来后的响应方法
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        //判断用户是否有状态，由于取消了session 这里永远到是false
        //System.out.println("检查用户是否授权的方法");
        return false;
    }

    /**
     * 认证未通过执行这个方法、上面返回false 下面的方法才会执行。由于上面
     * 方法永远是false，所以下面的方法一定会执行。验证token即可
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        System.out.println("token filter 认证");
        HttpServletResponse response=  (HttpServletResponse)servletResponse;
        //获取token
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("token");
        //对token进行认证
        JWTToken jwtToken = new JWTToken(token);
        Subject subject = SecurityUtils.getSubject();
        //发起认证
        try {
            subject.login(jwtToken);
        }catch (Exception e){
            ResponseEntity result = new ResponseEntity().setCode(DefinedCode.ERROR.getCode()).setMsg(e.getMessage());
            responseResult(response, result);
            //这个false很关键，不然会继续向下执行。
            return false;
        }
        return true;
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
