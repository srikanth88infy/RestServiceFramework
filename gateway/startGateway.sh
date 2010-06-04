#! /bin/bash

source ../config.sh


ARGC=$#
ARGV=$@


if [ $ARGC != 2 ]; then
  echo "Usage: ./startGateway.sh \$INTERNAL_NETWORK \$EXTERNAL_NETWORK"
  echo "Example: ./startGateway.sh 103 133"
  exit 2;
fi

INTERNAL_NETWORK=$1
EXTERNAL_NETWORK=$2

echo  MAVEN_OPTS=\"-Xms$GATEWAY_XMS -Xmx$GATEWAY_XMX -XX:PermSize=$GATEWAY_PERM\" mvn jetty:run-exploded -Dgateway.internal.host=$PRIVATE_NW_PREFIX.$INTERNAL_NETWORK.$GATEWAY_IP -Dgateway.internal.port=$GATEWAY_INTERNAL_DEFAULT_PORT -Dgateway.external.host=$PUBLIC_NW_PREFIX.$EXTERNAL_NETWORK.$GATEWAY_IP -Dgateway.external.port=$GATEWAY_EXTERNAL_DEFAULT_PORT -DmetaService.host=$META_SERVICE_ADDRESS -DmetaService.port=$META_SERVICE_PORT
MAVEN_OPTS="-Xms$GATEWAY_XMS -Xmx$GATEWAY_XMX -XX:PermSize=$GATEWAY_PERM" mvn jetty:run-exploded -Dgateway.internal.host=$PRIVATE_NW_PREFIX.$INTERNAL_NETWORK.$GATEWAY_IP -Dgateway.internal.port=$GATEWAY_INTERNAL_DEFAULT_PORT -Dgateway.external.host=$PUBLIC_NW_PREFIX.$EXTERNAL_NETWORK.$GATEWAY_IP -Dgateway.external.port=$GATEWAY_EXTERNAL_DEFAULT_PORT -DmetaService.host=$META_SERVICE_ADDRESS -DmetaService.port=$META_SERVICE_PORT
