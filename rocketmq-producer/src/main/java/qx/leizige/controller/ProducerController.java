package qx.leizige.controller;

import com.alibaba.fastjson.JSON;
import common.MqConstants;
import core.TracingRocketMqTemplate;
import module.Item;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/producer")
public class ProducerController {

    @Autowired
    private TracingRocketMqTemplate rocketMqTemplate;

    @GetMapping(value = "/send")
    public void send() {

        Item item = new Item(1, "iphone", BigDecimal.TEN);

        Message message = new Message(MqConstants.TEST_TOPIC, MqConstants.TAG_A, "KEY:" + 10086, JSON.toJSONBytes(item));
        rocketMqTemplate.asyncSend(destination(), message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.printf("%s%n", sendResult);
            }

            @Override
            public void onException(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    private String destination() {
        return MqConstants.TEST_TOPIC + ":" + MqConstants.TAG_A;
    }
}
