package com.kafka.MyConsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MyKafkaListener {
    @KafkaListener(topics = "type_2_myTopic", groupId = "type_2_myGroup")
    void listener(String data){
        System.out.println("listener received: "+data + ".");
        try{
            // send this data to an external api
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject("http://localhost:8090/api/myMessage/", String.class);
            System.out.println("Response: "+result);
        }catch (Exception e){
            System.out.println("Exception Occurred: "+e);
        }
    }
}
