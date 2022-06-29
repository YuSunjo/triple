package com.triple.point.config.interceptor;

import com.triple.point.exception.customException.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
        if (auth == null) {
            return true;
        }
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || header.equals("")) {
            throw new ValidationException("올바르지 않은 접근입니다.");
        }
        return true;
    }

}
