package com.name.no.dts.kafka;

import com.name.no.dts.dto.Message;
import com.name.no.dts.dto.UserStats;
import com.name.no.dts.util.MessageCounter;
import com.name.no.dts.util.TimeUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class KafkaConsumer {

    private MessageCounter messageCounter;
    private KafkaProducer kafkaProducer;

    public KafkaConsumer(MessageCounter messageCounter,
                         KafkaProducer kafkaProducer) {
        this.messageCounter = messageCounter;
        this.kafkaProducer = kafkaProducer;
    }

    public static final String TOPIC = "consumer-test";

    Map<Long, Set<String>> usersPerMinute = new HashMap<>();

    @KafkaListener(topics = TOPIC,
            properties = {"spring.json.value.default.type=com.name.no.dts.dto.Message"})
    public void consumeMessage(Message message, @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        messageCounter.countMessages(timestamp);

        Long minute = TimeUtil.getMinuteFromTs(message.getTs());
        Set<String> users = getUniqueUsersPerMinute(minute);

        if (!users.contains(message.getUid())) {
            users.add(message.getUid());
            usersPerMinute.put(minute, users);

            UserStats userStats = new UserStats(TimeUtil.getTsFromMinute(minute), users.size());
            kafkaProducer.sendMessage(userStats);
        }
    }

    private Set<String> getUniqueUsersPerMinute(Long minute) {
        Set<String> users;
        if (usersPerMinute.containsKey(minute)) {
            users = usersPerMinute.get(minute);
        } else {
            users = new HashSet<>();
        }
        return users;
    }

}
