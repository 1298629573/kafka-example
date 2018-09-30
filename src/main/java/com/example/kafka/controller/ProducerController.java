package com.example.kafka.controller;

import com.example.kafka.common.ErrorCode;
import com.example.kafka.common.MessageEntity;
import com.example.kafka.common.Response;
import com.example.kafka.producer.SimpleProducer;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("producer")
@Slf4j
public class ProducerController {

    @Autowired
    private SimpleProducer simpleProducer;

    @Value("${kafka.topic.default}")
    private String topic;

    private Gson gson = new Gson();

    @RequestMapping(value = "/hello",method = RequestMethod.GET , produces = "application/json")
    public Response sendKafka(){
        return new Response(ErrorCode.SUCCESS,"連接成功");
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = "application/json")
    public Response sendKafka(@RequestBody MessageEntity message){
        System.out.println(message);
        try {
            log.info("kafka的消息={}",gson.toJson(message));
            simpleProducer.send(topic,"key",message);
            log.info("消息發送成功");
            return new Response(ErrorCode.SUCCESS,"發送成功");
        }catch (Exception e){
            log.error("消息發送失敗",e);
            return new Response(ErrorCode.EXCEPTION,"消息發送失敗");
        }
    }
}
