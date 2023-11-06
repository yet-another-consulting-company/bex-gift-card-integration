#!/bin/bash

rm target/*\.jar
mvn clean install
rm target/*\.original

for file in target/*\.jar; do
  echo "${file##*/}"
  java -jar -Dspring.profiles.active=local target/"${file##*/}"

done

#java -jar -Dspring.profiles.active=local target/demo-0.0.1-SNAPSHOT.jar
