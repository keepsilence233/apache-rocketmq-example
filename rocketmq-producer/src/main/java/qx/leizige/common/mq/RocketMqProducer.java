package qx.leizige.common.mq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

public interface RocketMqProducer {

    /**
     * 发送事务消息
     *
     * @param tag      标签
     * @param payload  消息体
     * @param executor 回调
     * @return 发送结果
     */
//    <T> TransactionSendResult transactionalSend(String tag, T payload, LocalExecutor<?> executor);

    /**
     * 同步发送消息
     *
     * @param tag     标签
     * @param payload 消息体
     * @return 发送结果
     */
    <T> SendResult syncSend(String tag, T payload);

    /**
     * 异步发送消息
     *
     * @param tag     标签
     * @param payload 消息体
     */
    <T> void asyncSend(String tag, T payload);

    /**
     * 异步发送消息
     *
     * @param tag      标签
     * @param payload  消息体
     * @param callback 回调
     */
    <T> void asyncSend(String tag, T payload, SendCallback callback);

}
