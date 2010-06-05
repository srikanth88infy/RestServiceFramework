#! /bin/bash

IFACE=lo0

echo "EVS Rest 2010 Interface removal script. Run as root."

ifconfig $IFACE -alias 10.0.101.1
ifconfig $IFACE -alias 10.0.101.5
ifconfig $IFACE -alias 10.0.101.10
ifconfig $IFACE -alias 10.0.101.11
ifconfig $IFACE -alias 10.0.101.12
ifconfig $IFACE -alias 10.0.102.1
ifconfig $IFACE -alias 10.0.102.5
ifconfig $IFACE -alias 10.0.102.10
ifconfig $IFACE -alias 10.0.102.11
ifconfig $IFACE -alias 10.0.102.12
ifconfig $IFACE -alias 10.0.103.1
ifconfig $IFACE -alias 10.0.103.5
ifconfig $IFACE -alias 10.0.103.10
ifconfig $IFACE -alias 10.0.103.11
ifconfig $IFACE -alias 10.0.103.12
ifconfig $IFACE -alias 193.82.101.1
ifconfig $IFACE -alias 193.82.102.1
ifconfig $IFACE -alias 193.82.103.1
ifconfig $IFACE -alias 193.82.105.1
ifconfig $IFACE -alias 10.0.105.1
ifconfig $IFACE -alias 10.0.105.5
ifconfig $IFACE -alias 86.112.115.1

echo "done"