package com.restful.aop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.restful.annotation.SaveLog;
import com.restful.entity.SysLog;
import com.restful.entity.SysUser;
import com.restful.service.SysLogService;
import com.restful.service.SysUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
@Configuration
@Order(1)
public class SysLogAop {
    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private SysUserService sysUserService;

    // 定义切点
    // 在指定注解的位置切入代码
    @Pointcut("@annotation( com.restful.annotation.SaveLog)")
    public void logPointCut() { }

    @After(value = "logPointCut()")
    public void saveLog(JoinPoint joinPoint) {
        System.out.println("----------------日志开始------------------");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SaveLog logAnnotation = method.getAnnotation(SaveLog.class);
        SysLog log = new SysLog();
        log.setMethod(method.getName());
        log.setParams(method.getParameters().toString());

        if (logAnnotation != null) {
            String value = logAnnotation.value();
            log.setDescription(value);
        }
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EntityWrapper<SysUser> sysUserEntityWrapper = new EntityWrapper<SysUser>();
        sysUserEntityWrapper.eq("username", userName);
        SysUser sysUser = sysUserService.selectOne(sysUserEntityWrapper);
        log.setOperator(sysUser.getId());
        log.setTitle(logAnnotation.title());
        log.setCreatedAt(new Date());
        sysLogService.insert(log);
    }
}
