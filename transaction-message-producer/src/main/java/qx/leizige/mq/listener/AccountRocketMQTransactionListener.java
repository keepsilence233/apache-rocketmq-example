package qx.leizige.mq.listener;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import qx.leizige.mp.service.MQTransactionLogService;
import qx.leizige.mq.executor.LocalTransactionExecutor;

@Component
@RocketMQTransactionListener
public class AccountRocketMQTransactionListener implements RocketMQLocalTransactionListener {

    private static final Logger log = LoggerFactory.getLogger(AccountRocketMQTransactionListener.class);

    @Autowired
    private MQTransactionLogService transactionLogService;

    /**
     * 执行业务逻辑
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String transactionId = (String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        if (arg instanceof LocalTransactionExecutor) {
            try {
                log.info("------> start execute operation ......");
                LocalTransactionExecutor<?> localTransactionExecutor = (LocalTransactionExecutor<?>) arg;
                localTransactionExecutor.executor();
                log.info("------> end execute operation ......");
                return RocketMQLocalTransactionState.COMMIT;
            } catch (Exception e) {
                log.error("本地事务执行异常,错误信息:{}", e.getMessage());
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        }
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    /**
     * 回查
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String transactionId = (String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        log.info("回查消息 -- > transactionId = {}", transactionId);
        boolean exists = transactionLogService.existsMQTransactionLog(transactionId);
        return exists ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.ROLLBACK;
    }
}
