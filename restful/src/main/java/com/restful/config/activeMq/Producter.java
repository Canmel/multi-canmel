package com.restful.config.activeMq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class Producter {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendQueueMsg(String destination , String content){
        Destination queue = new ActiveMQQueue(destination);
        this.sendMessage(queue, content);
    }

    public void sendTopicMsg(String destination,final String content){
        Destination topic = new ActiveMQTopic(destination);
        this.sendMessage(topic, content);
    }

    private void sendMessage(Destination  destination, final String message){
        jmsMessagingTemplate.convertAndSend(destination, message);
    }
}
