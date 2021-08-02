package schedulemessage;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import qx.leizige.rocketmq.test.common.constant.Constants;
import qx.leizige.rocketmq.test.common.utils.RocketMQUtil;

import java.util.List;

/**
 * @author leizige
 * created 2021/08/01
 */
public class ScheduleMessageConsumer {

    /**
     * 延迟消息消费
     */
    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer consumer = RocketMQUtil.getDefaultMQPushConsumer(Constants.ITEM_TOPIC, Constants.UPDATE_ITEM_PRICE_TAG);

        // 注册消息监听者
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext context) {
                messageExtList.forEach(messageExt -> {
                    // Print approximate delay time period
                    System.out.println("Receive message[msgId=" + messageExt.getMsgId() + "] "
                            + (System.currentTimeMillis() - messageExt.getBornTimestamp()) + "ms later" + "BODY : " + new String(messageExt.getBody()));
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者
        consumer.start();
    }
}
