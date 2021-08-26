package qx.leizige.mq;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import qx.leizige.common.constants.MqConstants;
import qx.leizige.mq.executor.LocalTransactionExecutor;

/**
 * @author leizige
 */
@Component
public class RocketMqProducerImpl implements RocketMqProducer {


    private static final Logger log = LoggerFactory.getLogger(RocketMqProducerImpl.class);


    @Autowired
    private RocketMQTemplate rocketMqTemplate;


    @Override
    public <T> TransactionSendResult transactionalSend(String tag, T payload, LocalTransactionExecutor<?> executor) {
        TransactionSendResult sendResult = null;
        try {
            log.info("send transactional message, begin. tag {}", tag);
            sendResult = rocketMqTemplate.sendMessageInTransaction(destination(tag), message(payload), executor);
            log.info("send transactional message, succeed. transaction id {}, message id {}", sendResult.getTransactionId(), sendResult.getMsgId());
            return sendResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendResult;
    }


    /**
     * destination formats: `topicName:tags`
     */
    private String destination(String tag) {
        return MqConstants.ACCOUNT_TOPIC + ":" + tag;
    }

    private <T> org.springframework.messaging.Message<T> message(T payload) {
        return MessageBuilder.withPayload(payload)
                .build();
    }

}
