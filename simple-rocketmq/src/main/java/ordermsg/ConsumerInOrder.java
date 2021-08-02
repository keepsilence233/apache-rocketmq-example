package ordermsg;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import qx.leizige.rocketmq.test.common.constant.Constants;
import qx.leizige.rocketmq.test.common.utils.RocketMQUtil;

import java.util.List;

public class ConsumerInOrder {

    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer consumer = RocketMQUtil.getDefaultMQPushConsumer(Constants.ORDER_TOPIC, Constants.ADD_ORDER_TAG);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);


        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeOrderlyContext context) {

                context.setAutoCommit(true);

                messageExtList.forEach(messageExt -> {
                    System.out.println("consumeThread=" + Thread.currentThread().getName() + "queueId=" + messageExt.getQueueId() + ", content:" + new String(messageExt.getBody()));
                });


                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();

        System.out.println("Consumer Started.");

    }
}
