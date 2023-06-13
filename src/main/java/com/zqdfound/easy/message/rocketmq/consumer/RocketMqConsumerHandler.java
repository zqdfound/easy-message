package com.zqdfound.easy.message.rocketmq.consumer;

import com.aliyun.openservices.ons.api.MessageListener;

/**
 * 消费者实现该接口
 * @Author: zhuangqingdian
 * @Date:2023/4/2
 */
public interface RocketMqConsumerHandler extends MessageListener {
    //基础配置
    RocketMqConsumerProperties getProperties();
}
