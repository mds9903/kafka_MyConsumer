package com.kafka.MyConsumer;


import com.fasterxml.jackson.databind.ObjectMapper;

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

@Component
public class MyKafkaListener {
    private static final String uri = "http://localhost:8088/inventory/items/noValidate";
    Logger logger = LoggerFactory.getLogger(MyKafkaListener.class);

    @KafkaListener(topics = "inventory_app", groupId = "items_consumer")
    void listener(String data) {

        logger.info("listener received data:-\n{"+ data + "}\n");
//        if(isFormatValid(data)){
//            try {
//                // send this data to an external api
//                logger.info("Sending data to api");
//
//                // set up the api call
//
//                // a rest template to perform http calls
//                RestTemplate restTemplate = new RestTemplate();
//
//                // convert the data into a itemObject
//                ItemObject pojoItem = getItemObjectFromCSVString(data);
//                if(pojoItem != null){
//                    logger.info("pojo item: "+pojoItem.toString());
//                    // headers to set the body for json
//                    HttpHeaders headers = new HttpHeaders();
//                    headers.setContentType(MediaType.APPLICATION_JSON);
////
//                    // convert pojoItem to a json object for sending in the http request;
//                    ObjectMapper mapper = new ObjectMapper();
////                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//                    String jsonStrItem = mapper.writeValueAsString(pojoItem);
//                    logger.info("json string: "+jsonStrItem);
//
//                    // pass the json as the request body
//                    HttpEntity<String> request = new HttpEntity<String>(jsonStrItem, headers);
//
////                    HttpEntity<String> request = new HttpEntity<String>(pojoItem.toString(), headers);
//
//                    // response of the rest api call
//                    String response = restTemplate.postForObject(uri, request, String.class);
//                    logger.info("Response: " + response);
//
//                }else{
//                    throw new Exception("Error parsing csv data to pojoItem");
//                }
//            } catch (Exception e) {
//                logger.error("Exception Occurred: " + e);
//            }
//        }
    }

    private ItemObject getItemObjectFromCSVString(String data) {
        List<String> elements = Arrays.stream(data.split(",")).toList();
        logger.info("getItemObjectFromCSVString: elements:-\n"+elements);
//        return null;
        int i = 0;
        try{
            Long itemId = Long.valueOf(elements.get(i++));
            String itemDesc = elements.get(i++);
            String category = elements.get(i++);
            String itemType = elements.get(i++);
            String status = elements.get(i++);
            Double price = Double.valueOf(elements.get(i++));
            Boolean pickupAllowed = Boolean.valueOf(elements.get(i++));
            Boolean shippingAllowed = Boolean.valueOf(elements.get(i++));
            Boolean deliveryAllowed = Boolean.valueOf(elements.get(i++));
            return new ItemObject(
                    itemId,
                    itemDesc,
                    category,
                    itemType,
                    status,
                    price,
                    pickupAllowed,
                    shippingAllowed,
                    deliveryAllowed);
        }catch (Exception e){
            return null;
        }
    }

    private Boolean isFormatValid(String data) {
        // add basic level validation; is a string, is of set length, etc.
        return true;
    }
}
