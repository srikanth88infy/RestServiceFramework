#! /bin/bash

source ../config.sh


ARGC=$#
ARGV=$@


if [ $ARGC != 1 ]; then
  echo "Usage: ./startLocationIndex.sh \$NETWORK"
  echo "Example: ./startLocationIndex.sh 103"
  exit 2;
fi

NETWORK=$1

echo MAVEN_OPTS=\"-Xms$LOCATION_INDEX_XMS -Xmx$LOCATION_INDEX_XMX -XX:PermSize=$LOCATION_INDEX_PERM\" mvn jetty:run-exploded -DlocationIndex.host=$PRIVATE_NW_PREFIX.$NETWORK.$LOCATION_SERVER_IP -DlocationIndex.port=$LOCATION_INDEX_DEFAULT_PORT -Dgateway.host=$PRIVATE_NW_PREFIX.$NETWORK.$GATEWAY_IP -Dgateway.port=$GATEWAY_INTERNAL_DEFAULT_PORT
MAVEN_OPTS="-Xms$LOCATION_INDEX_XMS -Xmx$LOCATION_INDEX_XMX -XX:PermSize=$LOCATION_INDEX_PERM" mvn jetty:run-exploded -DlocationIndex.host=$PRIVATE_NW_PREFIX.$NETWORK.$LOCATION_SERVER_IP -DlocationIndex.port=$LOCATION_INDEX_DEFAULT_PORT -Dgateway.host=$PRIVATE_NW_PREFIX.$NETWORK.$GATEWAY_IP -Dgateway.port=$GATEWAY_INTERNAL_DEFAULT_PORT
