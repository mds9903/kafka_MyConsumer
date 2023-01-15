package com.kafka.MyConsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaListener {
    @KafkaListener(topics = "type_2_myTopic", groupId = "type_2_myGroup")
    void listener(String data){
        System.out.println("listener received: "+data + ".");
    }
}
