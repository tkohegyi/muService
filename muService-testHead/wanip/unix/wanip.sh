#!/bin/bash
#this script checks the WAN IP address and uploads the data to the server
#this script should work via crontab in every hour

#need libs installed:
# - wget
# - curl
# and this script installed at /opt/mu-service/wanipcheck folder
# and fill the settings below properly
MYID=
MYSERVER=https://muService.magyar.website/secure/uploadData

#clean up
cd /opt/mu-service/wanipcheck
rm -f index.html
rm -f wget.tmp
rm -f myip.txt

#get my IP
wget http://getmyipaddress.org -o wget.tmp --no-dns-cache --no-cache
#this will create an index.html

#now get my ip
cat index.html| grep "Your IP Address"| tr ' ' '\n'| tr '<' '\n'| grep "\." | tr -d ' ' > myip.txt
MYIP=`cat myip.txt| tr -d ' '`

#if my ip is empty: CRITICAL
if [ "$MYIP" == "" ]; then
    echo "CRITICAL, Cannot detect My WAN IP|0.0.0.0"
    mv -f myip.txt myip.txt.old
    mv -f index.html index.html.old
    exit 1
fi

#if my ip is new: WARNING
OLDIP=`cat myip.txt.old`
if [ "$MYIP" != "$OLDIP" ]; then
    echo "WARNING, My WAN IP is changed from '$OLDIP' to '$MYIP'|$MYIP"
    #preserve old ip for next use
    mv -f myip.txt myip.txt.old
    mv -f index.html index.html.old
    #upload new info
    MYSTATEMENT=`echo -n "{ \"id\": \"";echo -n $MYID;echo -n "\", \"head\":\"wanipcheck\", \"status\": \"OK\", \"information\": \"";echo -n $MYIP;echo -n "\" }"`
    echo "MYSTATEMENT $MYSTATEMENT"
    curl -X POST -H "Content-Type: application/json" -d "$MYSTATEMENT" $MYSERVER
    exit 0
fi

echo "OK, My WAN IP is:$MYIP|$MYIP"
mv -f index.html index.html.old

exit 0
