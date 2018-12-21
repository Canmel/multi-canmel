package com.restful.aop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.restful.annotation.SaveLog;
import com.restful.config.activeMq.Comsumer;
import com.restful.config.activeMq.Producter;
import com.restful.entity.SysLog;
import com.restful.entity.SysUser;
import com.restful.service.SysUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect
@Configuration
@Order(1)
public class SysLogAop {
    @Autowired
    private SysUserService sysUserService;

    public static final String DEFAULT_USER = "demoUser1";

    public static final String DATE_TEMPLATE_FULL = "yyyy-MM-dd hh:mm:ss";

    @Autowired
    private Producter producter;

    // 定义切点
    // 在指定注解的位置切入代码
    @Pointcut("@annotation( com.restful.annotation.SaveLog)")
    public void logPointCut() {
    }

    @After(value = "logPointCut()")
    public void saveLog(JoinPoint joinPoint) {
        System.out.println("----------------日志开始------------------");
        String splitChar = SysLog.STRING_SPLIT_CHARTS;
        StringBuilder stringBuilder = new StringBuilder();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SaveLog logAnnotation = method.getAnnotation(SaveLog.class);
        stringBuilder.append(method.getName()).append(splitChar).append(method.getParameters().toString()).append(splitChar);

        if (logAnnotation != null) {
            String value = logAnnotation.value();
            stringBuilder.append(value).append(splitChar);
        }
        String userName = "";
        try {
            userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (RuntimeException e) {
            userName = DEFAULT_USER;
        }

        EntityWrapper<SysUser> sysUserEntityWrapper = new EntityWrapper<SysUser>();
        sysUserEntityWrapper.eq("username", userName);
        SysUser sysUser = sysUserService.selectOne(sysUserEntityWrapper);
        SimpleDateFormat sDateFormat = new SimpleDateFormat(DATE_TEMPLATE_FULL);
        stringBuilder.append(sysUser.getId()).append(splitChar)
                .append(logAnnotation.title()).append(splitChar)
                .append(sDateFormat.format(new Date()));
        producter.sendTopicMsg(Comsumer.SYSTEM_LOG_TOPIC_NAME, stringBuilder.toString());
        System.out.println("----------------日志结束------------------");
    }
}
