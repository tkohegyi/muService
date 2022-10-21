#!/bin/bash
#this script checks the temperature and the humidity by using DHT11
#this script should work via crontab in every minute

#need libs installed:
# - curl
# and this script installed at /opt/mu-service/dht11 folder
# and fill the settings below properly
GPIO=
MYID=
MYSERVER=https://muService.magyar.website/appService/uploadData
DHT_TYPE=11

cd /opt/mu-service/dht11
SENSOR=`python Read_DHT.py $DHT_TYPE $GPIO`
#echo $SENSOR > "$MYID"_temp.txt

#upload new data report
MYSTATEMENT=`echo -n "{ \"id\": \"";echo -n $MYID;echo -n "\", \"head\":\"dht11\", \"status\": \"OK\", \"information\": \"";echo -n $SENSOR;echo -n "\" }"`
echo "MYSTATEMENT $MYSTATEMENT"
curl -X POST -H "Content-Type: application/json" -d "$MYSTATEMENT" $MYSERVER
exit 0
