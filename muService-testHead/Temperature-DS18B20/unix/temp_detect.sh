#!/bin/bash

#ID of the temp sensor
TEMP_DEV=
#ID of the test head
TH=

cd /sys/bus/w1/devices/$TEMP_DEV

ULIMIT_WARN=30000
ULIMIT_CRIT=40000
LLIMIT_WARN=0
LLIMIT_CRIT=-2000


#get the temperature
ZA=`cat w1_slave`
Z=`echo $ZA| grep -E -o ".{0,0}t=.{0,5}"|cut -c 3-`
#Z=-100000
#echo Z=$Z

SENSORTEMP=`echo "scale=3; x=$Z/1000; if(x==0) print \"0.0\" else if(x>0 && x<1) print 0,x else if(x>-1 && x<0) print \"-0\",-x else print x" | bc`

#if lower or over certain limit, then CRITICAL
if [ $ULIMIT_CRIT -lt $Z ]; then
    echo $ZA > /tmp/$THCritical.txt
    exit 2
fi
if [ $LLIMIT_CRIT -gt $Z ]; then
    echo $ZA > /tmp/$THCritical.txt
    exit 2
fi


#if lower or over certain limit, then WARNING
if [ $ULIMIT_WARN -lt $Z ]; then
    echo $ZA > /tmp/$THWarning.txt
    exit 1
fi
if [ $LLIMIT_WARN -gt $Z ]; then
    echo $ZA > /tmp/$THWarning.txt
    exit 1
fi

echo $ZA > /tmp/$THNormal.txt
exit 0
