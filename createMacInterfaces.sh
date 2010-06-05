#! /bin/bash

source ./config.sh

IFACE=lo0
LOCATION_NETWORKS="101 102 103"

echo "EVS Rest 2010 Interface setup script. Run as root."

echo "Creating internal IPs"
for i in $LOCATION_NETWORKS; do 

    echo "Creating Location: network $PRIVATE_NW_PREFIX.$i.0/24"
	
    echo "Creating Gateway $PRIVATE_NW_PREFIX.$i.$GATEWAY_IP "
    ifconfig $IFACE alias $PRIVATE_NW_PREFIX.$i.$GATEWAY_IP/24 up

    echo "Creating LocationIndex $PRIVATE_NW_PREFIX.$i.$LOCATION_SERVER_IP "
    ifconfig $IFACE alias $PRIVATE_NW_PREFIX.$i.$LOCATION_SERVER_IP/24 up

    echo "Creating storage interfaces for $PRIVATE_NW_PREFIX.$i.0/24"
	
    echo "Creating $PRIVATE_NW_PREFIX.$i.$STORAGE_ONE_IP"
    ifconfig $IFACE alias $PRIVATE_NW_PREFIX.$i.$STORAGE_ONE_IP/24 up
    
    echo "Creating $PRIVATE_NW_PREFIX.$i.$STORAGE_TWO_IP"
	ifconfig $IFACE alias $PRIVATE_NW_PREFIX.$i.$STORAGE_TWO_IP/24 up
    
    echo "Creating $PRIVATE_NW_PREFIX.$i.$STORAGE_THREE_IP"
    ifconfig $IFACE alias $PRIVATE_NW_PREFIX.$i.$STORAGE_THREE_IP/24 up
	
done

echo "Creating external IPs for gateways"
for i in $LOCATION_NETWORKS; do
    echo "external ip for internal network GW $PRIVATE_NW_PREFIX.$i.$GATEWAY_IP is 193.82.$i.$GATEWAY_IP"
    ifconfig $IFACE alias $PUBLIC_NW_PREFIX.$i.$GATEWAY_IP/24 up
done;

echo "Creating MetaService"

echo "external ip for MetaService GW $PRIVATE_NW_PREFIX.$METASERVICE_NW.$GATEWAY_IP is $PUBLIC_NW_PREFIX.$METASERVICE_NW.$GATEWAY_IP"
    ifconfig $IFACE alias $PRIVATE_NW_PREFIX.$METASERVICE_NW.$GATEWAY_IP/24 up

    ifconfig $IFACE alias $PUBLIC_NW_PREFIX.$METASERVICE_NW.$GATEWAY_IP/24 up

echo "external ip for CentralIndex $PRIVATE_NW_PREFIX.$METASERVICE_NW.$LOCATION_SERVER_IP "
    ifconfig $IFACE alias $PRIVATE_NW_PREFIX.$METASERVICE_NW.$LOCATION_SERVER_IP/24 up

echo "Creating External Service at $EXTERNAL_SERVICE_IP "
    ifconfig $IFACE alias $EXTERNAL_SERVICE_IP/24 up

echo "done"