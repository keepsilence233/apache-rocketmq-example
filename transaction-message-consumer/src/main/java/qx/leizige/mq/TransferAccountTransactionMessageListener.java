package qx.leizige.mq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qx.leizige.common.constants.MqConstants;
import qx.leizige.mp.service.AccountService;
import qx.leizige.mq.message.TransferAccountDto;

import java.nio.charset.StandardCharsets;

@Component
@RocketMQMessageListener(consumerGroup = MqConstants.TRANSFER_ACCOUNT_GROUP,
        topic = MqConstants.ACCOUNT_TOPIC, selectorType = SelectorType.TAG,
        selectorExpression = MqConstants.TRANSFER_ACCOUNT_TAG)
public class TransferAccountTransactionMessageListener implements RocketMQListener<MessageExt> {

    Logger log = LoggerFactory.getLogger(TransferAccountTransactionMessageListener.class);

    @Autowired
    private AccountService accountService;

    @Override
    public void onMessage(MessageExt message) {
        //TODO 防止重复消费
        try {
            String jsonStr = new String(message.getBody(), StandardCharsets.UTF_8);
            TransferAccountDto transferAccountDto = JSON.parseObject(jsonStr, TransferAccountDto.class);
            log.info("consumerGroup:{},topic:{},tag:{},getMessage:{}",
                    MqConstants.TRANSFER_ACCOUNT_GROUP,
                    MqConstants.ACCOUNT_TOPIC, MqConstants.TRANSFER_ACCOUNT_TAG, jsonStr);
            accountService.addBalanceByCardNo(transferAccountDto.getCartNo(), transferAccountDto.getBalance());
        } catch (Exception e) {
            log.error("errorInfo:{}", e.getMessage());
        }
    }
}
