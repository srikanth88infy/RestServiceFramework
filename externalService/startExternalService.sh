#! /bin/bash

source ../config.sh


ARGC=$#
ARGV=$@


if [ $ARGC != 0 ]; then
  echo "Usage: ./startIndexGateway.sh"
  echo "Example: ./startIndexGateway.sh"
  exit 2;
fi

NETWORK=$1

echo MAVEN_OPTS=\"-Xms$EXTERNAL_SERVICE_XMS -Xmx$EXTERNAL_SERVICE_XMX -XX:PermSize=$EXTERNAL_SERVICE_PERM\" mvn jetty:run-exploded -DexternalService.host=$EXTERNAL_SERVICE_IP -DmetaService.external.host=$META_SERVICE_ADDRESS -DmetaService.external.port=$META_SERVICE_PORT
MAVEN_OPTS="-Xms$EXTERNAL_SERVICE_XMS -Xmx$EXTERNAL_SERVICE_XMX -XX:PermSize=$EXTERNAL_SERVICE_PERM" mvn jetty:run-exploded -DexternalService.host=$EXTERNAL_SERVICE_IP -DmetaService.external.host=$META_SERVICE_ADDRESS -DmetaService.external.port=$META_SERVICE_PORT