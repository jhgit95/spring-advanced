package org.example.expert.domain.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j(topic = "Admin Log")
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    private void changeUserRole(){};

    @Pointcut("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..))")
    private void deleteComment(){};

    @Before("changeUserRole()||deleteComment()")
    public void logAdmin(){
        log.info("Admin Method");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            Long userId = (Long) request.getAttribute("userId");
            LocalDateTime requestTime = LocalDateTime.now();
            String requestUrl = request.getRequestURI();
            String requestMethod = request.getMethod();

            log.info("User ID: {}, Request Time: {}, Request Method: {}, Request URL: {}", userId, requestTime, requestMethod, requestUrl);
        }

    }


}
