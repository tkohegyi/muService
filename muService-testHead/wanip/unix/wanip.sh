#!/bin/bash
#this script checks the WAN IP address and uploads the data to the server
#this script should work via crontab in every hour

#need libs installed:
# - curl
# and this script installed at /opt/mu-service/wanipcheck folder
# and fill the settings below properly
MYID=
MYSERVER=https://muService.magyar.website/appService/uploadData

#clean up
cd /opt/mu-service/wanipcheck
rm -f myip.txt

#get my IP
MYIP=$(curl -s https://api.ipify.org)

#if my ip is empty: CRITICAL
if [ "$MYIP" == "" ]; then
    echo "CRITICAL, Cannot detect My WAN IP|0.0.0.0"
    exit 1
fi

#if my ip is new: WARNING
OLDIP=`cat myip.txt.old 2>/dev/null`
if [ "$MYIP" != "$OLDIP" ]; then
    echo "WARNING, My WAN IP is changed from '$OLDIP' to '$MYIP'|$MYIP"
    #preserve old ip for next use
    echo -n "$MYIP" > myip.txt.old
    #upload new info
    MYSTATEMENT=`echo -n "{ \"id\": \"";echo -n $MYID;echo -n "\", \"head\":\"wanipcheck\", \"status\": \"OK\", \"information\": \"";echo -n $MYIP;echo -n "\" }"`
    echo "MYSTATEMENT $MYSTATEMENT"
    curl -X POST -H "Content-Type: application/json" -d "$MYSTATEMENT" $MYSERVER
    exit 0
fi

echo "OK, My WAN IP is:$MYIP|$MYIP"

exit 0
