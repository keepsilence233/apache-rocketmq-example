package qx.leizige.controller;

import java.math.BigDecimal;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import qx.leizige.common.constants.MqConstants;
import qx.leizige.mp.po.Account;
import qx.leizige.mp.po.MQTransactionLog;
import qx.leizige.mp.service.AccountService;
import qx.leizige.mp.service.MQTransactionLogService;
import qx.leizige.mq.RocketMqProducer;
import qx.leizige.mq.executor.LocalTransactionExecutor;
import qx.leizige.mq.executor.LocalTransactionFactory;
import qx.leizige.mq.message.TransferAccountDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tx")
public class TransactionMessageHandler {

	@Autowired
	private RocketMqProducer rocketMqProducer;

	@Autowired
	private AccountService accountService;

	@Autowired
	private MQTransactionLogService transactionLogService;


	@GetMapping(value = "/message")
	public void test() throws Exception {
		BigDecimal balance = BigDecimal.valueOf(2000);
		//张三给李四转2000
		Account account = accountService.getById(1L);

		if (account.getBalance().compareTo(balance) < 0) {
			throw new Exception("余额不足");
		}

		account.setBalance(account.getBalance().subtract(balance));

		LocalTransactionExecutor<Void, String> localTransactionExecutor = LocalTransactionFactory.execute((transactionId) -> {
			accountService.updateById(account);
			transactionLogService.save(MQTransactionLog.builder()
					.transactionId(transactionId).extInfo(JSON.toJSONString(account)).build());
		});

		String transactionId = UUID.randomUUID().toString().replace("-", "");
		TransactionSendResult sendResult = rocketMqProducer.transactionalSend(MqConstants.TRANSFER_ACCOUNT_TAG,
				new TransferAccountDto("5105297737791629", balance), transactionId, localTransactionExecutor);

		localTransactionExecutor.getResult();
	}
}
