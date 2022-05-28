package qx.leizige.mq.listener;


import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qx.leizige.mp.service.MQTransactionLogService;
import qx.leizige.mq.executor.LocalTransactionExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("all")
@RocketMQTransactionListener
public class AccountRocketMQTransactionListener implements RocketMQLocalTransactionListener {

	private static final Logger log = LoggerFactory.getLogger(AccountRocketMQTransactionListener.class);

	@Autowired
	private MQTransactionLogService transactionLogService;

	/**
	 * 执行本地业务逻辑并提交事务
	 * <p>
	 *     事务消息共有三种状态，提交状态、回滚状态、中间状态：
	 * </p>
	 * TransactionStatus.CommitTransaction: 提交事务，它允许消费者消费此消息。
	 * TransactionStatus.RollbackTransaction: 回滚事务，它代表该消息将被删除，不允许被消费。
	 * TransactionStatus.Unknown: 中间状态，它代表需要检查消息队列来确定状态。
	 */
	@Override
	public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
		if (arg instanceof LocalTransactionExecutor) {
			try {
				String transactionId = getTransactionId(msg);
				log.info("------> start execute operation transactionId = {} ......", transactionId);
				LocalTransactionExecutor<?, Object> localTransactionExecutor = (LocalTransactionExecutor<?, Object>) arg;
				localTransactionExecutor.executor(transactionId);
				log.info("------> end execute operation ......");
				Object result = localTransactionExecutor.getResult();
				return RocketMQLocalTransactionState.COMMIT;
			}
			catch (Exception e) {
				log.error("本地事务执行异常,错误信息:{}", e.getMessage());
				return RocketMQLocalTransactionState.ROLLBACK;
			}
		}
		return RocketMQLocalTransactionState.UNKNOWN;
	}

	/**
	 * 回查
	 * RocketMQ回查事务状态的方法，根据msg里transactionId查询事务是否已提交
	 * <p>
	 *     单个消息默认检查次数为15次，可通过Broker配置文件transactionCheckMax来修改此限制
	 *     如果已经检查某条消息超过 N 次的话（ N = transactionCheckMax ） 则 Broker 将丢弃此消息，并在默认情况下同时打印错误日志。
	 * </p>
	 */
	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
		String transactionId = getTransactionId(message);
		log.info("回查消息 -- > transactionId = {}", transactionId);
		boolean exists = transactionLogService.existsMQTransactionLog(transactionId);
		return exists ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.ROLLBACK;
	}


	/**
	 * 获取事务id
	 */
	private String getTransactionId(org.springframework.messaging.Message message) {
		String transactionKey = RocketMQUtil.toRocketHeaderKey(RocketMQHeaders.TRANSACTION_ID);
		Object transactionId = message.getHeaders().get(transactionKey);
		return null == transactionId ? null : transactionId.toString();
	}

}
