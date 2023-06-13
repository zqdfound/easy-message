package com.zqdfound.easy.message.rocketmq.consumer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消费者配置信息
 * @Author: zhuangqingdian
 * @Date:2023/4/2
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RocketMqConsumerProperties {
    private String accessKey;
    private String secretKey;
    private String nameSrvAddr;
    private String groupId;
    private String topic;
    private String tag;
}
