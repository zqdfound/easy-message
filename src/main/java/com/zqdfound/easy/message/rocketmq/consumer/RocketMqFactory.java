package com.zqdfound.easy.message.rocketmq.consumer;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;


/**
 * 消费者工厂
 * @Author: zhuangqingdian
 * @Date:2023/4/2
 */
@Component
@Slf4j
public class RocketMqFactory implements InitializingBean {

    //加载所有RocketMqConsumerHandler的实现类
    @Resource
    List<RocketMqConsumerHandler> handlerList;

    @Override
    public void afterPropertiesSet() {
        if(null != handlerList && handlerList.size() > 1){
            log.info("[rocketMq] 开始加载所有客户端");
            handlerList.forEach(l->{
                RocketMqConsumerProperties cp = l.getProperties();
                if(cp != null){
                    Properties properties = new Properties();
                    properties.put(PropertyKeyConst.GROUP_ID, cp.getGroupId());
                    properties.put(PropertyKeyConst.AccessKey, cp.getAccessKey());
                    properties.put(PropertyKeyConst.SecretKey, cp.getSecretKey());
                    properties.put(PropertyKeyConst.NAMESRV_ADDR, cp.getNameSrvAddr());
                    Consumer consumer = ONSFactory.createConsumer(properties);
                    consumer.subscribe(cp.getTopic(),cp.getTag(),l);
                    consumer.start();
                }
            });
            log.info("[rocketMq] 所有客户端加载完毕，共:{}个",handlerList.size()-1);
        }
    }
}
