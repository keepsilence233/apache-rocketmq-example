package batchmessage;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import qx.leizige.rocketmq.test.common.constant.Constants;
import qx.leizige.rocketmq.test.common.utils.RocketMQUtil;

import java.util.List;

public class BatchMessageConsumer {

    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer consumer = RocketMQUtil.getDefaultMQPushConsumer(Constants.ITEM_TOPIC, Constants.ADD_ITEM_TAG);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.err.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), messageExtList);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.printf("Consumer Started.%n");

    }
}
