package com.look.kafka.obj;

import com.look.kafka.Msg;
import com.look.kafka.QueueEntity;
import com.look.kafka.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * user: xingjun.zhang
 * Date: 2017-06-25 14:32
 */
@Service
public class MsgProducer {

    private final static Logger logger = LoggerFactory.getLogger(MsgProducer.class);

    @Autowired
    private KafkaTemplate<Integer, QueueEntity> template;

//    private static JsonSerializer<Msg> jsonWriter;
//
//    private static JsonDeserializer<Msg> jsonReader;
//
//    static {
//        jsonReader = new JsonDeserializer<Msg>() {};
//        jsonReader.configure(new HashMap<String, Object>(), false);
//        jsonReader.close(); // does nothing, so may be called any time, or not called at all
//        jsonWriter = new JsonSerializer<>();
//        jsonWriter.configure(new HashMap<String, Object>(), false);
//        jsonWriter.close();
//    }


    private int k = 0;

    @Scheduled(
            fixedDelay = 1000l
    )
    public void send() {
        logger.info("producer : {}", k);
        Msg entity = new Msg();
        entity.intValue = 19;
        entity.longValue = 7L;
        entity.stringValue = "dummy1";
        List<String> list = Arrays.asList("dummy3", "dummy4");
        entity.complexStruct = new HashMap<>();
        entity.complexStruct.put((short) 4, list);
        template.send(Utils.TOPIC_MSG, 0, entity);
        template.flush();
    }

    @Scheduled(
            fixedDelay = 5000l
    )
    public void send1() {
        logger.info("producer : 55");
        Msg entity = new Msg();
        entity.intValue = 20 + k;
        entity.longValue = 17L;
        entity.stringValue = "dummy";
        List<String> list = Arrays.asList("dummy1", "dummy2");
        entity.complexStruct = new HashMap<>();
        entity.complexStruct.put((short) 4, list);
        template.send("foo1", 0, entity);
        template.flush();
    }
}
