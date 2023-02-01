package com.kafka.MyConsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Pattern;

@Component
public class MyKafkaListener {
    private static final String baseUrl = "http://localhost:8088/inventory/";
    Logger logger = LoggerFactory.getLogger(MyKafkaListener.class);
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "inventory_app", groupId = "items_consumer")
    void listener(String data) throws JsonProcessingException {
        logger.info("Message received: {" + data + "}");

        // task: make a create resource call using the data as the request body (json) to the correct api endpoint
        // assumption: the data(msg) is in a json string format containing details of a new resource

        // select the api endpoint based on the message data

        // make the post call
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(data, headers); // the request object
        // decide the endpoint based on the result

        logger.info("Keys of the data json: " + getKeysInJsonString(data));
        List<String> keys = getKeysInJsonString(data);
        String response = "";

        if (keys.contains("itemId")) {
            logger.info("received a message of item");
            response = restTemplate.postForObject(baseUrl + "/items/", request, String.class);
        } else if (keys.contains("locationId")) {
            logger.info("received a message of location");
            response = restTemplate.postForObject(baseUrl + "/locations/", request, String.class);
        } else {
            logger.info("unknown type of message");
        }

//        // log the response from the request
        logger.info("Response: " + response);
    }

    public List<String> getKeysInJsonString(String jsonStr) throws JsonMappingException, JsonProcessingException {
        List<String> keys = new ArrayList<>();
        JsonNode jsonNode = mapper.readTree(jsonStr);
        Iterator<String> iterator = jsonNode.fieldNames();
        iterator.forEachRemaining(keys::add);
        return keys;
    }
}
