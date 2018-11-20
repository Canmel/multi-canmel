package com.restful.aop;

import com.restful.entity.SysLog;
import com.restful.entity.SysUser;
import com.restful.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class SysLogAop {
    @Autowired
    private SysLogService sysLogService;

    // 定义切点
    // 在指定注解的位置切入代码
    @Pointcut("@annotation(com.restful.annotation.SysLog)")
    public void logPointCut() {

    }

    @After("logPointCut()")
    public void saveLog(JoinPoint joinPoint) {
        System.out.println("日志开始");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.restful.annotation.SysLog logAnnotation = method.getAnnotation(com.restful.annotation.SysLog.class);
        SysLog log = new SysLog();
        log.setMethod(method.getName());
        log.setParams(method.getParameters().toString());

        if (logAnnotation != null) {
            String value = logAnnotation.value();
            log.setDescription(value);
        }
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.setOperator(sysUser.getId());
        log.setTitle(logAnnotation.title());
        log.setCreatedAt(new Date());
        sysLogService.insert(log);
    }
}
