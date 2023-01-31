package com.kafka.MyConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class MyKafkaListener {
    private static final String itemsURL = "http://localhost:8088/inventory/items/";
    Logger logger = LoggerFactory.getLogger(MyKafkaListener.class);
    RestTemplate restTemplate = new RestTemplate();
    @KafkaListener(topics = "inventory_app", groupId = "items_consumer")
    void listener(String data) {
        logger.info("Message received: {" + data + "}");

        // task: make a create resource call using the data as the request body (json) to the correct api endpoint
        // assumption: the data(msg) is in a json string format containing details of a new resource

        // select the api endpoint based on the message data

        // make the post call
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(data, headers); // the request object
        String response = restTemplate.postForObject(itemsURL, request, String.class);
        // log the response from the request
        logger.info("Response: " + response);
    }
}
