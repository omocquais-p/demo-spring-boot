#!/bin/bash

if [ -z "$1" ]
then
  URL=http://localhost:8080
  ACTUATOR_PATH=readyz
else
  URL=$1
  ACTUATOR_PATH=actuator/health
fi

UUID=$(curl -X POST --location "http://$URL/customer" -H "Content-Type: application/json" -d "{\"firstName\": \"John\", \"lastName\": \"Smith\"}"  | yq .uuid)

echo "$UUID"

curl -X GET --location "http://$URL/customer/$UUID"

