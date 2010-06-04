#! /bin/bash



source ./config.sh

# TODO: replace the PROCESS_HELPER with screen
# TODO: a screen session should be spawned and each process attached to one of them

PROCESS_HELPER=xterm #this launches a xterm window for every process, in sum 18 windows
#PROCESS_HELPER=....

LOCATION_NETWORKS="101 102 103"

echo "Starting MetaService Index Gateway"
$PROCESS_HELPER ./indexGateway/startIndexGateway.sh

echo "Starting MetaService CentralIndex"
$PROCESS_HELPER ./centralIndex/startCentralIndex.sh

for NETWORK in $LOCATION_NETWORKS; do

    echo "Starting Gateway"
    $PROCESS_HELPER ./gateway/startGateway.sh $NETWORK $NETWORK

    echo "Starting LocationIndex"
    $PROCESS_HELPER ./locationIndex/startLocationIndex.sh $NETWORK

    echo "Starting Storage 1"
    $PROCESS_HELPER ./storage/startStorage.sh $NETWORK $STORAGE_ONE_IP

    echo "Starting Storage 2"
    $PROCESS_HELPER ./storage/startStorage.sh $NETWORK $STORAGE_TWO_IP

    echo "Starting Storage 3"
    $PROCESS_HELPER ./storage/startStorage.sh $NETWORK $STORAGE_THREE_IP

done;

echo "Starting ExternalService"
$PROCESS_HELPER ./externalService/startExternalService.sh

