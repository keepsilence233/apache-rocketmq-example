package qx.leizige.sendmsg;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import qx.leizige.common.constant.Constants;
import qx.leizige.common.utils.RocketMQUtil;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author leizige
 * created 2021/07/30
 */
public class AsyncProducer {

    /**
     * 发送异步消息
     * 异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应。
     */
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = RocketMQUtil.getDefaultMQProducer();
        // 启动Producer实例
        producer.start();
        /* 发送失败重试次数 */
        producer.setRetryTimesWhenSendAsyncFailed(0);

        final int messageCount = 5;
        final CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);
        for (int i = 0; i < messageCount; i++) {
            final int index = i;

            /* 创建消息 */
            Message message = new Message(Constants.ITEM_TOPIC,
                    Constants.UPDATE_ITEM_PRICE_TAG, "Hello world".getBytes(StandardCharsets.UTF_8));
            /* sendCallback接收异步返回结果回调 */
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.err.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        /* 等待五秒 */
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}
