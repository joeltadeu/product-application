package com.product.consumer.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.consumer.client.ProductClient;
import com.product.consumer.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.kinesis.exceptions.InvalidStateException;
import software.amazon.kinesis.exceptions.ShutdownException;
import software.amazon.kinesis.lifecycle.events.*;
import software.amazon.kinesis.processor.ShardRecordProcessor;
import java.io.IOException;

@Slf4j
public class ProductProcessor implements ShardRecordProcessor {

  private ProductClient productClient;

  @Override
  public void initialize(InitializationInput input) {}

  @Override
  public void processRecords(ProcessRecordsInput input) {

    input
        .records()
        .forEach(
            record -> {
              log.info("Received Kinesis message: {}.", record);
              byte[] byteArr = new byte[record.data().remaining()];
              record.data().get(byteArr);
              try {
                var product = new ObjectMapper().readValue(new String(record.data().array()), Product.class);
                productClient.save(product);
              } catch (IOException e) {
                log.error("Failed to deserialize record", e);
              }
            });
  }

  @Override
  public void leaseLost(LeaseLostInput input) {}

  @Override
  public void shardEnded(ShardEndedInput input) {
    try {
      input.checkpointer().checkpoint();
    } catch (InvalidStateException | ShutdownException e) {
      log.error("Exception while checkpointing at shard end. Giving up.", e);
    }
  }

  @Override
  public void shutdownRequested(ShutdownRequestedInput input) {
    try {
      input.checkpointer().checkpoint();
    } catch (InvalidStateException | ShutdownException e) {
      log.error("Exception while checkpointing at shutdown. Giving up.", e);
    }
  }
}
