#! /bin/bash

source ./config.sh

IFACE=lo
LOCATION_NETWORKS="101 102 103"
ifaceStart=1
currentIF=$ifaceStart

echo "EVS Rest 2010 Interface setup script. Run as root."

echo "Creating internal IPs"
for i in $LOCATION_NETWORKS; do 

    echo "Creating Location: network $PRIVATE_NW_PREFIX.$i.0/24"

    echo "Creating Gateway $PRIVATE_NW_PREFIX.$i.$GATEWAY_IP "

    ifconfig $IFACE:$currentIF $PRIVATE_NW_PREFIX.$i.$GATEWAY_IP/24 up

    currentIF=$((currentIF+1))

    echo "Creating LocationIndex $PRIVATE_NW_PREFIX.$i.$LOCATION_SERVER_IP "

    ifconfig $IFACE:$currentIF $PRIVATE_NW_PREFIX.$i.$LOCATION_SERVER_IP/24 up
    currentIF=$((currentIF+1))


    echo "Creating storage interfaces for $PRIVATE_NW_PREFIX.$i.0/24"

    echo "Creating $PRIVATE_NW_PREFIX.$i.$STORAGE_ONE_IP"
    ifconfig $IFACE:$currentIF $PRIVATE_NW_PREFIX.$i.$STORAGE_ONE_IP/24 up
    currentIF=$((currentIF+1))

    echo "Creating $PRIVATE_NW_PREFIX.$i.$STORAGE_TWO_IP"
    ifconfig $IFACE:$currentIF $PRIVATE_NW_PREFIX.$i.$STORAGE_TWO_IP/24 up
    currentIF=$((currentIF+1))

    echo "Creating $PRIVATE_NW_PREFIX.$i.$STORAGE_THREE_IP"
    ifconfig $IFACE:$currentIF $PRIVATE_NW_PREFIX.$i.$STORAGE_THREE_IP/24 up
    currentIF=$((currentIF+1))

done

echo "Creating external IPs for gateways"
for i in $LOCATION_NETWORKS; do
    echo "external ip for internal network GW $PRIVATE_NW_PREFIX.$i.$GATEWAY_IP is 193.82.$i.$GATEWAY_IP"
    ifconfig $IFACE:$currentIF $PUBLIC_NW_PREFIX.$i.$GATEWAY_IP/24 up
    currentIF=$((currentIF+1))
done;

echo "Creating MetaService"

echo "external ip for MetaService GW $PRIVATE_NW_PREFIX.$METASERVICE_NW.$GATEWAY_IP is $PUBLIC_NW_PREFIX.$METASERVICE_NW.$GATEWAY_IP"
    ifconfig $IFACE:$currentIF $PRIVATE_NW_PREFIX.$METASERVICE_NW.$GATEWAY_IP/24 up
    currentIF=$((currentIF+1))

    ifconfig $IFACE:$currentIF $PUBLIC_NW_PREFIX.$METASERVICE_NW.$GATEWAY_IP/24 up
    currentIF=$((currentIF+1))

echo "external ip for CentralIndex $PRIVATE_NW_PREFIX.$METASERVICE_NW.$LOCATION_SERVER_IP "
    ifconfig $IFACE:$currentIF $PRIVATE_NW_PREFIX.$METASERVICE_NW.$LOCATION_SERVER_IP/24 up
    currentIF=$((currentIF+1))



echo "Creating External Service at $EXTERNAL_SERVICE_IP "
    ifconfig $IFACE:$currentIF $EXTERNAL_SERVICE_IP/24 up

echo "done"
echo "created  $currentIF interfaces" 

echo "To remove this interfaces, simply type:"
echo " for i in \`seq " $ifaceStart " 1 " $currentIF "\`; do ifconfig $IFACE:\$i down; done "



