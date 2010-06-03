#! /bin/bash

GATEWAY_IP=1
LOCATION_SERVER_IP=5
STORAGE_ONE_IP=10
STORAGE_TWO_IP=11
STORAGE_THREE_IP=12
LOCATION_NETWORKS="101 102 103"
IFACE=lo
METASERVICE_NW=105
EXTERNAL_SERVICE_IP=86.112.115.1

ifaceStart=1
currentIF=$ifaceStart

echo "EVS Rest 2010 Interface setup script. Run as root."

echo "Creating internal IPs"
for i in $LOCATION_NETWORKS; do 

    echo "Creating Location: network 10.0.$i.0/24"

    echo "Creating Gateway 10.0.$i.$GATEWAY_IP "

    ifconfig $IFACE:$currentIF 10.0.$i.$GATEWAY_IP/24 up

    currentIF=$((currentIF+1))

    echo "Creating LocationIndex 10.0.$i.$LOCATION_SERVER_IP "

    ifconfig $IFACE:$currentIF 10.0.$i.$LOCATION_SERVER_IP/24 up
    currentIF=$((currentIF+1))


    echo "Creating storage interfaces for 10.0.$i.0/24"

    echo "Creating 10.0.$i.$STORAGE_ONE_IP"
    ifconfig $IFACE:$currentIF 10.0.$i.$STORAGE_ONE_IP/24 up
    currentIF=$((currentIF+1))

    echo "Creating 10.0.$i.$STORAGE_TWO_IP"
    ifconfig $IFACE:$currentIF 10.0.$i.$STORAGE_TWO_IP/24 up
    currentIF=$((currentIF+1))

    echo "Creating 10.0.$i.$STORAGE_THREE_IP"
    ifconfig $IFACE:$currentIF 10.0.$i.$STORAGE_THREE_IP/24 up
    currentIF=$((currentIF+1))

done

echo "Creating external IPs for gateways"
for i in $LOCATION_NETWORKS; do
    echo "external ip for internal network GW 10.0.$i.$GATEWAY_IP is 193.82.$i.$GATEWAY_IP"
    ifconfig $IFACE:$currentIF 193.82.$i.$GATEWAY_IP/24 up
    currentIF=$((currentIF+1))
done;

echo "Creating MetaService"

echo "external ip for MetaService GW 10.0.$METASERVICE_NW.$GATEWAY_IP is 193.82.$METASERVICE_NW.$GATEWAY_IP"
    ifconfig $IFACE:$currentIF 10.0.$METASERVICE_NW.$GATEWAY_IP/24 up
    currentIF=$((currentIF+1))

    ifconfig $IFACE:$currentIF 193.82.$METASERVICE_NW.$GATEWAY_IP/24 up
    currentIF=$((currentIF+1))

echo "external ip for CentralIndex 10.0.$METASERVICE_NW.$LOCATION_SERVER_IP "
    ifconfig $IFACE:$currentIF 10.0.$METASERVICE_NW.$LOCATION_SERVER_IP/24 up
    currentIF=$((currentIF+1))



echo "Creating External Service at $EXTERNAL_SERVICE_IP "
    ifconfig $IFACE:$currentIF $EXTERNAL_SERVICE_IP/24 up

echo "done"
echo "created  $currentIF interfaces" 

echo "To remove this interfaces, simply type:"
echo " for i in \`seq " $ifaceStart " 1 " $currentIF "\`; do ifconfig $IFACE:\$i down; done "



