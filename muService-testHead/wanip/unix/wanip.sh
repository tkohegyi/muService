#!/bin/bash
#this script checks the WAN IP address and uploads the data to the server
#this script should work via crontab in every hour

#need libs installed:
# - curl
# and this script installed at /opt/mu-service/wanipcheck folder
# and fill the settings below properly
MYID=
MYSERVER=https://muService.magyar.website/appService/uploadData

#get my IP
MYIP=$(curl -s https://api.ipify.org)

#if my ip is empty: CRITICAL
if [ "$MYIP" == "" ]; then
    echo "CRITICAL, Cannot detect My WAN IP"
    exit 1
fi

#upload data
MYSTATEMENT=`echo -n "{ \"id\": \"";echo -n $MYID;echo -n "\", \"head\":\"wanipcheck\", \"status\": \"OK\", \"information\": \"";echo -n $MYIP;echo -n "\" }"`
echo "MYSTATEMENT $MYSTATEMENT"
curl -X POST -H "Content-Type: application/json" -d "$MYSTATEMENT" $MYSERVER
echo "OK, My WAN IP is: $MYIP"

exit 0
