package com.restful.config.activeMq;

import com.restful.entity.SysLog;
import com.restful.service.SysLogService;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class Comsumer {

    @Autowired
    private SysLogService sysLogService;

    // 日志模块接收消息的 topic 名称
    public static final String SYSTEM_LOG_TOPIC_NAME = "restful.system.log";

    // 自定义containerFactory
    public static final String JOB_CONTAINER_FACTORY = "myJobContainerFactory";

    /**
     * 接收队列
     * @param msg
     */
    @JmsListener(destination = "test.log")
    public void receiveMsg(String msg){
        System.out.println(msg);
    }

    /**
     * 接收topic
     * @param msg
     */
    @JmsListener(destination = SYSTEM_LOG_TOPIC_NAME, containerFactory = JOB_CONTAINER_FACTORY)
    public void receiveTopicMsg(String msg){
        sysLogService.insert(SysLog.str2Obj(msg));
    }
}
