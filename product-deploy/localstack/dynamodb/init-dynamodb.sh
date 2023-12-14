#!/bin/bash

# -- > Create DynamoDb Table
echo $(awslocal dynamodb create-table --cli-input-json '{"TableName":"Product", "KeySchema":[{"AttributeName":"id","KeyType":"HASH"}], "AttributeDefinitions":[{"AttributeName":"id","AttributeType":"S"}],"BillingMode":"PAY_PER_REQUEST"}')
echo $(awslocal dynamodb create-table --table-name spring-stream-lock-registry --attribute-definitions AttributeName=lockKey,AttributeType=S AttributeName=sortKey,AttributeType=S --key-schema AttributeName=lockKey,KeyType=HASH AttributeName=sortKey,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 --tags Key=Owner,Value=localstack)
echo $(awslocal dynamodb create-table --table-name spring-stream-metadata --attribute-definitions AttributeName=KEY,AttributeType=S --key-schema AttributeName=KEY,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 --tags Key=Owner,Value=localstack)

# --> List DynamoDb Tables
echo Listing tables ...
echo $(awslocal dynamodb list-tables)