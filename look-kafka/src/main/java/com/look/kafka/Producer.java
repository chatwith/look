package com.look.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * user: xingjun.zhang
 * Date: 2017-06-25 13:28
 */
@Service
public class Producer {

//    @Autowired
//    private KafkaTemplate<Integer, String> template;
//
//    private int k = 0;
//
//    @Scheduled(
//            fixedDelay = 1000l
//    )
//    public void send() {
//        template.send(Utils.TOPIC, 0, "foo-" + k++);
//        template.flush();
//    }
}

