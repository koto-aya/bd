package com.bd.interceptor;

import com.bd.utils.JWTUtil;
import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author 夕雾
 * @description: 全局拦截器
 * @date 2024/9/24 20:11
 */
public class BDInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        if (StringUtils.isBlank(token)){
            response.getWriter().write("{'code':301,'message':'未登录'}");
            return false;
        }
        if (!JWTUtil.verify(token)||JWTUtil.isExpired(token)){
            response.getWriter().write("{'code':301,'message':'未登录'}");
            return false;
        }
        String userAccount = (String) JWTUtil.getClaim(token).get("userAccount");
        if (StringUtils.isBlank(userAccount)){
            response.getWriter().write("{'code':301,'message':'未登录'}");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
