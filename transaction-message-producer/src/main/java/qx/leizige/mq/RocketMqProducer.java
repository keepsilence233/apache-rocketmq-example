package qx.leizige.mq;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import qx.leizige.mq.executor.LocalTransactionExecutor;

public interface RocketMqProducer {

    /**
     * 发送事务消息
     *
     * @param tag      标签
     * @param payload  消息体
     * @param executor 回调
     * @return 发送结果
     */
    <T> TransactionSendResult transactionalSend(String tag, T payload, LocalTransactionExecutor<?> executor) ;

}
