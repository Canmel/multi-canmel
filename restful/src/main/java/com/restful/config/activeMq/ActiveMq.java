package com.restful.config.activeMq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class ActiveMq {
    @Bean
    public ConnectionFactory connectionFactory(){
        System.out.println("正在配置activeMq");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        connectionFactory.setUserName("admin");
        connectionFactory.setPassword("admin");
        return connectionFactory;
    }
    @Bean
    public JmsTemplate genJmsTemplate(){
        System.out.println("生成jmsTemplate");
        return new JmsTemplate(connectionFactory());

    }
    @Bean
    public JmsMessagingTemplate jmsMessageTemplate(){
        System.out.println("生成jmsMessagingTemplate");
        return new JmsMessagingTemplate(connectionFactory());

    }

    @Bean
    JmsListenerContainerFactory<?> myJobContainerFactory(ConnectionFactory connectionFactory){
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }
}
