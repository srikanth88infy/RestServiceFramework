#! /bin/bash

source ../config.sh


ARGC=$#
ARGV=$@


if [ $ARGC != 2 ]; then
  echo "Usage: ./startStorage.sh \$NETWORK \$HOSTIP"
  echo "In the test setup, only 10, 11 + 12 are pre-setup storage host-ips"
  echo "Example: ./startStorage.sh 103 10"
  exit 2;
fi

NETWORK=$1
HOSTIP=$2

echo MAVEN_OPTS=\"-Xms$STORAGE_XMS -Xmx$STORAGE_XMX -XX:PermSize=$STORAGE_PERM\" mvn jetty:run-exploded -Dstorage.host=$PRIVATE_NW_PREFIX.$NETWORK.$HOSTIP -Dstorage.port=$STORAGE_DEFAULT_PORT -DlocationIndex.host=$PRIVATE_NW_PREFIX.$NETWORK.$LOCATION_SERVER_IP -DlocationIndex.port=$LOCATION_INDEX_DEFAULT_PORT
MAVEN_OPTS="-Xms$STORAGE_XMS -Xmx$STORAGE_XMX -XX:PermSize=$STORAGE_PERM" mvn jetty:run-exploded -Dmaven.test.skip=true -Dstorage.host=$PRIVATE_NW_PREFIX.$NETWORK.$HOSTIP -Dstorage.port=$STORAGE_DEFAULT_PORT -DlocationIndex.host=$PRIVATE_NW_PREFIX.$NETWORK.$LOCATION_SERVER_IP -DlocationIndex.port=$LOCATION_INDEX_DEFAULT_PORT
