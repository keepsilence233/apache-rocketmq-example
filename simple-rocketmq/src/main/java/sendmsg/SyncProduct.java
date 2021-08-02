package sendmsg;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import qx.leizige.rocketmq.test.common.constant.Constants;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;


/**
 * @author leizige
 * created 2021/07/30
 */
public class SyncProduct {
    /**
     * Producer端发送同步消息
     * 这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知
     */
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer(Constants.DEFAULT_PRODUCT);
        // 设置NameServer的地址
        producer.setNamesrvAddr("172.19.61.60:9876");

        producer.start();

        for (int i = 0; i < 5; i++) {
            // 创建消息,并指定Topic,Tag和消息体
            StringJoiner joiner = new StringJoiner("+");
            String body = joiner.add("Hello RocketMQ").add(String.valueOf(i)).toString();
            Message message = new Message(Constants.ITEM_TOPIC,
                    Constants.UPDATE_ITEM_PRICE_TAG,
                    body.getBytes(StandardCharsets.UTF_8));

            SendResult sendResult = producer.send(message);
            // 通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
        }

        producer.shutdown();
    }
}
