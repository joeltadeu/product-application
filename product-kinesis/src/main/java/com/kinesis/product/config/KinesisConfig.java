package com.kinesis.product.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;

import com.amazonaws.services.kinesis.AmazonKinesisAsyncClientBuilder;
import com.amazonaws.services.kinesis.AmazonKinesisAsync;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Primary;

@Profile("localstack")
@Configuration
public class KinesisConfig {
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
  @Primary
  public AmazonKinesisAsync amazonKinesis(AWSCredentialsProvider awsCredentialsProvider) {
    return AmazonKinesisAsyncClientBuilder.standard()
        .withCredentials(awsCredentialsProvider)
        .withEndpointConfiguration(getEndpointConfiguration(kinesisEndpointUrl))
        .build();
  }

  @Bean
  public AWSCredentialsProvider awsCredentialsProvider() {
    return new AWSStaticCredentialsProvider(getBasicAWSCredentials());
  }

  private EndpointConfiguration getEndpointConfiguration(String url) {
    return new EndpointConfiguration(url, region);
  }

  private AWSStaticCredentialsProvider getCredentialsProvider() {
    return new AWSStaticCredentialsProvider(getBasicAWSCredentials());
  }

  private BasicAWSCredentials getBasicAWSCredentials() {
    return new BasicAWSCredentials(accessKey, secretKey);
  }



  /*@Bean
  public AmazonKinesis amazonKinesis(AWSCredentialsProvider awsCredentialsProvider)  {
      return AmazonKinesisClientBuilder
              .standard()
              .withCredentials(awsCredentialsProvider)
              .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, region))
              .build();
  }*/

}
