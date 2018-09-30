package com.example.kafka.producer;

import com.example.kafka.common.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
public class ProducerCallback implements ListenableFutureCallback<SendResult<String, MessageEntity>> {
    private final long startTime;
    private final String key;
    private final MessageEntity message;

    public ProducerCallback(long startTime, String key, MessageEntity message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }

    @Override
    public void onFailure(Throwable throwable) {
        System.err.print(throwable);
    }

    @Override
    public void onSuccess(@Nullable SendResult<String, MessageEntity> result) {
        if (result == null) {
            return;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        RecordMetadata metadata = result.getRecordMetadata();
        if (metadata!=null){
            StringBuilder record = new StringBuilder();
            record.append("message(")
                    .append("key = ").append(key).append(",")
                    .append("message = ").append(message).append(",")
                    .append("sent to partition(").append(metadata.partition()).append("),")
                    .append("with offset(").append(metadata.offset()).append("),")
                    .append("in ").append(elapsedTime).append(" ms");
            log.info(record.toString());
        }
    }
}
