#! /bin/bash



source ./config.sh

# TODO: replace the PROCESS_HELPER with screen
# TODO: a screen session should be spawned and each process attached to one of them

PROCESS_HELPER="xterm -e " #this launches a xterm window for every process, in sum 18 windows
#PROCESS_HELPER=....

LOCATION_NETWORKS="101 102 103"

./checkInterfacesUp.sh
INTERFACES_OK=$?

if [ $INTERFACES_OK != 0 ]; then
    echo "ERROR: the interfaces/ips are not setup correctly."
    echo "Please run "
    echo "      sudo ./createInterfaces.sh"
    echo "first "
    exit 1;
fi;


CURRENT_PWD=`pwd`

echo "ATTENTION! This will probably make your system SWAP A LOT!"
echo "Only use it if you have PLENTY OF RAM."

echo " Press CTRL+C now or Continue with [ENTER] "
read


echo "Starting MetaService Index Gateway"
cd $CURRENT_PWD/indexGateway/
echo $PROCESS_HELPER $CURRENT_PWD/indexGateway/startIndexGateway.sh &

sleep 2;

cd $CURRENT_PWD/centralIndex/
echo "Starting MetaService CentralIndex"
$PROCESS_HELPER $CURRENT_PWD/centralIndex/startCentralIndex.sh &

sleep 2;

for NETWORK in $LOCATION_NETWORKS; do

    cd $CURRENT_PWD/gateway/
    echo "Starting Gateway"
    $PROCESS_HELPER $CURRENT_PWD/gateway/startGateway.sh $NETWORK $NETWORK &

    sleep 2;

    cd $CURRENT_PWD/locationIndex/
    echo "Starting LocationIndex"
    $PROCESS_HELPER $CURRENT_PWD/locationIndex/startLocationIndex.sh $NETWORK &

    sleep 2;

    cd $CURRENT_PWD/storage/
    echo "Starting Storage 1"
    $PROCESS_HELPER $CURRENT_PWD/storage/startStorage.sh $NETWORK $STORAGE_ONE_IP &

    sleep 2;

    echo "Starting Storage 2"
    $PROCESS_HELPER $CURRENT_PWD/storage/startStorage.sh $NETWORK $STORAGE_TWO_IP &

    sleep 2;

    echo "Starting Storage 3"
    $PROCESS_HELPER $CURRENT_PWD/storage/startStorage.sh $NETWORK $STORAGE_THREE_IP &

    sleep 2;

done;

echo "Starting ExternalService"
$PROCESS_HELPER $CURRENT_PWD/externalService/startExternalService.sh & 

