package qx.leizige.controller;

import core.TracingRocketMqTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/producer")
public class ProducerController {

    @Autowired
    private TracingRocketMqTemplate rocketMqTemplate;

    @GetMapping(value = "/send")
    public void send() {

    }

}
