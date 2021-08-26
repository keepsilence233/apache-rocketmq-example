package qx.leizige.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import qx.leizige.TransactionMessageProducer;
import qx.leizige.common.constants.MqConstants;
import qx.leizige.mq.RocketMqProducer;
import qx.leizige.mq.executor.LocalTransactionExecutor;
import qx.leizige.mq.executor.LocalTransactionFactory;
import qx.leizige.mp.po.Account;
import qx.leizige.mp.service.AccountService;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionMessageProducer.class)
public class TransactionMessageProducerTest {

    @Autowired
    private RocketMqProducer rocketMqProducer;

    @Autowired
    private AccountService accountService;

    @Test
    public void testTransactionMessage() throws Exception {
//        Account account = Account.builder().cartNo(1L).balance(BigDecimal.valueOf(50)).build();
        LocalTransactionExecutor<Void> localTransactionExecutor = LocalTransactionFactory.execute(() -> {
//            accountService.updateById(account);
        });

        rocketMqProducer.transactionalSend(MqConstants.TRANSFER_ACCOUNT_TAG, "666", localTransactionExecutor);

    }

}
