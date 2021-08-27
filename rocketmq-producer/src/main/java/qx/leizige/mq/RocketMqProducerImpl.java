package qx.leizige.mq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import qx.leizige.common.constants.MqConstants;

/**
 * @author leizige
 */
@Component
public class RocketMqProducerImpl implements RocketMqProducer {


    private static final Logger log = LoggerFactory.getLogger(RocketMqProducerImpl.class);


    @Autowired
    private RocketMQTemplate rocketMqTemplate;

    @Override
    public <T> SendResult syncSend(String tag, T payload) {
        try {
            log.info("sync send message,tag:{},payload:{}", tag, JSON.toJSONString(payload));
            SendResult sendResult = rocketMqTemplate.syncSend(destination(tag), message(payload));
            log.info("sync send message success,sendResult : {}", JSON.toJSONString(sendResult));
            return sendResult;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public <T> void asyncSend(String tag, T payload) {
        this.asyncSend(tag, payload, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("sync send message success,sendResult : {}", JSON.toJSONString(sendResult));
            }

            @Override
            public void onException(Throwable e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        });
    }

    @Override
    public <T> void asyncSend(String tag, T payload, SendCallback callback) {
        SendCallback sendCallback = new SendCallback() {

            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("send message async, succeed. message id {}", sendResult.getMsgId());
                callback.onSuccess(sendResult);
            }

            @Override
            public void onException(Throwable e) {
                log.error("send message async, failed", e);
                callback.onException(e);
            }
        };
        try {
            log.info("async send message,tag:{},payload:{}", tag, JSON.toJSONString(payload));
            rocketMqTemplate.asyncSend(destination(tag), message(payload), sendCallback);
            log.info("async send message ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * destination formats: `topicName:tags`
     */
    private String destination(String tag) {
        return MqConstants.ITEM_TOPIC + ":" + tag;
    }

    private <T> org.springframework.messaging.Message<T> message(T payload) {
        return MessageBuilder.withPayload(payload)
                .build();
    }
}
