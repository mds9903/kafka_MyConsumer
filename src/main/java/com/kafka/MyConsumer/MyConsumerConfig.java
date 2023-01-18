package com.kafka.MyConsumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyConsumerConfig {

    @Value("${spring.kafka.bootstrap-server}")
    private String bootstrapServer;
    @Value("${consumer_groupId}")
    private Object groupId;

    public Map<String, Object> myConsumerConfig() {
        Map<String, Object> props = new HashMap<>();
        // the server to connect with the kafka cluster
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        // deserializer for keys
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // deserializer for values
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // group id configuration
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        // auto offset reset
        // none: if no previous offset is found, don't start consuming
        // earliest: read from the very beginning
        // latest: read from now
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> myConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(myConsumerConfig());
    }


    // receives all messages from all topics all partitions on a single thread
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> factory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(myConsumerFactory());
        return factory;
    }
}
