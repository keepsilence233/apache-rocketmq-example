package qx.leizige.transactionmessage;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import qx.leizige.common.constant.Constants;
import qx.leizige.common.utils.RocketMQUtil;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TransactionMessageProducer {
    public static void main(String[] args) throws MQClientException, InterruptedException {

        TransactionListener transactionListener = new TransactionListenerImpl();
        TransactionMQProducer producer = RocketMQUtil.getTransactionProducer();

        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2000), r -> {
            Thread thread = new Thread(r);
            thread.setName("client-transaction-msg-check-thread");
            return thread;
        });

        producer.setExecutorService(executorService);
        producer.setTransactionListener(transactionListener);
        producer.start();

        try {
            Message message = new Message(Constants.ITEM_TOPIC, Constants.ADD_ITEM_TAG, "KEY" + 10086,
                    "Hello RocketMQ ".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.sendMessageInTransaction(message, "666666");
            System.out.printf("%s%n", sendResult);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Thread.sleep(10);
    }
}
