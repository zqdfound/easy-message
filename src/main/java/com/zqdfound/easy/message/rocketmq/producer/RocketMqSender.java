package com.zqdfound.easy.message.rocketmq.producer;

import com.aliyun.openservices.ons.api.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * rocketMq消息发送类
 *
 * @Author: zhuangqingdian
 * @Date:2023/4/2
 */
@Component
@Slf4j
public class RocketMqSender {

    @Resource
    private Producer rocketMqProducer;

    /**
     * 发送普通消息-同步
     * @param topic 消息主题
     * @param tag 多个tag使用||分隔，如tagA||tagB
     * @param msgKey 消息业务属性，尽可能唯一方便查找，可以不设置
     * @param msg 消息内容
     */
    public void send(String topic, String tag, String msgKey, String msg) {
        log.info("[rocketMq]发送普通同步消息. Topic:{},tag:{},key:{},内容:{}", topic,tag,msgKey,msg);
        Message message = new Message(
                topic,
                tag,
                msgKey,
                msg.getBytes());

        try {
            SendResult sendResult = rocketMqProducer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理。
            log.error("[rocketMq]普通同步消息发送失败. Topic:{},id:{},key:{}", topic,message.getMsgID(), msgKey);
        }
    }

    /**
     * 发送普通消息-异步
     * @param topic 消息主题
     * @param tag 多个tag使用||分隔，如tagA||tagB
     * @param msgKey 消息业务属性，尽可能唯一方便查找，可以不设置
     * @param msg 消息内容
     */
    public void sendAsync(String topic, String tag, String msgKey, String msg) {
        log.info("[rocketMq]发送普通异步消息. Topic:{},tag:{},key:{},内容:{}", topic,tag,msgKey,msg);
        Message message = new Message(
                topic,
                tag,
                msgKey,
                msg.getBytes());

        rocketMqProducer.sendAsync(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(OnExceptionContext onExceptionContext) {
                // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理。
                log.error("[rocketMq]普通异步消息发送失败. Topic:{},id:{},key:{}", topic,message.getMsgID(), msgKey);
            }
        });

    }

    /**
     * 延时消息
     * 延时时间如果太长，建议通过定时任务在当天延时发送
     * @param topic 消息主题
     * @param tag *表示所有tag，多个tag使用||分隔，如tagA||tagB
     * @param msgKey 消息业务属性，尽可能唯一方便查找，可以不设置
     * @param msg 消息内容
     * @param delayedTime 延时时间
     * @param timeUnit 时间单位
     */
    public void sendDelay(String topic, String tag, String msgKey, String msg, Integer delayedTime, TimeUnit timeUnit) {
        log.info("[rocketMq]发送延时消息. Topic:{},tag:{},key:{},内容:{}", topic,tag,msgKey,msg);
        Message message = new Message(
                topic,
                tag,
                msgKey,
                msg.getBytes());
        long millis = System.currentTimeMillis() + timeUnit.toMillis(delayedTime);
        // 设置消息需要被投递的时间
        message.setStartDeliverTime(millis);
        try {
            rocketMqProducer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理。
            log.error("[rocketMq]延时消息发送失败. Topic:{},id:{},key:{}", topic,message.getMsgID(), msgKey);
        }

    }
}
