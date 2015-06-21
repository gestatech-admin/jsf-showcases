#!/bin/bash
SCRIPTPATH=$( cd $(dirname $0) ; pwd -P )
CODEGEN_REL=v2.1.2

git clone https://github.com/swagger-api/swagger-codegen.git $SCRIPTPATH/../swagger-codegen
cd $SCRIPTPATH/../swagger-codegen

echo chekcing out tags/$CODEGEN_REL
git checkout tags/$CODEGEN_REL

echo Building and installing swagger-codegen to local repository
mvn clean install
