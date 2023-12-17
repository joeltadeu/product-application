#!/bin/bash

# -- > Create Kinesis Streams
echo Creating  S3 Bucket ...
echo $(awslocal s3 mb s3://product-s3-bucket)

# --> List S3 Buckets
echo Listing S3 Buckets ...
echo $(awslocal s3 ls)