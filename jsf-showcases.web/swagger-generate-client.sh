#!/bin/bash

swaggerJson=$1
outputDir=$2

# https://github.com/swagger-api/swagger-codegen
java -cp `cat build-classpath.txt` io.swagger.codegen.SwaggerCodegen generate \
  -i $swaggerJson \
  -l jaxrs \
  -o $outputDir
