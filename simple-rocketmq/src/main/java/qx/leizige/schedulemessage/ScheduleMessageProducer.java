package qx.leizige.schedulemessage;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import qx.leizige.common.constant.Constants;
import qx.leizige.common.utils.RocketMQUtil;

/**
 * @author leizige
 * created 2021/08/01
 */
public class ScheduleMessageProducer {

    /**
     * 延迟消息生产者
     */
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {

        DefaultMQProducer producer = RocketMQUtil.getDefaultMQProducer();

        producer.start();

        int totalMessagesToSend = 100;

        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message(Constants.ITEM_TOPIC,
                    Constants.UPDATE_ITEM_PRICE_TAG, ("Hello scheduled message " + i).getBytes());

            // 设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
            message.setDelayTimeLevel(3);

            SendResult sendResult = producer.send(message);
            System.out.printf("%s%n", sendResult);
        }

        producer.shutdown();
    }
}
