package com.zqdfound.easy.message.rocketmq.producer;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * RocketMq基础配置
 * @Author: zhuangqingdian
 * @Date:2023/4/2
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ali.rocketmq")
@Slf4j
public class ProducerConfig {
    private String accessKey;
    private String secretKey;
    private String nameSrvAddr;
    private String groupId;

    public Properties getMqProperties() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, this.accessKey);
        properties.setProperty(PropertyKeyConst.SecretKey, this.secretKey);
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, this.nameSrvAddr);
        properties.setProperty(PropertyKeyConst.GROUP_ID, this.groupId);
        return properties;
    }

    @Bean
    public Producer rocketMqProducer() {
        log.info("[rocketMq]生产者启动...");
        Producer producer = ONSFactory.createProducer(getMqProperties());
        producer.start();
        return producer;
    }
}
