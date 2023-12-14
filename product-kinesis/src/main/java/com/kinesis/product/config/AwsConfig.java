package com.kinesis.product.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisAsync;
import com.amazonaws.services.kinesis.AmazonKinesisAsyncClientBuilder;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.kinesis.common.KinesisClientUtil;

import java.net.URI;

@Profile("local")
@Configuration
public class AwsConfig {
    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.dynamodb.url}")
    private String dynamoDbEndpointUrl;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.kinesis.url}")
    private String kinesisEndpointUrl;

    @Bean
    public AmazonKinesis amazonKinesisClient() {
        return AmazonKinesisClientBuilder.standard()
                .withCredentials(getCredentials())
                .withEndpointConfiguration(getEndpointConfiguration(kinesisEndpointUrl))
                .build();
    }

    @Bean
    public AmazonKinesisAsync amazonKinesisAsyncClient() {
        return AmazonKinesisAsyncClientBuilder.standard()
                .withCredentials(getCredentials())
                .withEndpointConfiguration(getEndpointConfiguration(kinesisEndpointUrl))
                .build();
    }


    @Bean
    public KinesisAsyncClient kinesisAsyncClient() {
        return KinesisClientUtil.createKinesisAsyncClient(
                KinesisAsyncClient.builder()
                        .endpointOverride(URI.create(kinesisEndpointUrl))
                        .region(Region.of(region))
        );
    }

    @Bean
    public DynamoDbAsyncClient dynamoClient() {
        return DynamoDbAsyncClient.builder()
                .region(Region.of(region))
                .endpointOverride(URI.create(dynamoDbEndpointUrl))
                .build();
    }

    @Bean
    public CloudWatchAsyncClient cloudWatchClient() {
        return CloudWatchAsyncClient.builder()
                .region(Region.of(region))
                .build();
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDBAsyncClient() {
        return AmazonDynamoDBAsyncClientBuilder.standard()
                .withCredentials(getCredentials())
                .withEndpointConfiguration(getEndpointConfiguration(dynamoDbEndpointUrl))
                .build();
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDBClient() {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(getCredentials())
                .withEndpointConfiguration(getEndpointConfiguration(dynamoDbEndpointUrl))
                .build();
    }

    @Bean
    public AWSCredentialsProvider getCredentials() {
        return new DefaultAWSCredentialsProviderChain();
    }

    private EndpointConfiguration getEndpointConfiguration(String url) {
        return new EndpointConfiguration(url, region);
    }

}
