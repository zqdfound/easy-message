package com.zqdfound.easy.message.rocketmq.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import org.springframework.stereotype.Component;

/**
 * 空实现类，防报错用
 * @Author: zhuangqingdian
 * @Date:2023/5/29
 */
@Component
public class DemoConsumer implements RocketMqConsumerHandler{
    @Override
    public RocketMqConsumerProperties getProperties() {
        return null;
    }

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        return null;
    }
}
