package com.product.consumer.config;

import com.product.consumer.client.ProductClient;
import com.product.consumer.processor.ProductProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.kinesis.common.ConfigsBuilder;
import software.amazon.kinesis.coordinator.Scheduler;
import software.amazon.kinesis.retrieval.polling.PollingConfig;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class KinesisConfiguration {

    private final KinesisConfigurationProperties properties;
    private final ProductClient productClient;

    @Bean
    public Scheduler scheduler(KinesisAsyncClient kinesis, DynamoDbAsyncClient dynamodb, CloudWatchAsyncClient cloudwatch) {
        ConfigsBuilder configs = new ConfigsBuilder(properties.getStream(), properties.getStream(), kinesis, dynamodb, cloudwatch,
                UUID.randomUUID().toString(),
                ProductProcessor::new
        );

        return new Scheduler(
                configs.checkpointConfig(),
                configs.coordinatorConfig(),
                configs.leaseManagementConfig(),
                configs.lifecycleConfig(),
                configs.metricsConfig(),
                configs.processorConfig(),
                configs.retrievalConfig().retrievalSpecificConfig(
                        new PollingConfig(properties.getStream(), kinesis)
                )
        );
    }
}
