package qx.leizige.batchmessage;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import qx.leizige.common.constant.Constants;
import qx.leizige.common.utils.RocketMQUtil;

import java.util.ArrayList;
import java.util.List;

public class BatchMessageProducer {
    /**
     * Producer端[批量]发送同步消息
     *
     */
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException {

        DefaultMQProducer producer = RocketMQUtil.getDefaultMQProducer();
        producer.start();

        List<Message> messageList = new ArrayList<>();

        messageList.add(new Message(Constants.ITEM_TOPIC, Constants.ADD_ITEM_TAG, "0", "Hello world 0".getBytes()));
        messageList.add(new Message(Constants.ITEM_TOPIC, Constants.ADD_ITEM_TAG, "1", "Hello world 1".getBytes()));
        messageList.add(new Message(Constants.ITEM_TOPIC, Constants.ADD_ITEM_TAG, "2", "Hello world 2".getBytes()));
        messageList.add(new Message(Constants.ITEM_TOPIC, Constants.ADD_ITEM_TAG, "3", "Hello world 3".getBytes()));
        messageList.add(new Message(Constants.ITEM_TOPIC, Constants.ADD_ITEM_TAG, "4", "Hello world 4".getBytes()));
        messageList.add(new Message(Constants.ITEM_TOPIC, Constants.ADD_ITEM_TAG, "5", "Hello world 5".getBytes()));


        try {
            SendResult sendResult = producer.send(messageList);
            System.out.printf("%s%n", sendResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        producer.shutdown();
    }
}
