package com.example.kafka.consumer;

import com.example.kafka.common.MessageEntity;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SImpleConsumer {
    private Gson gson = new Gson();
    @KafkaListener(topics = "${kafka.topic.default}",containerFactory = "kafkaListenerContainerFactory")
    public void recive(MessageEntity message){
        log.info(gson.toJson(message));
    }
}
