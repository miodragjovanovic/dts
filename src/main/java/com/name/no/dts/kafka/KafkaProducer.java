package com.name.no.dts.kafka;

import com.name.no.dts.dto.UserStats;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final String TOPIC = "producer-test";

    private KafkaTemplate<String, UserStats> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, UserStats> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UserStats message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
