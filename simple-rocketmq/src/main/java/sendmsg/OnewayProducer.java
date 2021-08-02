package sendmsg;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import qx.leizige.rocketmq.test.common.constant.Constants;
import qx.leizige.rocketmq.test.common.utils.RocketMQUtil;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

/**
 * @author leizige
 * created 2021/07/30
 */
public class OnewayProducer {

    /**
     * 单向发送消息
     * 这种方式主要用在不特别关心发送结果的场景，例如日志发送。
     */
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = RocketMQUtil.getDefaultMQProducer();
        // 启动Producer实例
        producer.start();

        for (int i = 0; i < 5; i++) {
            // 创建消息,并指定Topic,Tag和消息体
            StringJoiner joiner = new StringJoiner("+");
            String body = joiner.add("Hello RocketMQ").add(String.valueOf(i)).toString();
            Message message = new Message(Constants.ITEM_TOPIC,
                    Constants.UPDATE_ITEM_PRICE_TAG,
                    body.getBytes(StandardCharsets.UTF_8));
            /* 发送单向消息，没有任何返回结果 */
            producer.sendOneway(message);
        }
        producer.shutdown();
    }
}
