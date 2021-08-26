package qx.leizige.test;


import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import qx.leizige.ProducerApplication;
import qx.leizige.common.constants.MqConstants;
import qx.leizige.mq.RocketMqProducer;
import qx.leizige.module.Item;
import qx.leizige.module.UpdateItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author leizige
 * created：2021/08/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProducerApplication.class)
public class ProducerTest {


    @Autowired
    private RocketMqProducer rocketMqProducer;

    @Test
    public void syncSend() {
        for (int i = 1; i <= 10; i++) {
            UpdateItem item = new UpdateItem(i, "iphone", BigDecimal.TEN);
            SendResult sendResult = rocketMqProducer.syncSend(MqConstants.UPDATE_ITEM_TAG, item);
            System.out.printf("%s%n", sendResult);
        }
    }


    @Test
    public void asyncSend() throws InterruptedException {
        List<Item> message = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            message.add(new Item(i, "iphone", BigDecimal.TEN));
        }
        rocketMqProducer.asyncSend(MqConstants.ADD_ITEM_TAG, message);
        TimeUnit.SECONDS.sleep(1);
    }

}
