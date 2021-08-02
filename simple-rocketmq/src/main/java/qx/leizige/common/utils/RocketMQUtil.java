package qx.leizige.common.utils;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import qx.leizige.rocketmq.test.common.constant.Constants;

/**
 * @author leizige
 * created 2021/07/30
 */
public class RocketMQUtil {

    public static DefaultMQProducer getDefaultMQProducer() {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer(Constants.DEFAULT_PRODUCT);
        // 设置NameServer的地址
        producer.setNamesrvAddr("172.19.61.60:9876");
        return producer;
    }

    public static DefaultMQPushConsumer getDefaultMQPushConsumer(String topic, String tag) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(Constants.DEFAULT_PRODUCT);
        consumer.setNamesrvAddr("172.19.61.60:9876");
        consumer.subscribe(topic, tag);
        return consumer;
    }


    public static TransactionMQProducer  getTransactionProducer() {
        TransactionMQProducer producer = new TransactionMQProducer(Constants.DEFAULT_PRODUCT);
        // 设置NameServer的地址
        producer.setNamesrvAddr("172.19.61.60:9876");
        return producer;
    }
}
