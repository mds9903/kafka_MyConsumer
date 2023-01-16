package com.kafka.MyConsumer;

import org.springframework.http.HttpEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MyKafkaListener {
    @KafkaListener(topics = "type_2_myTopic", groupId = "type_2_myGroup")
    void listener(String data) {
        System.out.println("listener received: " + data + ".");
        String uri = "http://localhost:8090/api/myMessage/";
        try {
            // send this data to an external api
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MyMessage> msg = new HttpEntity<>(new MyMessage("hello from [data:" + data + "]"));
            String result = String.valueOf(restTemplate.postForObject(uri, msg, MyMessage.class));
            System.out.println("Response: " + result);
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e);
        }
    }
}
