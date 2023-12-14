#!/bin/bash

# -- > Create Kinesis Streams
echo Creating  Streams ...
echo $(awslocal kinesis create-stream --stream-name product_stream --shard-count 1)

# --> List Kinesis Streams
echo Listing streams ...
echo $(awslocal kinesis list-streams)