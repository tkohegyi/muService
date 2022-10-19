#!/bin/bash
#this script checks the temperature
#this script should work via crontab in every minute

#need libs installed:
# - wget
# and this script installed at /opt/mu-service/ds18b20 folder
# and fill the settings below properly
MYID=
MYSERVER=https://muService.magyar.website/secure/uploadData
#ID of the temp sensor
TEMP_DEV=

cd /sys/bus/w1/devices/$TEMP_DEV

#get the temperature
ZA=`cat w1_slave`
Z=`echo $ZA| grep -E -o ".{0,0}t=.{0,5}"|cut -c 3-`
#Z=-100000
#echo Z=$Z

SENSORTEMP=`echo "scale=3; x=$Z/1000; if(x==0) print \"0.0\" else if(x>0 && x<1) print 0,x else if(x>-1 && x<0) print \"-0\",-x else print x" | bc`

cd /opt/mu-service/ds18b20
echo $SENSORTEMP > "$MYID"_temp.txt

#upload new data report
MYSTATEMENT=`echo -n "{ \"id\": \"";echo -n $MYID;echo -n "\", \"head\":\"ds18b20\", \"status\": \"OK\", \"information\": \"";echo -n $SENSORTEMP;echo -n "\" }"`
echo "MYSTATEMENT $MYSTATEMENT"
curl -X POST -H "Content-Type: application/json" -d "$MYSTATEMENT" $MYSERVER
exit 0
