package com.name.no.dts.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MessageCounter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Long messagesPerSecond = 0l;
    private Long second = 0l;

    public void countMessages(Long timestamp) {
        Long messageSecond = timestamp / 1000;
        if (second.equals(messageSecond)) {
            messagesPerSecond++;
        } else {
            logger.info("Number of messages: {} for second: {}", messagesPerSecond, Instant.ofEpochSecond(second));
            messagesPerSecond = 1l;
            second = messageSecond;
        }
    }
}
