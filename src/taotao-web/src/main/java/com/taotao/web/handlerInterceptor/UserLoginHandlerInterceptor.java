package com.taotao.web.handlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.commom.utils.CookieUtils;
import com.taotao.web.bean.User;
import com.taotao.web.service.PropertiesService;
import com.taotao.web.service.UserService;
import com.taotao.web.threadlocal.UserThreadLocal;

public class UserLoginHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private PropertiesService propertiesService;

    public static final String COOKIE_NAME = "TT_TOKEN";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        UserThreadLocal.set(null);
        String token = CookieUtils.getCookieValue(request, COOKIE_NAME);
        String loginUrl = propertiesService.TAOTAO_SSO_URL + propertiesService.USER_LOGIN;
        if (StringUtils.isEmpty(token)) {
            response.sendRedirect(loginUrl);
            return false;
        }
        User user = this.userService.queryUserByToken(token);
        if (user == null) {
            response.sendRedirect(loginUrl);
            return false;
        }
        UserThreadLocal.set(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {

    }

}
